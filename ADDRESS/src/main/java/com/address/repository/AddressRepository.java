package com.address.repository;

import com.address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAllByEmpId(long empId);

    boolean existsAddressById(long id);
}
