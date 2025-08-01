package edu.mx.unsis.unsiSmile.repository.forms.catalogs;

import edu.mx.unsis.unsiSmile.model.forms.catalogs.CatalogOptionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICatalogOptionRepository extends JpaRepository<CatalogOptionModel, Long> {

    @Query("SELECT co FROM CatalogOptionModel co WHERE co.catalogModel.idCatalog = :catalogId AND co.statusKey = :statusKey")
    List<CatalogOptionModel> findAllByCatalogIdAndStatusKey(@Param("catalogId") Long catalogId, @Param("statusKey") String statusKey);

    Optional<CatalogOptionModel> findByOptionName(String option);
}