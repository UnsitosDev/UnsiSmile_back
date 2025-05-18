package edu.mx.unsis.unsiSmile.authenticationProviders.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "otp_tokens")
public class OtpTokenModel {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private String id;

    @Column(name = "code", length = 6, nullable = false)
    private String code;

    @Column(name = "purpose", length = 30, nullable = false)
    private String purpose;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "is_used", nullable = false)
    private boolean isUsed = false;

    @Column(name = "attempts", nullable = false)
    private byte attempts = 0;

    @Column(name = "email", length = 255, nullable = false)
    private String email;

    @Column(name = "validated_at", updatable = false)
    private LocalDateTime validatedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @PrePersist
    public void generateId() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
    }
}