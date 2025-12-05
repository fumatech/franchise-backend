package com.franchise.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.franchise.Entity.Permission;
import com.franchise.Repository.PermissionRepo;
import com.franchise.Service.PermissionService;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionRepo permissionRepo;

	@Override
	public List<Permission> getAllPermissions() {
		return permissionRepo.findAll();
	}

	@Override
	public Permission savePermission(Permission permission) {
		return permissionRepo.save(permission);
	}

	@Override
	public Permission getPermissionById(Long id) {
		return permissionRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Permission not found with id " + id));
	}
}
