package dev.dumezthomas.kitchencost.entities;

import dev.dumezthomas.kitchencost.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@ToString(callSuper = true)
@Getter
@Setter
public class User extends RestaurantScoped {

    @NotBlank
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @NotBlank
    @Email
    @Column(nullable = false)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserRole role;

    @Column(nullable = false)
    private boolean active = true;
}
