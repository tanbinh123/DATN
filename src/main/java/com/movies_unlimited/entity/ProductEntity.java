package com.movies_unlimited.entity;

import com.movies_unlimited.entity.enums.ActiveStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private double price;

    private String description;

    @Enumerated(EnumType.STRING)
    private ActiveStatus status = ActiveStatus.ACTIVE;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private CategoryEntity category;

    @ManyToMany(mappedBy = "products")
    private List<PromotionEntity> promotions;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<FavoriteEntity> favorites;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<CommentEntity> comments;

    private String image;

}
