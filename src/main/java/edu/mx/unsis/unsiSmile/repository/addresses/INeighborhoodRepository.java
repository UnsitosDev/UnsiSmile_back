package edu.mx.unsis.unsiSmile.repository.addresses;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.mx.unsis.unsiSmile.model.addresses.NeighborhoodModel;

@Repository
public interface INeighborhoodRepository extends JpaRepository<NeighborhoodModel, Long> {

    Optional<NeighborhoodModel> findByIdNeighborhood(Long idNeighborhood);

    List<NeighborhoodModel> findByName(String name);

    List<NeighborhoodModel> findByLocalityIdLocality(String idLocality);


    @Query("SELECT n FROM NeighborhoodModel n WHERE n.name = :name AND n.locality.idLocality = :localityId")
    Optional<NeighborhoodModel> findByLocalityIdAndName(@Param("localityId") String localityId,
                                                        @Param("name") String name);
}