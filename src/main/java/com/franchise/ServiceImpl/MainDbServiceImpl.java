package com.franchise.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.franchise.Service.MainDbService;

@Service
public class MainDbServiceImpl implements MainDbService {

    @Autowired
    private JdbcTemplate jdbcTemplate; // uses the default main DB

    @Override
    public String validateAndGetFranchiseDb(String email, String password) {
        try {
            String sql = "SELECT franchise_db_name FROM franchise_users WHERE username = ? AND password = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{email, password}, String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}

