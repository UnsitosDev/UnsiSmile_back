package edu.mx.unsis.unsiSmile.repository.addresses;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.mx.unsis.unsiSmile.model.LocalityModel;
import edu.mx.unsis.unsiSmile.model.MunicipalityModel;

@Repository
public interface ILocalityRepository extends JpaRepository<LocalityModel, String> {

    Optional<LocalityModel> findByIdLocality(String idLocality);

    List<LocalityModel> findByName(String name);

    List<LocalityModel> findByPostalCode(String postalCode);

    List<LocalityModel> findByMunicipality(MunicipalityModel municipality);

}