package com.example.yourhealth.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User extends AbstractEntity implements UserDetails, UserInf {
    private static final long serialVersionUID = 1L;

    public enum Authority {
        ROLE_USER, ROLE_ADMIN
    };

    public User() {
        super();
    }

    public User(String email, String name, String password, BigDecimal targetWeight, Authority authority) {
        this.username = email;
        this.name = name;
        this.password = password;
        this.targetWeight = targetWeight;
        this.authority = authority;
    }

    @Id
    @SequenceGenerator(name = "users_id_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private BigDecimal targetWeight;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Authority authority;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<WeightData> weightData;
    
    public List<WeightData> getWeightData() {
        return weightData;
    }
    public void setWeightData(List<WeightData> weightData) {
        this.weightData = weightData;
    }
    
    public BigDecimal getTargetWeightData() {
        return targetWeight;
    }
    
    public String getPassword() {
        return password;
    }

    
    public void setTargetWeightData(BigDecimal targetWeight) {
    	this.targetWeight = targetWeight;
    }
    

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(authority.toString()));
        return authorities;
    }
    
    

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}