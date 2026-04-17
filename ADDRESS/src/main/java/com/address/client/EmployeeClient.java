package com.address.client;

import com.address.entity.dto.EmployeeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "employeeClient", url = "${employee.service.url}")
public interface EmployeeClient {

    @GetMapping("/{id}")
    EmployeeDto findById(@PathVariable Long id);
}
