package edu.mx.unsis.unsiSmile.repository.addresses;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.mx.unsis.unsiSmile.model.addresses.LocalityModel;
import edu.mx.unsis.unsiSmile.model.addresses.MunicipalityModel;

@Repository
public interface ILocalityRepository extends JpaRepository<LocalityModel, String> {

    Optional<LocalityModel> findByIdLocality(String idLocality);

    List<LocalityModel> findByName(String name);

    List<LocalityModel> findByPostalCode(String postalCode);

    List<LocalityModel> findByMunicipality(MunicipalityModel municipality);

    @Query("SELECT l FROM LocalityModel l WHERE l.name like :keyword%")
    Page<LocalityModel> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
}