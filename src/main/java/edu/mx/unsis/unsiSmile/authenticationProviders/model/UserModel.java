package edu.mx.unsis.unsiSmile.authenticationProviders.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_App", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
public class UserModel implements UserDetails {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "uuid2")
    private UUID id;

    @Column(nullable = false)
    private String username;

    private String password;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private RoleModel role;

    public String getIdAsString() {
        return id.toString();
    }

    public void setIdFromString(String id) {
        this.id = UUID.fromString(id);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getRole().name()));
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
