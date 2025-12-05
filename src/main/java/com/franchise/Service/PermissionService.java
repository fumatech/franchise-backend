package com.franchise.Service;

import java.util.List;

import com.franchise.Entity.Permission;
 
public interface PermissionService {

	List<Permission> getAllPermissions();

	Permission savePermission(Permission permission);

	Permission getPermissionById(Long id);

}

  