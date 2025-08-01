package edu.mx.unsis.unsiSmile.repository.medicalrecords.teeth;

import edu.mx.unsis.unsiSmile.model.medicalrecords.teeth.ToothModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IToothRepository extends JpaRepository<ToothModel, String> {
}