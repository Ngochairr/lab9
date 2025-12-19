package com.example.securecustomerapi.service;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.securecustomerapi.dto.CustomerRequestDTO;
import com.example.securecustomerapi.dto.CustomerResponseDTO;
import com.example.securecustomerapi.dto.CustomerUpdateDTO;
import com.example.securecustomerapi.entity.Customer;
import com.example.securecustomerapi.entity.CustomerStatus;
import com.example.securecustomerapi.exception.DuplicateResourceException;
import com.example.securecustomerapi.exception.ResourceNotFoundException;
import com.example.securecustomerapi.repository.CustomerRepository;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    
    private final CustomerRepository customerRepository;
    
    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    
    @Override
    public List<CustomerResponseDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public CustomerResponseDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        return convertToResponseDTO(customer);
    }
    
    @Override
    public CustomerResponseDTO createCustomer(CustomerRequestDTO requestDTO) {
        // Check for duplicates
        if (customerRepository.existsByCustomerCode(requestDTO.getCustomerCode())) {
            throw new DuplicateResourceException("Customer code already exists: " + requestDTO.getCustomerCode());
        }
        
        if (customerRepository.existsByEmail(requestDTO.getEmail())) {
            throw new DuplicateResourceException("Email already exists: " + requestDTO.getEmail());
        }
        
        // Convert DTO to Entity
        Customer customer = convertToEntity(requestDTO);
        
        // Save to database
        Customer savedCustomer = customerRepository.save(customer);
        
        // Convert Entity to Response DTO
        return convertToResponseDTO(savedCustomer);
    }
    
    @Override
    public CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO requestDTO) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        if (requestDTO.getStatus() != null) {
            existingCustomer.setStatus(CustomerStatus.valueOf(requestDTO.getStatus().toUpperCase()));
        }
        // Check if email is being changed to an existing one
        if (!existingCustomer.getEmail().equals(requestDTO.getEmail()) 
            && customerRepository.existsByEmail(requestDTO.getEmail())) {
            throw new DuplicateResourceException("Email already exists: " + requestDTO.getEmail());
        }
        
        // Update fields
        existingCustomer.setFullName(requestDTO.getFullName());
        existingCustomer.setEmail(requestDTO.getEmail());
        existingCustomer.setPhone(requestDTO.getPhone());
        existingCustomer.setAddress(requestDTO.getAddress());
        
        // Don't update customerCode (immutable)
        
        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return convertToResponseDTO(updatedCustomer);
    }
    
    @Override
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }
    
    @Override
    public List<CustomerResponseDTO> searchCustomers(String keyword) {
        return customerRepository.searchCustomers(keyword)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerResponseDTO> advancedsearchCustomers(String name, String email, String status) {
        return customerRepository.advancedsearchCustomers(name, email, status)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
   @Override
    public List<CustomerResponseDTO> getCustomersByStatus(String status) {
        CustomerStatus customerStatus;
        try {
            customerStatus = CustomerStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + status);
        }

        return customerRepository.findAllByStatus(customerStatus)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    @Override
    public Page<CustomerResponseDTO> getAllCustomers(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Customer> customers = customerRepository.findAll(pageable);
        return customers.map(this::convertToResponseDTO);
    }
    // Helper Methods for DTO Conversion
    
    private CustomerResponseDTO convertToResponseDTO(Customer customer) {
        CustomerResponseDTO dto = new CustomerResponseDTO();
        dto.setId(customer.getId());
        dto.setCustomerCode(customer.getCustomerCode());
        dto.setFullName(customer.getFullName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        dto.setAddress(customer.getAddress());
        dto.setStatus(customer.getStatus().toString());
        dto.setCreatedAt(customer.getCreatedAt());
        return dto;
    }
    
    private Customer convertToEntity(CustomerRequestDTO dto) {
        Customer customer = new Customer();
        customer.setCustomerCode(dto.getCustomerCode());
        customer.setFullName(dto.getFullName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setAddress(dto.getAddress());
        return customer;
    }
    @Override
    public List<CustomerResponseDTO> getAllCustomers(Sort sort) {
        return customerRepository.findAll(sort)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<CustomerResponseDTO> getAllCustomers(int page, int size, Sort sort) {
        PageRequest pageable = PageRequest.of(page, size, sort);
        Page<Customer> customers = customerRepository.findAll(pageable);
        return customers.map(this::convertToResponseDTO);
    }

    public CustomerResponseDTO partialUpdateCustomer(Long id, CustomerUpdateDTO updateDTO) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));

        // Only update fields that are non-null
        if (updateDTO.getFullName() != null) {
            customer.setFullName(updateDTO.getFullName());
        }
        if (updateDTO.getEmail() != null) {
            // Check for duplicate email
            if (!customer.getEmail().equals(updateDTO.getEmail()) && customerRepository.existsByEmail(updateDTO.getEmail())) {
                throw new DuplicateResourceException("Email already exists: " + updateDTO.getEmail());
            }
            customer.setEmail(updateDTO.getEmail());
        }
        if (updateDTO.getPhone() != null) {
            customer.setPhone(updateDTO.getPhone());
        }
        if (updateDTO.getAddress() != null) {
            customer.setAddress(updateDTO.getAddress());
        }

        Customer updatedCustomer = customerRepository.save(customer);
        return convertToResponseDTO(updatedCustomer);
    }
}
