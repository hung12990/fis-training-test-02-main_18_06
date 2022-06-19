package vn.fis.training.ordermanagement.domain;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="tbl_customer")
@NamedQuery(name = "Customer.findByMobileEquals",
        query = "select c from Customer c where c.mobile = ?1")
//@NamedQuery(name = "Customer.deleteExistedCustomer",
//        query = "delete from Customer c where c.id not in (select o.customer_id from Order o on c.id = o.customer_id)")
//DELETE FROM customer WHERE customer.id NOT IN
//        (SELECT order.customer_id from order where order.customer_id = c.id)
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="mobile")
    private String mobile;

    @Column(name="address")
    private String address;

    public Customer(long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Customer(Long id, String name, String mobile, String address) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.address = address;
    }

    public Customer() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return id.equals(customer.id) && name.equals(customer.name) && mobile.equals(customer.mobile) && address.equals(customer.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, mobile, address);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
