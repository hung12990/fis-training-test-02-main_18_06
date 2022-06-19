package vn.fis.training.ordermanagement.service;

import vn.fis.training.ordermanagement.domain.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Customer createCustomer(Customer customer);
    Customer updateCustomer(Customer customer);
    void deleteCustomerById(Long customerId);
    void deleteAll();
    List<Customer> findAll();
    Customer findByMobileEquals(String mobileNumber);
    Optional<Customer> findById(Long Id);
}
