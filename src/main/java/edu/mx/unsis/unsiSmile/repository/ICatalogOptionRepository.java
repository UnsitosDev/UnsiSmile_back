package edu.mx.unsis.unsiSmile.repository;

import edu.mx.unsis.unsiSmile.model.CatalogOptionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICatalogOptionRepository extends JpaRepository<CatalogOptionModel, Long> {

    @Query("SELECT co FROM CatalogOptionModel co WHERE co.catalogModel.idCatalog = :catalogId")
    List<CatalogOptionModel> findAllByCatalogId(@Param("catalogId") Long catalogId);
}
