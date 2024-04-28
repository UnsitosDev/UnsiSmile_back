package edu.mx.unsis.unsiSmile.repository.patients;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.mx.unsis.unsiSmile.model.EthnicGroupModel;

@Repository
public interface IEthnicGroupRepository extends JpaRepository<EthnicGroupModel, Long> {

    Optional<EthnicGroupModel> findByIdEthnicGroup(Long idEthnicGroup);

    Optional<EthnicGroupModel> findByEthnicGroup(String ethnicGroup);

}
