package vn.fis.training.ordermanagement.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.fis.training.ordermanagement.domain.Customer;
import vn.fis.training.ordermanagement.domain.Order;
import vn.fis.training.ordermanagement.domain.OrderItem;
import vn.fis.training.ordermanagement.domain.OrderStatus;
import vn.fis.training.ordermanagement.repository.OrderItemRepository;
import vn.fis.training.ordermanagement.repository.OrderRepository;
import vn.fis.training.ordermanagement.service.OrderService;

import javax.management.InvalidAttributeValueException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;


@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order addOrderItem(Long orderId, OrderItem orderItem) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        Optional<Order> orderInOrderItemOptional = orderRepository.findById(orderItem.getOrder().getId());
        if (!orderOptional.isPresent() || !orderInOrderItemOptional.isPresent()) {
            throw new NoSuchElementException("No such order_id");
        }
        if (!Objects.equals(orderItem.getOrder().getId(), orderId)) {
            throw new NoSuchElementException("No such order_id");
        }
        Order order = orderOptional.get();
        Double minusAmount = orderItem.getProduct().getPrice() * orderItem.getQuantity();
        order.setTotalAmount( order.getTotalAmount() + minusAmount);
        order.getOrderItems().add(orderItem);
        orderItemRepository.save(orderItem);
        orderRepository.save(order);


        return order;
    }

    @Override
    public Order removeOrderItem(Long orderId, OrderItem orderItem) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        Optional<Order> orderInOrderItemOptional = orderRepository.findById(orderItem.getOrder().getId());
        if (!orderOptional.isPresent() || !orderInOrderItemOptional.isPresent()) {
            throw new NoSuchElementException("No such order_id");
        }
        if (!Objects.equals(orderItem.getOrder().getId(), orderId)) {
            throw new NoSuchElementException("No such order_id");
        }
        Order order = orderOptional.get();
        Double minusAmount = orderItem.getProduct().getPrice() * orderItem.getQuantity();
        order.setTotalAmount( order.getTotalAmount() - minusAmount);
        order.getOrderItems().remove(orderItem);
        orderRepository.save(order);
        orderItemRepository.delete(orderItem);

        return order;
    }

    @Override
    public Order updateOrderStatus(Order order, OrderStatus orderStatus) {
        Optional<Order> optionalOrder = orderRepository.findById(order.getId());
        if (!optionalOrder.isPresent()) {
            throw new NoSuchElementException("No such order with order id = "+order.getId());
        }
        else {
            Order updatingOrder = optionalOrder.get();
            updatingOrder.setStatus(orderStatus);
            orderRepository.save(updatingOrder);
        }
        return optionalOrder.get();
    }

    @Override
    public List<Order> findOrdersBetween(LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        return orderRepository.findByOrderDateTimeBetween(fromDateTime, toDateTime);
    }

    @Override
    public List<Order> findWaitingApprovalOrders() {
        return orderRepository.findByStatusEquals(OrderStatus.WAITING_APPROVAL);
    }

    @Override
    public List<Order> findOrdersByOrderStatus(OrderStatus orderStatus) {
        return orderRepository.findByStatusEquals(orderStatus);
    }

    @Override
    public List<Order> findOrdersByCustomer(Customer customer) {
        List<Order> orderListByCustomer = orderRepository.findByCustomerIdEquals(customer.getId());
        if (orderListByCustomer== null) {
            throw new NoSuchElementException("This customer with id = "+customer.getId()+" never orders anything!");
        }
        else {
            return orderListByCustomer;
        }
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }
}
