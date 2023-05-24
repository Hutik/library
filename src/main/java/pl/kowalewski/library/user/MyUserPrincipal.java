package pl.kowalewski.library.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import pl.kowalewski.library.user.role.Role;

public class MyUserPrincipal implements UserDetails{
    private User user;
    private List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
    
    public MyUserPrincipal(User user) {
        this.user = user;

        Set<Role> roles = user.getRoles();

        roles.forEach((role) -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
        });
    }

    public MyUserPrincipal(MyUserPrincipal myUserPrincipal) {
        this.user=myUserPrincipal.user;
        this.authorities=myUserPrincipal.authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public String getFullName(){
        return user.getLastName()+" "+user.getFirstName();
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

    @Override
    public MyUserPrincipal clone(){
        MyUserPrincipal up = new MyUserPrincipal(this);
        up.user.password=null;
        return up;
    }
}
