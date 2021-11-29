package com.surabhi.web.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.surabhi.persistence.model.ItemWrapperList;
import com.surabhi.persistence.model.Seat;
import com.surabhi.persistence.model.SelectItemsList;
import com.surabhi.persistence.model.User;
import com.surabhi.service.BillService;
import com.surabhi.service.BookService;
import com.surabhi.service.MenuService;

import org.springframework.beans.factory.annotation.Autowired;
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
public class OfflineUserController {
    
    @Autowired
    MenuService menuService;
    
    @Autowired
    BillService billService;
    
    @Autowired
    BookService bookService;
    
    @GetMapping("/offline/allItems")
    public String getOffileAllItems(final Locale locale, final Model model) {
    	List<SelectItemsList> listOfItems = menuService.getAllItemsList();
    	ItemWrapperList itemrWrapper = new ItemWrapperList();
    	itemrWrapper.setListOfItems(listOfItems);
    	model.addAttribute("itemWrapper", itemrWrapper);
        model.addAttribute("items", listOfItems);
        return "itemListOffline";
    }
    
    @RequestMapping(value = "/offline/selectItems", method = RequestMethod.POST)
    public String offlineSelectItems(@ModelAttribute ItemWrapperList itemWrapper, Model model){
    	List<SelectItemsList> selectedMenu = itemWrapper.getListOfItems().stream().filter(e->e.getStatus()>0).toList();
    	
    	model.addAttribute("selectedItems",selectedMenu);
    	Integer cost = selectedMenu.stream().map(e->e.getPrice()*e.getStatus()).reduce(0, Integer::sum);
    	String items = StringUtils.join(
    			selectedMenu.stream()
    			.map(e->e.getId())
    			.map(e-> String.valueOf(e))
    			.collect(Collectors.toList())
    			,',');
    	final Authentication curAuth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) curAuth.getPrincipal();
    	billService.saveBill(cost.longValue(), new Date(), currentUser.getEmail(), items);
    	model.addAttribute("cost", cost);
    	return "successMail";
    }
    
    @GetMapping("/offline/pay")
    public String pay(@RequestParam(value = "method", required = false) String method, final Model model) {
    	/*
    	 * itemWrapper to have pay model -> pay by cash or card
    	 * */
    	model.addAttribute("message", "paid successfully!");
        return "successMail";
    }
    
    @RequestMapping(value = "/offline/bookSeat", method = RequestMethod.POST)
    public String bookSeat(@ModelAttribute Seat seat, Model model){
    	/*
    	 * itemWrapper to have pay model -> pay by cash or card
    	 * */
    	Calendar calendar = Calendar.getInstance();
    	calendar.add(Calendar.DAY_OF_MONTH, -2);
    	//check if item wrapper has date after calendar -> success or deny
    	if(seat.getDate().after(calendar.getTime())) {
    		final Authentication curAuth = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = (User) curAuth.getPrincipal();
            seat.setEmail(currentUser.getEmail());
    		bookService.bookSeat(seat);    	
    		model.addAttribute("message", "Booked successfully");
    	}else {
    		model.addAttribute("message", "Cannot book later than 2 days. Please go back and rebook the seat.");
    	}
    	return "successMail";
    }
    
    @GetMapping("/offline/feedback")
    public String feedback(@RequestParam(value = "feedback", required = false) String feedback, final Model model) {
    	/*
    	 * get feedback from itemwrapper
    	 * */
    	
    	model.addAttribute("message", "feedback taken. Thank you for the input");
        return "successMail";
    }
    
}

