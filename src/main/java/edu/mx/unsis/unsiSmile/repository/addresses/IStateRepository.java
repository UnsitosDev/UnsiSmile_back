package edu.mx.unsis.unsiSmile.repository.addresses;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

import edu.mx.unsis.unsiSmile.model.HousingModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.mx.unsis.unsiSmile.model.addresses.StateModel;

@Repository
public interface IStateRepository extends JpaRepository<StateModel, String> {

    Optional<StateModel> findByIdState(String idState);

    Optional<StateModel> findByName(String name);

    @Query("SELECT s FROM StateModel s WHERE s.name LIKE :search%")
    Page<StateModel> findByNameContaining(@Param("search") String search, Pageable pageable);

}