package vn.fis.training.ordermanagement.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vn.fis.training.ordermanagement.domain.Customer;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void findByMobileEquals_Exist() {
        Customer expectedCustomer = new Customer(1L, "Thao", "0125416879", "nam dinh");

        Optional<Customer> optionalCustomer = customerRepository.findByMobileEquals("0125416879");
        Customer actualCustomer = optionalCustomer.get();

        assertEquals(expectedCustomer, actualCustomer);
    }

    @Test
    void findByMobileEquals_NotExist() {
        Customer expectedCustomer = null;

        Optional<Customer> actualCustomer = customerRepository.findByMobileEquals("0123456789");

        assertTrue(!actualCustomer.isPresent());
    }
}