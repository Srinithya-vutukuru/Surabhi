package com.surabhi.web.controller;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.surabhi.persistence.model.ItemWrapper;
import com.surabhi.persistence.model.SelectItems;
import com.surabhi.persistence.model.User;
import com.surabhi.service.BillService;
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
import org.thymeleaf.util.StringUtils;

@Controller
public class MenuController {
    
    @Autowired
    MenuService menuService;
    
    @Autowired
    BillService billService;
    
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
    
}

