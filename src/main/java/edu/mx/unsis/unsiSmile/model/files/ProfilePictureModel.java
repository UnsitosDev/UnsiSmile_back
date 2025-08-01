package edu.mx.unsis.unsiSmile.model.files;

import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "profile_pictures")
public class ProfilePictureModel extends AuditModel {

    @Id
    @Column(name = "id_profile_picture", nullable = false, updatable = false, length = 36)
    private String idProfilePicture;

    @Column(name = "url", nullable = false, length = 255)
    private String url;

    @Column(name = "extention_picture", nullable = false, length = 6)
    private String extentionPicture;

        @PrePersist
    public void generateId() {
        if (idProfilePicture == null) {
            idProfilePicture = UUID.randomUUID().toString();
        }
    }
}