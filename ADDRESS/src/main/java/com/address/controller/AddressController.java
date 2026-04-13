package com.address.controller;

import com.address.entity.dto.AddressDto;
import com.address.entity.dto.AddressRequest;
import com.address.response.Response;
import com.address.service.impl.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/addresses")
@RestController
@RequiredArgsConstructor
public class AddressController {


    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<Response<List<AddressDto>>> saveAddress(AddressRequest request) {
       return new ResponseEntity<>(new Response<>(HttpStatus.CREATED.value(), "Addresses saved successfully",addressService.saveAddress(request),true),HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<Response<List<AddressDto>>> updateAddress(AddressRequest request) {
        return new ResponseEntity<>(new Response<>(HttpStatus.OK.value(), "Addresses updated successfully",addressService.updateAddress(request),true),HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<Response<List<AddressDto>>> getAddresses() {
        return new  ResponseEntity<>(new Response<>(HttpStatus.OK.value(), "Addresses  fetched successfully", addressService.getAddresses(),true),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<AddressDto>> getAddress(@PathVariable long id) {
        return new ResponseEntity<>(new Response<>(HttpStatus.OK.value(),"Address fetched Successfully",addressService.getAddressById(id),true),HttpStatus.OK);
    }

    @GetMapping("/{empId}")
    public ResponseEntity<Response<List<AddressDto>>> getAddressByEmpId(@PathVariable long empId) {
        return new ResponseEntity<>(new Response<>(HttpStatus.OK.value(),"Address fetched Successfully",addressService.getAddressesByEmpId(empId),true),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Object>> deleteAddress(@PathVariable long id) {
        addressService.deleteAddress(id);
        return new ResponseEntity<>(new Response<>(HttpStatus.OK.value(),"Address deleted Successfully",null,true),HttpStatus.OK);

    }
}
