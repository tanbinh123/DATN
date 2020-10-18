package com.movies_unlimited.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "verify")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerifyEmailEntity implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(nullable = false,unique = true)
    private String email;
    
    @Column(nullable = false)
    private String code;

}
