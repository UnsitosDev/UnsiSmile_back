package edu.mx.unsis.unsiSmile.repository.files;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mx.unsis.unsiSmile.model.ProfilePictureModel;

public interface IProfilePictureRepository extends JpaRepository<ProfilePictureModel, String> {

}
