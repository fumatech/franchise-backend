package com.franchise.ServiceImpl;

import com.franchise.Entity.Department;
import com.franchise.Repository.DepartmentRepo;
import com.franchise.Service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepo departmentRepo;

    @Override
    public Department saveDepartment(Department department) {
        if (departmentRepo.existsByDepartmentId(department.getDepartmentId())) {
            throw new IllegalArgumentException("Department ID " + department.getDepartmentId() + " already exists.");
        }
        return departmentRepo.save(department);
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepo.findAll();
    }

    @Override
    public Department updateDepartment(Long id, Department department) {
        Optional<Department> existingDepartment = departmentRepo.findById(id);
        if (!existingDepartment.isPresent()) {
            throw new IllegalArgumentException("Department with ID " + id + " does not exist.");
        }
        if (!existingDepartment.get().getDepartmentId().equals(department.getDepartmentId())
                && departmentRepo.existsByDepartmentId(department.getDepartmentId())) {
            throw new IllegalArgumentException("Department ID " + department.getDepartmentId() + " already exists.");
        }
        department.setId(id);
        return departmentRepo.save(department);
    }

    @Override
    public Department getDepartmentById(Long id) {
        return departmentRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Department with ID " + id + " not found"));
    }

    @Override
    public void deleteDepartmentById(Long id) {
        departmentRepo.deleteById(id);
    }
}
