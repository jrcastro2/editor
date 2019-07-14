package com.qindel.editor.services;


import com.qindel.editor.DTOs.*;
import com.qindel.editor.DTOs.Response.BuscarLineResponseDTO;
import com.qindel.editor.models.Textos;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface TextosService {

    Textos addLineAtEndService(ObjectId id, AddLineDTO linea);

    List<Textos> getAllTextos();

    Optional<Textos> findById(ObjectId id);

    Textos editLine(ObjectId id, EditLine input);

    String getAlltext(ObjectId id);

    Textos save(Textos texto);

    Textos insertLine(ObjectId id, EditLine input);

    void deleteLine(ObjectId id, DeleteLineDTO input);

    void multipleEdition(MultipleEditionDTO input);

    BuscarLineResponseDTO buscarLine(ObjectId id, BuscarLineaDTO input);

    Textos deshacer(ObjectId id);

    Textos deshacerRandom(ObjectId id);
}
