package ir.maktab.model;

import ir.maktab.model.enumeration.OrderStatus;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
    @ManyToOne(cascade = CascadeType.ALL)
    private Product product;
    private Integer count;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}
