package edu.mx.unsis.unsiSmile.repository;

import edu.mx.unsis.unsiSmile.model.GroupModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGroupRepository extends JpaRepository<GroupModel, Long> {
}
