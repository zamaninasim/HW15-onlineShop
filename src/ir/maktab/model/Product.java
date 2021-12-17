package ir.maktab.model;

import ir.maktab.model.enumeration.ProductType;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private ProductType productType;
    private String name;
    private Long price;
    private Integer count;
}
