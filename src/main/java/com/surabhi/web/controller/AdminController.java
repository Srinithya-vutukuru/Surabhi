package com.surabhi.web.controller;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.surabhi.persistence.dao.BillRepository;
import com.surabhi.persistence.model.Bill;
import com.surabhi.persistence.model.Message;
import com.surabhi.persistence.model.Privilege;
import com.surabhi.persistence.model.Role;
import com.surabhi.persistence.model.User;
import com.surabhi.persistence.model.UserWrapper;
import com.surabhi.security.ActiveUserStore;
import com.surabhi.service.BillService;
import com.surabhi.service.IAdminService;
import com.surabhi.service.KafkaProducerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

@Controller
public class AdminController {

    @Autowired
    ActiveUserStore activeUserStore;

    @Autowired
    IAdminService userService;
    
    @Autowired
    BillService billService;
    
    @Autowired
    KafkaProducerService kafkaProducerService;

    @GetMapping("/admin/loggedUsers")
    public String getLoggedUsers(final Locale locale, final Model model) { 
        model.addAttribute("users", activeUserStore.getUsers().stream().map(e->userService.findUserByEmail(e)).toList());
        return "users";
    }

    @GetMapping("/admin/loggedUsersFromSessionRegistry")
    public String getLoggedUsersFromSessionRegistry(final Locale locale, final Model model) {
        model.addAttribute("users", userService.getUsersFromSessionRegistry().stream().map(e->userService.findUserByEmail(e)).toList());
        return "users";
    }  
    
    @RequestMapping(value = "/admin/createUsers", method = RequestMethod.POST)
    public String createUsers(@ModelAttribute UserWrapper user, final Model model) {
    	// == create initial privileges
        final Privilege readPrivilege = userService.createPrivilegeIfNotFound("READ_PRIVILEGE");
        final Privilege writePrivilege = userService.createPrivilegeIfNotFound("WRITE_PRIVILEGE");
        final Privilege passwordPrivilege = userService.createPrivilegeIfNotFound("CHANGE_PASSWORD_PRIVILEGE");

        // == create initial roles
        final List<Privilege> adminPrivileges = new ArrayList<>(Arrays.asList(readPrivilege, writePrivilege, passwordPrivilege));
        final List<Privilege> userPrivileges = new ArrayList<>(Arrays.asList(readPrivilege, passwordPrivilege));
        final Role adminRole = userService.createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        final Role userRole = userService.createRoleIfNotFound("ROLE_USER", userPrivileges);
        if("Admin Role".equalsIgnoreCase(user.getRoleType()))
        	user.setRoles(new ArrayList<>(Arrays.asList(adminRole)));
        else
        	user.setRoles(new ArrayList<>(Arrays.asList(userRole)));
        
        model.addAttribute("users", userService.createUserIfNotFound(user.getEmail(), user.getFirstName(), user.getLastName(), user.getPassword(), user.getRoles()));
        model.addAttribute("action", "Success Fully created");   
        return "users";
    }
    
    @RequestMapping(value = "/admin/updateUsers", method = RequestMethod.POST)
    public String updateUsers(@ModelAttribute UserWrapper user, final Model model) {
    	final Privilege readPrivilege = userService.createPrivilegeIfNotFound("READ_PRIVILEGE");
        final Privilege writePrivilege = userService.createPrivilegeIfNotFound("WRITE_PRIVILEGE");
        final Privilege passwordPrivilege = userService.createPrivilegeIfNotFound("CHANGE_PASSWORD_PRIVILEGE");

        // == create initial roles
        final List<Privilege> adminPrivileges = new ArrayList<>(Arrays.asList(readPrivilege, writePrivilege, passwordPrivilege));
        final List<Privilege> userPrivileges = new ArrayList<>(Arrays.asList(readPrivilege, passwordPrivilege));
        final Role adminRole = userService.createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        final Role userRole = userService.createRoleIfNotFound("ROLE_USER", userPrivileges);
        if("Admin Role".equalsIgnoreCase(user.getRoleType()))
        	user.setRoles(new ArrayList<>(Arrays.asList(adminRole)));
        else if("User Role".equalsIgnoreCase(user.getRoleType()))
        	user.setRoles(new ArrayList<>(Arrays.asList(userRole)));
    	
        model.addAttribute("users", userService.updateUserIfFound(user.getEmail(), user.getFirstName(), user.getLastName(), user.getPassword(), user.getRoles()));
        model.addAttribute("action", "Success Fully updated");   
        return "users";
    }
    
    @RequestMapping(value = "/admin/deleteUsers", method = RequestMethod.POST)
    public String deleteUsers(@ModelAttribute User user, final Model model) {
    	userService.deleteUser(userService.findUserByEmail(user.getEmail()));
        model.addAttribute("action", "Success Fully deleted");        		
        return "users";
    }
    
    @GetMapping("/admin/viewUsers")
    public String viewUsers(final Locale local, final Model model) {
    	 model.addAttribute("users",userService.findAll());
        model.addAttribute("action", "User List");        		
        return "users";
    }
    
    @RequestMapping(value = "/admin/billByDate", method = RequestMethod.POST)
    public String billByDate(@ModelAttribute Bill bill, final Model model) {
    	model.addAttribute("billItems",billService.viewBillByDate(bill.getDate()));
        model.addAttribute("action", "Bill List");        		
        return "users";
    }
    
    @RequestMapping(value = "/admin/billByMonth", method = RequestMethod.POST)
    public String billByMonth(@ModelAttribute Bill bill, final Model model) {
    	Date date = bill.getDate();
    	date.setDate(01);
    	List<Bill> bills = billService.viewBillByDate(date);
    	
    	model.addAttribute("billItems",billService.viewBillByDate(date));
    	model.addAttribute("Cost",bills.stream().map(e-> e.getAmount()).reduce(Long::sum));
        model.addAttribute("action", "Bill Amount");        		
        return "users";
    }
    
    @GetMapping("/admin/viewSalesByCity")
    public String viewSalesByCity(@RequestParam(value = "city", required = true) String city, final Model model) {
    	model.addAttribute("saleItems",userService.findAllOrders(city));
        model.addAttribute("action", "Bill List");        		
        return "users";
    }
    
    @GetMapping("/admin/viewSalesByMonth")
    public String viewSalesByMonth(@RequestParam(value = "month", required = true) String month, final Model model) {
    	model.addAttribute("saleItems",userService.findByMonth(month));
        model.addAttribute("action", "Bill List");        		
        return "users";
    }
    
    @GetMapping("/admin/viewSalesByYear")
    public String viewSalesByYear(@RequestParam(value = "year", required = true) String year, final Model model) {
    	model.addAttribute("billItems",userService.findByYear(year));
        model.addAttribute("action", "Bill List");        		
        return "users";
    }
    
    @KafkaListener(topics = "admin", groupId = "groupId")
   	public void listenUserGroup(Message message) {
       	
		System.out.println("Received Message in group Admin: ");
		System.out.println("From : " + message.getSender());
		System.out.println("Date : " + message.getDate());
		System.out.println("Message : "+ message.getMessage());
       
   	    
   	}
    @GetMapping("/admin/sendMessage")
    public String sendMessage(@RequestParam(value = "message", required = true) String message,
    		@RequestParam(value = "receiver", required = true) String receiver, final Model model) {
    	Message msg = new Message();
    	msg.setDate(new Date());
    	msg.setMessage(message);
    	msg.setReciver(receiver);
    	msg.setSender("admin");
    	kafkaProducerService.sendMessage(msg, "admin");
        return "users";
    }
    
    
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        //convert the date Note that the conversion here should always be in the same format as the string passed in, e.g. 2015-9-9 should be yyyy-MM-dd
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor is a custom date editor
    }
    
}
