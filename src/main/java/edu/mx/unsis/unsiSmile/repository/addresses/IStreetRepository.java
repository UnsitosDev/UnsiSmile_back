package edu.mx.unsis.unsiSmile.repository.addresses;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.mx.unsis.unsiSmile.model.addresses.NeighborhoodModel;
import edu.mx.unsis.unsiSmile.model.addresses.StreetModel;

@Repository
public interface IStreetRepository extends JpaRepository<StreetModel, Long> {

    Optional<StreetModel> findByIdStreet(Long idStreet);

    List<StreetModel> findByName(String name);

    List<StreetModel> findByNeighborhood(NeighborhoodModel neighborhood);

}