package com.apu.user.security_oauth2.models.security;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Data
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "oauth_user")
@ToString
public class User implements UserDetails, Serializable {
    private static final long serialVersionUID = -4744753522696645871L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private boolean accountExpired;

    private boolean accountLocked;

    private boolean credentialsExpired;

    private boolean enabled;


    /*@JoinTable can be used to map following associations to database table:
    bidirectional many-to-one/one-to-many,
    unidirectional many-to-one,
    and one-to-one (both bidirectional and unidirectional) associations.
    @JoinTable(name = "oauth_user_authorities",
            joinColumns = @JoinColumn(name = "user_id"//oauth_user_authorities.user_id,
            referencedColumnName = "id"//oauth_user.id),
    inverseJoinColumns = @JoinColumn(name = "authority_id"//oauth_user_authorities.authority_id,
    referencedColumnName = "id"//oauth_authority.id))*/

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "oauth_user_authorities",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    @OrderBy
    private Collection<Authority> authorities;

    @Override
    public boolean isAccountNonExpired() {
        return !isAccountExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isAccountLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !isCredentialsExpired();
    }
}