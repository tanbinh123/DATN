package com.movies_unlimited.entity;

import com.movies_unlimited.Ultil.NumberUltil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "order_detail")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    public double getTotal() {
        return NumberUltil.roundDoubleMoney(this.product.getPrice() * this.quantity, 2);
    }
}
