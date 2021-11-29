package com.surabhi.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.surabhi.persistence.model.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {
	Menu findByPrice(int price);
}