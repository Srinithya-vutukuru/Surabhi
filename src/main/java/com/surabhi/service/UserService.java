package com.surabhi.service;

import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.surabhi.persistence.dao.AllOrderByFilterViewRepository;
import com.surabhi.persistence.dao.AllOrderViewRepository;
import com.surabhi.persistence.model.AllOrders;
import com.surabhi.persistence.model.AllOrdersByFilters;

@Service
@Transactional
public class UserService implements IUserService {
    
    @Autowired
    private AllOrderViewRepository allOrderViewRepository;
    
    @Autowired
    private AllOrderByFilterViewRepository allOrderByFilterViewRepository;
    
    @Override
    public List<AllOrders> findByEmail(String email){
    	return allOrderViewRepository.findByEmail(email);
    }
    
    @Override
    public List<AllOrdersByFilters> findByDate(Date date, String email){
    	return allOrderByFilterViewRepository.findByDate(date).stream().filter(e-> e.getEmail().equalsIgnoreCase(email)).toList();
    }
    
    @Override
    public List<AllOrdersByFilters> findByPrice(Long amount,String email){
    	return allOrderByFilterViewRepository.findByAmount(amount).stream().filter(e-> e.getEmail().equalsIgnoreCase(email)).toList();
    }
}
