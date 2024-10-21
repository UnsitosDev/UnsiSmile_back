package edu.mx.unsis.unsiSmile.repository.addresses;

import edu.mx.unsis.unsiSmile.model.addresses.StateModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IStateRepository extends JpaRepository<StateModel, String> {

    Optional<StateModel> findByIdState(String idState);

    Optional<StateModel> findByName(String name);

    @Query("SELECT s FROM StateModel s WHERE s.name LIKE :search%")
    Page<StateModel> findByNameContaining(@Param("search") String search, Pageable pageable);

}