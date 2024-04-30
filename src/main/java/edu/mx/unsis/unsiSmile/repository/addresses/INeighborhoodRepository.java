package edu.mx.unsis.unsiSmile.repository.addresses;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.mx.unsis.unsiSmile.model.addresses.LocalityModel;
import edu.mx.unsis.unsiSmile.model.addresses.NeighborhoodModel;

@Repository
public interface INeighborhoodRepository extends JpaRepository<NeighborhoodModel, Long> {

    Optional<NeighborhoodModel> findByIdNeighborhood(Long idNeighborhood);

    List<NeighborhoodModel> findByName(String name);

    List<NeighborhoodModel> findByLocality(LocalityModel locality);

}