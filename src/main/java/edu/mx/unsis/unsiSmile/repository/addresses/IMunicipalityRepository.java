package edu.mx.unsis.unsiSmile.repository.addresses;

import edu.mx.unsis.unsiSmile.model.addresses.MunicipalityModel;
import edu.mx.unsis.unsiSmile.model.addresses.StateModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IMunicipalityRepository extends JpaRepository<MunicipalityModel, String> {

    Optional<MunicipalityModel> findByIdMunicipality(String idMunicipality);

    List<MunicipalityModel> findByName(String name);

    List<MunicipalityModel> findByState(StateModel state);

    @Query("SELECT l FROM MunicipalityModel l WHERE l.name like :keyword%")
    Page<MunicipalityModel> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
}