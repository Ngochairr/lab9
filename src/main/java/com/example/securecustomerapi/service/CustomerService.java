package com.example.securecustomerapi.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import com.example.securecustomerapi.dto.CustomerRequestDTO;
import com.example.securecustomerapi.dto.CustomerResponseDTO;
import com.example.securecustomerapi.dto.CustomerUpdateDTO;

public interface CustomerService {
    
    List<CustomerResponseDTO> getAllCustomers();
    
    CustomerResponseDTO getCustomerById(Long id);
    
    CustomerResponseDTO createCustomer(CustomerRequestDTO requestDTO);
    
    CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO requestDTO);
    
    void deleteCustomer(Long id);
    
    List<CustomerResponseDTO> searchCustomers(String keyword);

    List<CustomerResponseDTO> advancedsearchCustomers(String name, String email, String status);
    
    List<CustomerResponseDTO> getCustomersByStatus(String status);

    Page<CustomerResponseDTO> getAllCustomers(int page, int size);
    
    List<CustomerResponseDTO> getAllCustomers(Sort sort);

    Page<CustomerResponseDTO> getAllCustomers(int page, int size, Sort sort);

    CustomerResponseDTO partialUpdateCustomer(Long id, CustomerUpdateDTO updateDTO);
    
}