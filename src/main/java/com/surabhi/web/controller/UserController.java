package com.surabhi.web.controller;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.surabhi.persistence.model.ItemWrapper;
import com.surabhi.persistence.model.Message;
import com.surabhi.persistence.model.SelectItems;
import com.surabhi.persistence.model.User;
import com.surabhi.service.BillService;
import com.surabhi.service.IUserService;
import com.surabhi.service.KafkaProducerService;
import com.surabhi.service.MenuService;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

@Controller
@Getter
@Setter
public class UserController {
    
    @Autowired
    MenuService menuService;
    
    @Autowired
    BillService billService;
    
    @Autowired
    IUserService userService;
    
    @Autowired
    KafkaProducerService kafkaProducerService;
    
    private User user = null;
    
    @GetMapping("/user/allItems")
    public String getAllItems(final Locale locale, final Model model) {
    	List<SelectItems> listOfItems = menuService.getAllItems();
    	ItemWrapper itemrWrapper = new ItemWrapper();
    	itemrWrapper.setListOfItems(listOfItems);
    	model.addAttribute("itemWrapper", itemrWrapper);
        model.addAttribute("items", listOfItems);
        return "itemList";
    }
    
    @RequestMapping(value = "/user/selectItems", method = RequestMethod.POST)
    public String selectItems(@ModelAttribute ItemWrapper itemWrapper, Model model){
    	List<SelectItems> selectedMenu = itemWrapper.getListOfItems().stream().filter(e->e.getStatus()==true).toList();
    	
    	model.addAttribute("selectedItems",selectedMenu);
    	Integer cost = selectedMenu.stream().map(e->e.getPrice()).reduce(0, Integer::sum);
    	String items = StringUtils.join(
    			selectedMenu.stream()
    			.map(e->e.getItem())
    			.map(e-> String.valueOf(e))
    			.collect(Collectors.toList())
    			,',');
    	if(user==null)
    		setUser();
    	billService.saveBill(cost.longValue(), new Date(), user.getEmail(), items);
    	model.addAttribute("cost", cost);
    	return "successMail";
    }
    
    @GetMapping("/user/viewAllOrders")
    public String getAllOrders(final Locale locale, final Model model) {
    	if(user==null)
    		setUser();
        model.addAttribute("items", userService.findByEmail(user.getEmail()));
        return "orderList";
    }
    
    @GetMapping("/user/viewOrdersByDate")
    public String viewOrdersByDate(@RequestParam(value = "date", required = true) Date date, final Model model) {
    	if(user==null)
    		setUser();
        model.addAttribute("itemsByDate", userService.findByDate(date,user.getEmail()));
        return "orderList";
    }
    
    @GetMapping("/user/viewOrdersByPrice")
    public String viewOrdersByPrice(@RequestParam(value = "amount", required = true) Long amount, final Model model) {
    	if(user==null)
    		setUser();
        model.addAttribute("itemsByPrice", userService.findByPrice(amount,user.getEmail()));
        return "orderList";
    }
    
    
    @KafkaListener(topics = "user", groupId = "groupId")
	public void listenUserGroup(Message message) {
    	if(user==null)
    		setUser();
    	if(message.getReciver().equalsIgnoreCase(user.getEmail())) {
    		System.out.println("Received Message in group User: ");
    		System.out.println("From : " + message.getSender());
    		System.out.println("Date : " + message.getDate());
    		System.out.println("Message : "+ message.getMessage());
    	}
	    
	}
    
    @GetMapping("/user/sendMessage")
    public String sendMessage(@RequestParam(value = "message", required = true) String message,
    		@RequestParam(value = "receiver", required = true) String receiver, final Model model) {
    	if(user==null)
    		setUser();
    	Message msg = new Message();
    	msg.setDate(new Date());
    	msg.setMessage(message);
    	msg.setReciver(receiver);
    	msg.setSender(this.user.getEmail());
    	kafkaProducerService.sendMessage(msg, "user");
         		
        return "users";
    }
    
    
    private void setUser() {
    	final Authentication curAuth = SecurityContextHolder.getContext().getAuthentication();
        this.user = (User) curAuth.getPrincipal();
    }
    
    
}

