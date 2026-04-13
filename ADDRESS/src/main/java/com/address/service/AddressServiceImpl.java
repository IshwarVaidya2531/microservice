package com.address.service;

import com.address.entity.dto.AddressDto;
import com.address.entity.dto.AddressRequest;

import java.util.List;

public interface AddressServiceImpl {

    public List<AddressDto> getAddresses();
    public AddressDto getAddressById(long id);
    public List<AddressDto> getAddressesByEmpId(long empId);
    public List<AddressDto> saveAddress(AddressRequest request);
    public List<AddressDto> updateAddress(AddressRequest request);
    public void deleteAddress(long id);
}
