package com.security.jwt.service;

import com.security.jwt.dto.DtoDepartment;
import com.security.jwt.dto.DtoEmployee;
import com.security.jwt.model.Department;
import com.security.jwt.model.Employee;
import com.security.jwt.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements IEmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public DtoEmployee findEmployeeById(Long id) {
        DtoEmployee dtoEmployee = new DtoEmployee();
        DtoDepartment dtoDepartment = new DtoDepartment();
        Optional<Employee> employee = employeeRepository.findById(id);

        if(employee.isPresent()) {
            return null;
        }

        Employee employeeFinded= employee.get();
        Department department = employeeFinded.getDepartment();

        BeanUtils.copyProperties(employeeFinded, dtoEmployee);
        BeanUtils.copyProperties(department, dtoDepartment);

        dtoEmployee.setDepartment(dtoDepartment);

        return dtoEmployee;
    }
}
