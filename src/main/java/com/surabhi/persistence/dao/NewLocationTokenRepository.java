package com.surabhi.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.surabhi.persistence.model.NewLocationToken;
import com.surabhi.persistence.model.UserLocation;

public interface NewLocationTokenRepository extends JpaRepository<NewLocationToken, Long> {

    NewLocationToken findByToken(String token);

    NewLocationToken findByUserLocation(UserLocation userLocation);

}
