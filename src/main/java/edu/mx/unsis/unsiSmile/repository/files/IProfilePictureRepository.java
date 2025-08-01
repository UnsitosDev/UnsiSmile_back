package edu.mx.unsis.unsiSmile.repository.files;

import edu.mx.unsis.unsiSmile.model.files.ProfilePictureModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProfilePictureRepository extends JpaRepository<ProfilePictureModel, String> {
}