package edu.mx.unsis.unsiSmile.repository.forms.catalogs;

import edu.mx.unsis.unsiSmile.model.forms.catalogs.CatalogModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICatalogRepository extends JpaRepository<CatalogModel, Long> {
    @Query("SELECT c.idCatalog FROM CatalogModel c WHERE c.catalogName = :catalogName")
    Optional<Long> findIdByCatalogName(@Param("catalogName") String catalogName);
}
