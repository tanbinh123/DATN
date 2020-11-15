package com.movies_unlimited.entity;

import com.movies_unlimited.entity.enums.OrderStatus;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "orders")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity implements Serializable {

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    @ToString.Exclude
    Set<OrderDetailEntity> orderDetails;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    @ToString.Exclude
    private AccountEntity account;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "order_date", nullable = false)
    private Date orderDate;
    @Column(name = "total_price", nullable = false)
    private double totalPrice;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "shipping_id", nullable = false)
    private ShippingEntity shipping;

    public double getTotal() {
        double total = 0;
        for (OrderDetailEntity orderDetail : orderDetails) {
            total += orderDetail.getTotal();
        }

        return total;
    }

    public String getProductsFormat() {
        String out = "";
        int i=0;
        for (OrderDetailEntity entity:orderDetails
             ) {
            out += "x" + entity.getQuantity() +  ", " + entity.getProduct().getName();
            i++;
            if (i != (orderDetails.size() - 1)) {
                out += "<br />";
            }
        }
        return out;
    }
}
