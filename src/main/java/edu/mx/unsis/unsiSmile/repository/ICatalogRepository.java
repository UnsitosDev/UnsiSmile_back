package edu.mx.unsis.unsiSmile.repository;

import edu.mx.unsis.unsiSmile.model.CatalogModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICatalogRepository extends JpaRepository<CatalogModel, Long> {
}
