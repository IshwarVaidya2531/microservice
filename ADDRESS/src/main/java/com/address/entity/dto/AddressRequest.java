package com.address.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class AddressRequest {


    private long id;
    private long empId;

    private List<AddressDto> addresses;

}
