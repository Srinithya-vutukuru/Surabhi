package com.surabhi.security;

public interface ISecurityUserService {

    String validatePasswordResetToken(String token);

}
