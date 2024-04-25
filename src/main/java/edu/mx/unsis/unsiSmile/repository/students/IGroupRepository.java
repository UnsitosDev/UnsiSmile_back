package edu.mx.unsis.unsiSmile.repository.students;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mx.unsis.unsiSmile.model.students.GroupModel;

public interface IGroupRepository extends JpaRepository<GroupModel, Long> {
}
