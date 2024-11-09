package edu.mx.unsis.unsiSmile.repository.patients;

import edu.mx.unsis.unsiSmile.model.patients.EthnicGroupModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IEthnicGroupRepository extends JpaRepository<EthnicGroupModel, Long> {

    Optional<EthnicGroupModel> findByIdEthnicGroup(Long idEthnicGroup);

    Optional<EthnicGroupModel> findByEthnicGroup(String ethnicGroup);

    @Query("SELECT l FROM EthnicGroupModel l WHERE l.ethnicGroup like :keyword%")
    Page<EthnicGroupModel> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
