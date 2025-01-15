package edu.mx.unsis.unsiSmile.repository.files;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.mx.unsis.unsiSmile.model.files.FileModel;

@Repository
public interface IFileRepository extends JpaRepository<FileModel, String> {

    @Query("SELECT f FROM FileModel f WHERE f.answer.idAnswer = :answerId")
    List<FileModel> findAllByAnswerId(@Param("answerId") Long answerId);
}