package com.employee.service.impl;

import com.employee.exceptions.NotFoundException;
import com.employee.model.dto.EmployeeDto;
import com.employee.model.entity.Employee;
import com.employee.repository.EmployeeRepository;
import com.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final ModelMapper modelMapper;
    private Long id;

    @Override
    public EmployeeDto save(EmployeeDto employeeDto) {
        if (employeeDto.getId() != null) {
            throw new IllegalArgumentException("New employee cannot have an ID");
        }
        Employee mappedEntity = modelMapper.map(employeeDto, Employee.class);
        Employee savedEntity = employeeRepository.save(mappedEntity);
        return modelMapper.map(savedEntity, EmployeeDto.class);
    }

    @Override
    public EmployeeDto update(Long id, EmployeeDto employeeDto) {
        if (id == null || employeeDto.getId() == null) throw new IllegalArgumentException("ID cannot be null for update");
        if (!Objects.equals(id,employeeDto.getId())) throw new IllegalArgumentException("ID in path and body must match for update");
        Employee mappedEntity = modelMapper.map(employeeDto, Employee.class);
        Employee savedEntity = employeeRepository.save(mappedEntity);
       return modelMapper.map(savedEntity, EmployeeDto.class);
    }

    @Override
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new NotFoundException("Employee with ID " + id + " does not exist");
        }
        employeeRepository.deleteById(id);
    }


    @Override
    public EmployeeDto findById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Employee with ID " + id + " does not exist"));
        return modelMapper.map(employee, EmployeeDto.class);
    }

    @Override
    public List<EmployeeDto> findAll() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(employee -> modelMapper.map(employee, EmployeeDto.class)).toList();

    }
}
