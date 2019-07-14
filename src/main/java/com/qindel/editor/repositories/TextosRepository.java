package com.qindel.editor.repositories;

import com.qindel.editor.DTOs.HistoricoDTO;
import com.qindel.editor.models.Textos;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TextosRepository extends MongoRepository<Textos, ObjectId> {
    Optional<Textos> findBy_id(ObjectId _id);
}
