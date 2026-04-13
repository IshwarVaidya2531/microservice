package com.employee.service;

import com.employee.model.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {

    public EmployeeDto save(EmployeeDto employeeDto);
    public EmployeeDto update(Long id,EmployeeDto employeeDto);
    public void deleteEmployee(Long id);
    public EmployeeDto findById(Long id);
    public List<EmployeeDto> findAll();
}
