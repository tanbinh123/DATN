package com.movies_unlimited.entity;

import com.movies_unlimited.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "account_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRoleEntity implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

}
