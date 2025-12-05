package com.franchise.Entity;

public class LoginResponse {
    private String status;
    private String dbName;

    public LoginResponse(String status, String dbName) {
        this.status = status;
        this.dbName = dbName;
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

    // Getters and setters
    
    
    
}

