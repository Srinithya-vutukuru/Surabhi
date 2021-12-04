package com.surabhi.service;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.surabhi.persistence.model.AllOrders;
import com.surabhi.persistence.model.AllOrdersByCity;
import com.surabhi.persistence.model.MaxSales;
import com.surabhi.persistence.model.NewLocationToken;
import com.surabhi.persistence.model.PasswordResetToken;
import com.surabhi.persistence.model.Privilege;
import com.surabhi.persistence.model.Role;
import com.surabhi.persistence.model.User;
import com.surabhi.persistence.model.VerificationToken;
import com.surabhi.web.dto.UserDto;

public interface IAdminService {

    User registerNewUserAccount(UserDto accountDto);

    User getUser(String verificationToken);

    void saveRegisteredUser(User user);

    void deleteUser(User user);

    void createVerificationTokenForUser(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    VerificationToken generateNewVerificationToken(String token);

    void createPasswordResetTokenForUser(User user, String token);

    User findUserByEmail(String email);

    PasswordResetToken getPasswordResetToken(String token);

    Optional<User> getUserByPasswordResetToken(String token);

    Optional<User> getUserByID(long id);

    void changeUserPassword(User user, String password);

    boolean checkIfValidOldPassword(User user, String password);

    String validateVerificationToken(String token);

    String generateQRUrl(User user) throws UnsupportedEncodingException;

    User updateUser2FA(boolean use2FA);

    List<String> getUsersFromSessionRegistry();

    NewLocationToken isNewLoginLocation(String username, String ip);

    String isValidNewLocationToken(String token);

    void addUserLocation(User user, String ip);
    
    User updateUserIfFound(String email, String firstName, String lastName, String password, Collection<Role> roles);
    
    User createUserIfNotFound(String email, String firstName, String lastName, String password, Collection<Role> roles);

	Privilege createPrivilegeIfNotFound(String name);

	Role createRoleIfNotFound(String name, Collection<Privilege> privileges);

	List<User> findAll();

	List<MaxSales> findByMonth(String month);

	List<MaxSales> findByYear(String year);

	List<AllOrdersByCity> findAllOrders(String city);
}