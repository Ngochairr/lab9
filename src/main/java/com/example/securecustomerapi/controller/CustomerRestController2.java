package com.example.securecustomerapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.securecustomerapi.dto.CustomerResponseDTO;
import com.example.securecustomerapi.service.CustomerService;

@RestController
@RequestMapping("/api/v2/customers")
@CrossOrigin(origins = "*")
public class CustomerRestController2 {

    private final CustomerService customerService;

    @Autowired
    public CustomerRestController2(CustomerService customerService) {
        this.customerService = customerService;
    }

    // GET all customers with sorting (no pagination)
    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers(
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        List<CustomerResponseDTO> customers = customerService.getAllCustomers(sort);
        return ResponseEntity.ok(customers);
    }

    // Other endpoints can include enhancements like filtering, aggregated data, etc.
}