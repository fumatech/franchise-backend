package com.franchise.Service;

import java.util.List;
import com.franchise.Entity.Department;

public interface DepartmentService {

    Department saveDepartment(Department department);

    List<Department> getAllDepartments();

    Department updateDepartment(Long id, Department department);

    Department getDepartmentById(Long id);

    void deleteDepartmentById(Long id);
}
