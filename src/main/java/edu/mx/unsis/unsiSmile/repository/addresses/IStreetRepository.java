package edu.mx.unsis.unsiSmile.repository.addresses;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.mx.unsis.unsiSmile.model.addresses.StreetModel;

@Repository
public interface IStreetRepository extends JpaRepository<StreetModel, Long> {

    Optional<StreetModel> findByIdStreet(Long idStreet);

    List<StreetModel> findByName(String name);

    List<StreetModel> findByNeighborhoodIdNeighborhood(Long neighborhoodId);

    @Query("SELECT s FROM StreetModel s WHERE s.name = :streetName " +
            "AND s.neighborhood.idNeighborhood = :neighborhoodId")
    Optional<StreetModel> findByNeighborhoodIdAndName(@Param("neighborhoodId") Long neighborhoodId,
                                                      @Param("streetName") String streetName);
}