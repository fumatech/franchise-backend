package com.franchise.Service;

import com.franchise.Entity.LoginResponse;

public interface LoginService {
    LoginResponse login(String email, String password);
}

