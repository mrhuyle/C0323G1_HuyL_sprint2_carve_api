package com.example.carve.user.model;

import com.example.carve.deck.model.Deck;
import com.example.carve.token.model.Token;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String username;
    private String password;
    private String email;

    @Column(columnDefinition = "VARCHAR(2083) DEFAULT '/src/assets/img/user_img.webp' ")
    private String img;

    @Column(columnDefinition = "BIT(1) DEFAULT 0")
    private boolean isDeleted;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Token> tokens;

    @ManyToOne
    @JoinColumn(name = "rank_id")
    private RankUser rank;

    @ManyToMany
    @JoinTable(
            name = "user_deck", // Name of the join table
            joinColumns = @JoinColumn(name = "user_id"), // Column from this entity
            inverseJoinColumns = @JoinColumn(name = "deck_id") // Column from the other entity
    )
    private Set<Deck> decks;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
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
