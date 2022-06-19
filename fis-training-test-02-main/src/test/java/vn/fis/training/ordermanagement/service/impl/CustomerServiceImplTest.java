package vn.fis.training.ordermanagement.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import vn.fis.training.ordermanagement.domain.Customer;
import vn.fis.training.ordermanagement.service.CustomerService;

import javax.transaction.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerServiceImplTest {

    @Autowired
    CustomerService customerService;
    Customer customerNotExistInDB;
    Customer customerExistedInDB;
    Customer updatedCustomerExistedInDB;
    Customer customerNoOrder;

    @BeforeEach
    void init() {
        customerExistedInDB = new Customer(1L, "Thao", "0125416879", "nam dinh");
        customerNotExistInDB = new Customer(4L, "Tuan Anh", "0822196869", "nam dinh");
        updatedCustomerExistedInDB = new Customer (1L, "Trang", "86324159", "da nang");
        customerNoOrder = new Customer(3L, "Nam", "0911928467", "TPHCM");
    }

    @Test
    @Transactional
    @Rollback
    void createCustomer() {
        // size = 3 in db, expected 4 after adding
        int expectedSize = 4;

        customerService.createCustomer(customerNotExistInDB);
        int actualSize = customerService.findAll().size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    @Transactional
    @Rollback
    void updateCustomer_Exist() {
        Customer oldCustomer = customerExistedInDB;
        Customer newCustomer = updatedCustomerExistedInDB;

        customerService.updateCustomer(newCustomer);
        Customer updatedCustomer = customerService.findById(newCustomer.getId()).get();

        assertNotSame(oldCustomer, updatedCustomer);
    }

    @Test
    @Transactional
    @Rollback
    void updateCustomer_NotExist() {
        //id = 10 is not exist in db
        Customer newCustomer = customerNotExistInDB;

        Executable ex = () -> customerService.updateCustomer(newCustomer);

        assertThrows(NoSuchElementException.class, ex);
    }

    @Test
    @Transactional
    @Rollback
    void deleteCustomerById_NoOrder() {
        // size = 3 in db, expected 2 after deleting
        int expectedSize = 2;

        customerService.deleteCustomerById(customerNoOrder.getId());
        int actualSize = customerService.findAll().size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    @Transactional
    @Rollback
    void deleteCustomerById_Ordered() {
        // size = 3 in db, expected 2 after deleting
        int expectedSize = 2;

        customerService.deleteCustomerById(customerExistedInDB.getId());
        int actualSize = customerService.findAll().size();

        assertEquals(expectedSize, actualSize);
    }

    @Test

    void deleteAll() {
        // size = 3 in db, expected 0 after deleting
        int expectedSize = 0;

        customerService.deleteAll();
        int actualSize = customerService.findAll().size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    void findAll() {
        int expectedSize = 3;

        int actualSize = customerService.findAll().size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    void findByMobileEquals_Exist() {
        Customer expectedCustomer = customerExistedInDB;

        Customer actualCustomer = customerService.findByMobileEquals(customerExistedInDB.getMobile());

        assertEquals(expectedCustomer, actualCustomer);
    }

    @Test
    void findByMobileEquals_NotExist() {
        Executable ex = () -> customerService.findByMobileEquals("0123456789");
        assertThrows(NoSuchElementException.class, ex);
    }

    @Test
    void findById_Exist() {
        Customer expectedCustomer = customerExistedInDB;

        Optional<Customer> actualCustomer = customerService.findById(expectedCustomer.getId());

        assertEquals(expectedCustomer, actualCustomer.get());
    }

    @Test
    void findById_NotExist() {
        Customer expectedCustomer = customerNotExistInDB;

        Optional<Customer> actualCustomer = customerService.findById(customerNotExistInDB.getId());

        assertTrue(!actualCustomer.isPresent());
    }
}