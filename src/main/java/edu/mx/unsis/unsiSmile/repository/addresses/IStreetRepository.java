package edu.mx.unsis.unsiSmile.repository.addresses;

import edu.mx.unsis.unsiSmile.model.addresses.NeighborhoodModel;
import edu.mx.unsis.unsiSmile.model.addresses.StreetModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IStreetRepository extends JpaRepository<StreetModel, Long> {

    Optional<StreetModel> findByIdStreet(Long idStreet);

    List<StreetModel> findByName(String name);

    List<StreetModel> findByNeighborhoodIdNeighborhood(Long neighborhoodId);

    @Query("SELECT s FROM StreetModel s WHERE s.name LIKE :keyword%")
    Page<StreetModel> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    Page<StreetModel> findByNeighborhood(NeighborhoodModel neighborhood, Pageable pageable);

    @Query("SELECT s FROM StreetModel s WHERE s.name = :streetName " +
            "AND s.neighborhood.idNeighborhood = :neighborhoodId")
    Optional<StreetModel> findByNeighborhoodIdAndName(@Param("neighborhoodId") Long neighborhoodId,
                                                      @Param("streetName") String streetName);
}