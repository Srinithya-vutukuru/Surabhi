package com.surabhi.web.controller;

import java.util.concurrent.ThreadLocalRandom;
import com.surabhi.service.BillService;
import com.surabhi.service.BookService;
import com.surabhi.service.MenuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FestiveOfferController {
    
    @Autowired
    MenuService menuService;
    
    @Autowired
    BillService billService;
    
    @Autowired
    BookService bookService;
    
    @GetMapping("/festive/offer")
    public String offer(@RequestParam(value = "offer", required = false) Boolean offer,
    		@RequestParam(value = "cost", required = false) int cost,
    		final Model model) {
    	/*
    	 * itemWrapper to have pay model -> pay by cash or card
    	 * */
    	int randomNum = ThreadLocalRandom.current().nextInt(0, 1000 + 1);
    	if(randomNum%2==0) {
    		model.addAttribute("cost", cost/2);
    		model.addAttribute("message", "Congratulations you got 50% discount");
    	}
    	else {
    		model.addAttribute("cost", cost);
    		model.addAttribute("message", "Better Luck Next Time");
    	}
        return "successMail";
    }    
}

