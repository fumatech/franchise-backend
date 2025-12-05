package com.franchise.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.franchise.Entity.LoginResponse;
import com.franchise.Service.LoginService;
import com.franchise.Service.MainDbService;
import com.franchise.Tenant.TenantContext;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private MainDbService mainDbService;

    @Override
    public LoginResponse login(String email, String password) {
        String dbName = mainDbService.validateAndGetFranchiseDb(email, password);

        if (dbName != null) {
            TenantContext.setCurrentTenant(dbName); // 💡 Dynamic DB switch
            return new LoginResponse("success", dbName);
        }

        return new LoginResponse("failed", null);
    }
}

