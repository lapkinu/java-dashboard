package example.neontest_db.security;

import example.neontest_db.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {

    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Пример: возврат роли USER
        return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Или добавьте соответствующее поле в User
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Или добавьте соответствующее поле в User
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Или добавьте соответствующее поле в User
    }

    @Override
    public boolean isEnabled() {
        return true; // Или добавьте соответствующее поле в User
    }
}