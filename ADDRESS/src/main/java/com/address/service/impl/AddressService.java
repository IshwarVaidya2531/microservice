package com.address.service.impl;

import com.address.client.EmployeeClient;
import com.address.entity.Address;
import com.address.entity.dto.AddressDto;
import com.address.entity.dto.AddressRequest;
import com.address.entity.dto.EmployeeDto;
import com.address.exceptions.CustomException;
import com.address.exceptions.NotFoundException;
import com.address.repository.AddressRepository;
import com.address.service.AddressServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService implements AddressServiceImpl {

    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;
    private final EmployeeClient employeeClient;

    @Override
    public List<AddressDto> saveAddress(AddressRequest request) {

        //Checked employee by feign client
        employeeClient.findById(request.getEmpId());

        List<Address> addressesToSave = saveOrUpdateAddresses(request);
        List<Address> addresses = addressRepository.saveAll(addressesToSave);
         return addresses.stream().map(adr ->modelMapper.map(adr, AddressDto.class)).toList();
    }

    @Override
    public List<AddressDto> updateAddress(AddressRequest request) {
        //Checked if employee exist by feign client
        employeeClient.findById(request.getEmpId());
        List<Address> addressesToUpdate = saveOrUpdateAddresses(request);
        List<Long> incomingIds = addressesToUpdate.stream().map(Address::getId).toList();
        List<Long> existingIds = addressRepository.findAllByEmpId(request.getEmpId()).stream().map(Address::getId).toList();

        List<Long> idsToDelete = existingIds.stream().filter(id -> !incomingIds.contains(id)).toList();
        if (!idsToDelete.isEmpty()) {
            addressRepository.deleteAllById(idsToDelete);
        }
        List<Address> addresses = addressRepository.saveAll(addressesToUpdate);
        return addresses.stream().map(adr ->modelMapper.map(adr, AddressDto.class)).toList();
    }

    @Override
    public void deleteAddress(long id) {
        if (addressRepository.existsAddressById(id)){
            addressRepository.deleteById(id);
        }else{
            throw new NotFoundException("Address not found for id: " + id);
        }

    }
    @Override
    public List<AddressDto> getAddresses() {
        List<Address> addresses = addressRepository.findAll();
       return addresses.stream().map(adr ->modelMapper.map(adr, AddressDto.class)).toList();

    }

    @Override
    public AddressDto getAddressById(long id) {
        Address  address= addressRepository.findById(id).orElseThrow(()-> new NotFoundException("Address not found for id: "+id));
        return modelMapper.map(address, AddressDto.class);
    }

    @Override
    public List<AddressDto> getAddressesByEmpId(long empId) {

        //checked if emp exists by feign client
        employeeClient.findById(empId);

        List<Address> addresses = addressRepository.findAllByEmpId(empId);
        return addresses.stream().map(adr ->modelMapper.map(adr, AddressDto.class)).toList();

    }

    private List<Address> saveOrUpdateAddresses(AddressRequest request) {
        List<Address> addresses = new ArrayList<>();
        for (AddressDto addressDto : request.getAddresses()) {
            Address address = new Address();
            address.setId(addressDto.getId() != null ? addressDto.getId() : null);
            address.setCity(addressDto.getCity());
            address.setCountry(addressDto.getCountry());
            address.setStreet(addressDto.getStreet());
            address.setZipCode(addressDto.getZipCode());
            address.setState(addressDto.getState());
            address.setAddressType(addressDto.getAddressType());
            address.setEmpId(request.getEmpId());
            addresses.add(address);

        }
        return addresses;

    }
}
