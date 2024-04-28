package edu.mx.unsis.unsiSmile.repository.addresses;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.mx.unsis.unsiSmile.model.MunicipalityModel;
import edu.mx.unsis.unsiSmile.model.StateModel;

@Repository
public interface IMunicipalityRepository extends JpaRepository<MunicipalityModel, String> {

    Optional<MunicipalityModel> findByIdMunicipality(String idMunicipality);

    List<MunicipalityModel> findByName(String name);

    List<MunicipalityModel> findByState(StateModel state);

}