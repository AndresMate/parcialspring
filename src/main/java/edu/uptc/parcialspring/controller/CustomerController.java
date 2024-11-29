package edu.uptc.parcialspring.controller;

import edu.uptc.parcialspring.entities.Customer;
import edu.uptc.parcialspring.service.CustomerService;
import edu.uptc.parcialspring.handling.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllCustomers() {
        try {
            List<Customer> result = customerService.getAllCustomers();
            return ResponseHandler.generateResponse("Customers retrieved successfully", HttpStatus.OK, result);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Error", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCustomerById(@PathVariable Long id) {
        try {
            Optional<Customer> result = customerService.getCustomerById(id);
            return result.map(value -> ResponseHandler.generateResponse("Customer found", HttpStatus.OK, value))
                    .orElseGet(() -> ResponseHandler.generateResponse("Customer not found", HttpStatus.NOT_FOUND, null));
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Error", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> saveCustomer(@RequestBody Customer customer) {
        try {
            Customer result = customerService.saveCustomer(customer);
            return ResponseHandler.generateResponse("Customer created successfully", HttpStatus.CREATED, result);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Error", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        try {
            Optional<Customer> result = customerService.updateCustomer(id, customer);
            return result.map(value -> ResponseHandler.generateResponse("Customer updated successfully", HttpStatus.OK, value))
                    .orElseGet(() -> ResponseHandler.generateResponse("Customer not found", HttpStatus.NOT_FOUND, null));
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Error", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable Long id) {
        try {
            boolean deleted = customerService.deleteCustomer(id);
            if (deleted) {
                return ResponseHandler.generateResponse("Customer deleted successfully", HttpStatus.NO_CONTENT, null);
            } else {
                return ResponseHandler.generateResponse("Customer not found", HttpStatus.NOT_FOUND, null);
            }
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Error", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
