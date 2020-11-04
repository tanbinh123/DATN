package com.movies_unlimited.entity;

import com.movies_unlimited.Ultil.StringUltil;
import com.movies_unlimited.entity.enums.ActiveStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "account")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "account_role_relation",
            joinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<AccountRoleEntity> accountRoles;

    @Column(unique = true, nullable = false)
    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @Column(name = "full_name")
    private String fullName;

    private String address;

    private String phone;

    @Enumerated(EnumType.STRING)
    private ActiveStatus status = ActiveStatus.INACTIVE;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<FavoriteEntity> favorites;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<CommentEntity> comments;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<OrderEntity> orders;

    public String getBirthdayFormat() {
        if (birthday == null) {
            return "";
        }
        return StringUltil.fromDateToUS(birthday);
    }

    public String getRoleString() {

        for (AccountRoleEntity role : accountRoles) {
            if (role != null && role.getName().equals("ROLE_ADMIN")) {
                return "ROLE_ADMIN";
            }
        }
        for (AccountRoleEntity role : accountRoles) {
            if (role != null && role.getName().equals("ROLE_SELLER")) {
                return "ROLE_SELLER";
            }
        }
        return "ROLE_USER";
    }

}
