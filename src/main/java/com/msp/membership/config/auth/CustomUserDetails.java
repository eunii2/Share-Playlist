package com.msp.membership.config.auth;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.Collections;

@Data
public abstract class CustomUserDetails implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    private int id;
    private String userid;
    private String userpw;
    private String role;


    //계정이 가지고 있는 권한을 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.role));
    }

    public int getId(){
        return this.id;
    }
    public String getUserid() {
        return this.userid;
    }

    //계정이 만료되었는지를 리턴  true : 만료되지 않음
    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    //계정이 잠겨있는지를 리턴
    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    //패스워드가 만료되었는지를 리턴
    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    //계정이 사용가능한지를 리턴
    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }


}