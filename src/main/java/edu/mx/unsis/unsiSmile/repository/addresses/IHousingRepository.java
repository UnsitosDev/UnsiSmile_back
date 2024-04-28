package edu.mx.unsis.unsiSmile.repository.addresses;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.mx.unsis.unsiSmile.model.HousingModel;

@Repository
public interface IHousingRepository extends JpaRepository<HousingModel, String> {

    Optional<HousingModel> findByIdHousing(String idHousing);

    Optional<HousingModel> findByCategory(String category);

}