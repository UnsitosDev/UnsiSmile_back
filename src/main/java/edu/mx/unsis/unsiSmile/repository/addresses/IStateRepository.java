package edu.mx.unsis.unsiSmile.repository.addresses;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.mx.unsis.unsiSmile.model.addresses.StateModel;

@Repository
public interface IStateRepository extends JpaRepository<StateModel, String> {

    Optional<StateModel> findByIdState(String idState);

    Optional<StateModel> findByName(String name);

}