package edu.mx.unsis.unsiSmile.repository.medicalrecords.dentalprophylaxis;

import edu.mx.unsis.unsiSmile.model.medicalrecords.dentalprophylaxis.SOHIModel;
import edu.mx.unsis.unsiSmile.model.medicalrecords.dentalprophylaxis.SOHIToothCodeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISOHITootCodeRepository extends JpaRepository<SOHIToothCodeModel, Long> {
    List<SOHIToothCodeModel> findBySohi(SOHIModel idSohi);
}
