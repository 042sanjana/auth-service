package com.ewallet.auth_service.entity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name="auth_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;



    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        USER,
        ADMIN
    }
}
