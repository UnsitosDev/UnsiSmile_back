package edu.mx.unsis.unsiSmile.repository.addresses;

import edu.mx.unsis.unsiSmile.model.addresses.LocalityModel;
import edu.mx.unsis.unsiSmile.model.addresses.MunicipalityModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ILocalityRepository extends JpaRepository<LocalityModel, Long> {

    Optional<LocalityModel> findByIdLocality(Long idLocality);

    List<LocalityModel> findByName(String name);

    List<LocalityModel> findByPostalCode(String postalCode);

    Page<LocalityModel> findByMunicipality(MunicipalityModel municipality, Pageable pageable);

    @Query("SELECT l FROM LocalityModel l WHERE l.name like :keyword%")
    Page<LocalityModel> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT l FROM LocalityModel l WHERE l.name = :name AND l.municipality.idMunicipality = :municipalityId")
    Optional<LocalityModel> findByMunicipalityIdAndName(@Param("municipalityId") String municipalityId,
                                                        @Param("name") String name);
}