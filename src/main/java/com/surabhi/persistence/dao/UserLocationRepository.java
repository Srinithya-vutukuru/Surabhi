package com.surabhi.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.surabhi.persistence.model.User;
import com.surabhi.persistence.model.UserLocation;

public interface UserLocationRepository extends JpaRepository<UserLocation, Long> {
    UserLocation findByCountryAndUser(String country, User user);

}
