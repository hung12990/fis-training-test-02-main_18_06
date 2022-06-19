package vn.fis.training.ordermanagement.domain;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="tbl_order")
@NamedQuery(name = "Order.findByOrderDateTimeBetween",
        query = "select o from Order o where o.orderDateTime between ?1 and ?2")
@NamedQuery(name = "Order.findByStatusEquals",
        query = "select o from Order o where o.status = ?1")
@NamedQuery(name = "Order.findByCustomerEquals",
        query = "select o from Order o where customer_id = ?1")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="order_datetime")
    private LocalDateTime orderDateTime;

    @ManyToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<OrderItem> orderItems;

    @Column(name="totalAmount")
    private Double totalAmount;

    /**
     * Bao gom cac trang thai duoc dinh nghia trong OrderStatus Class
     */
    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public Order() {
    }

    public Order(Long id, LocalDateTime orderDateTime, Customer customer, List<OrderItem> orderItems, Double totalAmount, OrderStatus status) {
        this.id = id;
        this.orderDateTime = orderDateTime;
        this.customer = customer;
        this.orderItems = orderItems;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Order(Long id) {
        this.id = id;
    }

    public Order(Long id, LocalDateTime orderDateTime) {
        this.id = id;
        this.orderDateTime = orderDateTime;
    }

    public Order(Long id, LocalDateTime orderDateTime, Customer customer, Double totalAmount, OrderStatus status) {
        this.id = id;
        this.orderDateTime = orderDateTime;
        this.customer = customer;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderDateTime=" + orderDateTime +
                ", totalAmount=" + totalAmount +
                ", status=" + status +
                '}';
    }
}
