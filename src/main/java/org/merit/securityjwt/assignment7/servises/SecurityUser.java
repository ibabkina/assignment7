package org.merit.securityjwt.assignment7.servises;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.merit.securityjwt.assignment7.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUser implements UserDetails {

    private final User user;

    public SecurityUser(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	List<GrantedAuthority> listAuthorities = new ArrayList<GrantedAuthority>();
//    	List authorities = new ArrayList<>();
//    	authorities.add(user.getRole());
//    	listAuthorities.add(authorities);    //(user.getRole());  //addAll(authorities) if a list;
        return listAuthorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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
