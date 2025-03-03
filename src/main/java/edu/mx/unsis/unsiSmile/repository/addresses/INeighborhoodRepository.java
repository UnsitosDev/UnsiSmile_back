package edu.mx.unsis.unsiSmile.repository.addresses;

import edu.mx.unsis.unsiSmile.model.addresses.LocalityModel;
import edu.mx.unsis.unsiSmile.model.addresses.NeighborhoodModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface INeighborhoodRepository extends JpaRepository<NeighborhoodModel, Long> {

    Optional<NeighborhoodModel> findByIdNeighborhood(Long idNeighborhood);

    List<NeighborhoodModel> findByName(String name);

    List<NeighborhoodModel> findByLocalityIdLocality(Long idLocality);

    Page<NeighborhoodModel> findByLocality(LocalityModel locality, Pageable pageable);

    @Query("SELECT n FROM NeighborhoodModel n WHERE n.name LIKE :keyword%")
    Page<NeighborhoodModel> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT n FROM NeighborhoodModel n WHERE n.name = :name AND n.locality.idLocality = :localityId")
    Optional<NeighborhoodModel> findByLocalityIdAndName(@Param("localityId") Long localityId,
                                                        @Param("name") String name);

    @Query("SELECT n.idNeighborhood, n.name FROM NeighborhoodModel n WHERE n.idNeighborhood = 1")
    Optional<Object[]> findFirst();
}