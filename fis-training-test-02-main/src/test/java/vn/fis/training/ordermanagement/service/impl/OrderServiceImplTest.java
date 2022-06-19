package vn.fis.training.ordermanagement.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import vn.fis.training.ordermanagement.domain.*;
import vn.fis.training.ordermanagement.service.OrderService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceImplTest {

    @Autowired
    OrderService orderService;

    Order orderNotExistInDB;

    @BeforeEach
    void init() {
        orderNotExistInDB = new Order(10L,
                LocalDateTime.of(2022,06,18,00,00,00));
    }

    @Test
    @Transactional
    @Rollback
    void createOrder() {
        // size = 7 in db, expected 8 after adding
        int expectedSize = 8;

        orderService.createOrder(orderNotExistInDB);
        int actualSize = orderService.findAll().size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    @Transactional
    @Rollback
    void addOrderItem() {
        Customer customer = new Customer(1L,"Thao","0125416879","nam dinh");
        Order order = new Order(1L, LocalDateTime.of(2022,06,18,00,00,00),
                customer , 100000.0, OrderStatus.PAID);
        Product product = new Product(5L, "quay", 40000D);
        OrderItem orderItem = new OrderItem(7L, order , product, 10, 50.0);

        orderService.addOrderItem(1L, orderItem);
    }

    @Test
    @Transactional
    @Rollback
    void removeOrderItem() {
        Customer customer = new Customer(1L,"Thao","0125416879","nam dinh");
        Order order = new Order(1L, LocalDateTime.of(2022,06,18,00,00,00),
                customer , 100000.0, OrderStatus.PAID);
        Product product = new Product(5L, "quay", 40000D);
        OrderItem orderItem = new OrderItem(5L, order , product, 10, 50.0);

        orderService.removeOrderItem(1L, orderItem);
    }

    @Test
    @Transactional
    @Rollback
    void updateOrderStatus() {
        Order order = new Order (2L); //WAITING_APPROVAL
        OrderStatus expectedStatus = orderService.findById(2L).get().getStatus();

        OrderStatus actualStatus = orderService.updateOrderStatus(order, OrderStatus.PAID).getStatus();

        assertNotEquals(expectedStatus, actualStatus);
        assertEquals(OrderStatus.PAID, actualStatus);
    }

    @Test
    void findOrdersBetween_Between() {
        LocalDateTime fromDateTime = LocalDateTime.of(2020,06,18,00,00,00);
        LocalDateTime toDateTime = LocalDateTime.of(2030,06,18,00,00,00);
        int expectedOrderListSize = 7;

        List<Order> actualOrderList = orderService.findOrdersBetween(fromDateTime, toDateTime);
        int actualOrderListSize = actualOrderList.size();

        assertEquals(expectedOrderListSize, actualOrderListSize);
    }

    @Test
    void findOrdersBetween_NotBetween() {
        LocalDateTime fromDateTime = LocalDateTime.of(2025,06,18,00,00,00);
        LocalDateTime toDateTime = LocalDateTime.of(2030,06,18,00,00,00);
        int expectedOrderListSize = 0;

        List<Order> actualOrderList = orderService.findOrdersBetween(fromDateTime, toDateTime);
        int actualOrderListSize = actualOrderList.size();

        assertEquals(expectedOrderListSize, actualOrderListSize);
    }

    @Test
    void findWaitingApprovalOrders() {
        int expectedOrderListSize = 1;

        List<Order> actualOrderList = orderService.findWaitingApprovalOrders();
        int actualOrderListSize = actualOrderList.size();

        assertEquals(expectedOrderListSize, actualOrderListSize);
    }

    @Test
    void findOrdersByOrderStatus() {
        int expectedOrderListSize = 4;

        List<Order> actualOrderList = orderService.findOrdersByOrderStatus(OrderStatus.PAID);
        int actualOrderListSize = actualOrderList.size();

        assertEquals(expectedOrderListSize, actualOrderListSize);
    }

    @Test
    void findOrdersByCustomer_Exist() {
        Customer customer = new Customer(1L);
        int expectedOrderListSize = 4;

        List<Order> actualOrderList = orderService.findOrdersByCustomer(customer);
        int actualOrderListSize = actualOrderList.size();

        assertEquals(expectedOrderListSize, actualOrderListSize);
    }

    @Test
    void findOrdersByCustomer_NotExist() {
        Customer customer = new Customer(100L);
        int expectedOrderListSize = 0;

        List<Order> actualOrderList = orderService.findOrdersByCustomer(customer);
        int actualOrderListSize = actualOrderList.size();

        assertEquals(expectedOrderListSize, actualOrderListSize);
    }


}