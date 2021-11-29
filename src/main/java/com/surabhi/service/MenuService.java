package com.surabhi.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.surabhi.persistence.dao.MenuRepository;
import com.surabhi.persistence.model.Menu;
import com.surabhi.persistence.model.SelectItems;
import com.surabhi.persistence.model.SelectItemsList;

@Service
@Transactional
public class MenuService {
	
	@Autowired
	private MenuRepository menuRepository;
	
	public List<SelectItems> getAllItems(){
		return menuRepository.findAll().stream().map(e -> (new SelectItems(e))).toList();
	}
	
	public int getPrice(int id) {
		Menu item = menuRepository.findByPrice(id);
		return item.getPrice();		
	}
	
	public List<SelectItemsList> getAllItemsList(){
		return menuRepository.findAll().stream().map(e -> (new SelectItemsList(e))).toList();
	}

}
