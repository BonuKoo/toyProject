package com.myboard.toy.domain.user;

import com.myboard.toy.domain.address.Address;
import com.myboard.toy.domain.bucket.dto.Bucket;
import com.myboard.toy.domain.hello.HelloWallet;
import com.myboard.toy.domain.order.Order;
import com.myboard.toy.domain.role.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor @AllArgsConstructor
@Table(name = "users")
@Entity
public class User implements Serializable {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;
    private String username;     //로그인 용 Id
    private String password;    //비밀번호
    private String name;        //이름

    @ManyToMany(fetch = FetchType.LAZY, cascade={CascadeType.MERGE})
    @JoinTable(name = "account_roles", joinColumns = { @JoinColumn(name = "account_id") }, inverseJoinColumns = {
            @JoinColumn(name = "role_id") })
    @ToString.Exclude
    private Set<Role> userRoles = new HashSet<>();



    /*
    * 임시 지갑 역할
     */

    @OneToOne(fetch = FetchType.LAZY)
    private HelloWallet wallet;


    /* 추후 추가 목록 - 세부사항 */

    //private String birthDate;   //생년월일

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Bucket bucket;

    public void setBucket(Bucket bucket) {
        this.bucket = bucket;
        bucket.setUser(this);
    }

    // 장바구니 초기화 메서드
    public void initializeBucket() {
        if (this.bucket != null) {
            this.bucket.getItems().clear();
        } else {
            this.bucket = new Bucket();
            this.bucket.setUser(this);
        }
    }

}
