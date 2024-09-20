package edu.mx.unsis.unsiSmile.repository;

import edu.mx.unsis.unsiSmile.model.FileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFileRepository extends JpaRepository<FileModel, String> {

    @Query("SELECT f FROM FileModel f WHERE f.answer.idAnswer = :answerId")
    List<FileModel> findAllByAnswerId(@Param("answerId") Long answerId);
}