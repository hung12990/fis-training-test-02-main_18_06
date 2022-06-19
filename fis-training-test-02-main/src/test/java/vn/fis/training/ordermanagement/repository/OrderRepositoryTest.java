package vn.fis.training.ordermanagement.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vn.fis.training.ordermanagement.domain.Order;
import vn.fis.training.ordermanagement.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Test
    void findByOrderDateTimeBetween_Between() {
        LocalDateTime fromDateTime = LocalDateTime.of(2020,06,18,00,00,00);
        LocalDateTime toDateTime = LocalDateTime.of(2030,06,18,00,00,00);
        int expectedOrderListSize = 7;

        List<Order> actualOrderList = orderRepository.findByOrderDateTimeBetween(fromDateTime, toDateTime);
        int actualOrderListSize = actualOrderList.size();

        assertEquals(expectedOrderListSize, actualOrderListSize);
    }

    @Test
    void findByOrderDateTimeBetween_NotBetween() {
        LocalDateTime fromDateTime = LocalDateTime.of(2025,06,18,00,00,00);
        LocalDateTime toDateTime = LocalDateTime.of(2030,06,18,00,00,00);
        int expectedOrderListSize = 0;

        List<Order> actualOrderList = orderRepository.findByOrderDateTimeBetween(fromDateTime, toDateTime);
        int actualOrderListSize = actualOrderList.size();

        assertEquals(expectedOrderListSize, actualOrderListSize);
    }

    @Test
    void findByStatusEquals() {
        int expectedOrderListSize = 4;

        List<Order> actualOrderList = orderRepository.findByStatusEquals(OrderStatus.PAID);
        int actualOrderListSize = actualOrderList.size();

        assertEquals(expectedOrderListSize, actualOrderListSize);
    }

    @Test
    void findByCustomerIdEquals_Exist() {
        int expectedOrderListSize = 4;

        List<Order> actualOrderList = orderRepository.findByCustomerIdEquals(1L);
        int actualOrderListSize = actualOrderList.size();

        assertEquals(expectedOrderListSize, actualOrderListSize);
    }

    @Test
    void findByCustomerIdEquals_NotExist() {
        int expectedOrderListSize = 0;

        List<Order> actualOrderList = orderRepository.findByCustomerIdEquals(100L);
        int actualOrderListSize = actualOrderList.size();

        assertEquals(expectedOrderListSize, actualOrderListSize);
    }
}