package edu.mx.unsis.unsiSmile.repository.addresses;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.mx.unsis.unsiSmile.model.HousingModel;

@Repository
public interface IHousingRepository extends JpaRepository<HousingModel, String> {

    Optional<HousingModel> findByIdHousing(String idHousing);

    Optional<HousingModel> findByCategory(String category);

    @Query("SELECT h FROM HousingModel h WHERE h.category LIKE %:category%")
    List<HousingModel> findByCategoryContaining(@Param("category") String category);

}