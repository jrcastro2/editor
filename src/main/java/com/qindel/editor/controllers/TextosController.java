package com.qindel.editor.controllers;


import com.qindel.editor.DTOs.*;
import com.qindel.editor.models.Textos;
import com.qindel.editor.repositories.TextosRepository;
import com.qindel.editor.services.TextosService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/textos")
public class TextosController {

    @Autowired
    TextosService textosService;

    //GET
    @RequestMapping(value="/", method = RequestMethod.GET)
    public List<Textos> getAllTextos(){
        return textosService.getAllTextos();
    }
    //GET BY ID
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public ResponseEntity getTextoById(@PathVariable("id") ObjectId id) {
        if(textosService.findById(id).isPresent()){
            return ResponseEntity
                    .status(HttpStatus.OK).body(textosService.findById(id).get()) ;
        }else{
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST).body("No existe ese id") ;
        }
        }

    //ADD A DOCUMENT
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity addText(@Valid @RequestBody Textos texto)
    {
        texto.set_id(ObjectId.get());
        return ResponseEntity.status(HttpStatus.OK).body(textosService.save(texto));
    }

    //PUT
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity addText(@PathVariable ObjectId id, @Valid @RequestBody Textos texto)
    {
        texto.set_id(id);
        return ResponseEntity.status(HttpStatus.OK).body(textosService.save(texto));
    }

    //GET NUMBER OF LINES
    @RequestMapping(value = "/{id}/lineas", method = RequestMethod.GET)
    public ResponseEntity getLineasTexto(@PathVariable("id") ObjectId id) {
        if(textosService.findById(id).isPresent()){
            return ResponseEntity
                    .status(HttpStatus.OK).body(textosService.findById(id).get()
                            .getLineas().length) ;
        }else{
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST).body("No existe ese id") ;
        }
    }

    //GET TEXT OF A LINE
    @RequestMapping(value = "/{id}/{linea}", method = RequestMethod.GET)
    public ResponseEntity getLineaTexto(@PathVariable("id") ObjectId id, @PathVariable("linea") Integer linea){
        if(textosService.findById(id).isPresent()){
            if(linea<textosService.findById(id).get().getLineas().length){
                return ResponseEntity
                        .status(HttpStatus.OK).body(textosService.findById(id).get()
                                .getLineas()[linea]) ;
            }else{
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST).body("No existe esa línea") ;
            }
        }else{
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST).body("No existe ese id") ;
        }
    }

    //ADD A LINE AT THE END OF THE DOCUMENT
    @RequestMapping(value = "/{id}/addLine", method = RequestMethod.PUT)
    public ResponseEntity addLineAtEnd(@PathVariable("id") ObjectId id, @Valid @RequestBody AddLineDTO linea){
        if(textosService.findById(id).isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(textosService.addLineAtEndService(id,linea));
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe ese id");
        }
    }

    //MODIFY TEXT OF A LINE
    @RequestMapping(value = "/{id}/editLine", method = RequestMethod.PUT)
    public ResponseEntity editLine(@PathVariable("id") ObjectId id, @Valid @RequestBody EditLine input)
    {
        if(textosService.findById(id).isPresent()){
            if(textosService.findById(id).get().getLineas().length>input.getNumeroLinea()){
                return ResponseEntity.status(HttpStatus.OK).body(textosService.editLine(id, input));
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe esa línea");
            }
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe ese id");
        }
    }

    //GET ALL THE TEXT OF THE DOCUMENT
    @RequestMapping(value = "/{id}/text", method = RequestMethod.GET)
    public ResponseEntity getAlltext(@PathVariable("id") ObjectId id){
        if(textosService.findById(id).isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(textosService.getAlltext(id));
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe ese id");
        }
    }

    //INSERT LINE
    @RequestMapping(value = "/{id}/insertLine", method = RequestMethod.PUT)
    public ResponseEntity insertLine(@PathVariable("id") ObjectId id,
                                     @Valid @RequestBody EditLine input){
        if(textosService.findById(id).isPresent()){
            if (textosService.findById(id).get().getLineas().length>input.getNumeroLinea())
            {
                return ResponseEntity.status(HttpStatus.OK).body(textosService.insertLine(id, input));
            }else if (textosService.findById(id).get().getLineas().length==input.getNumeroLinea()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Para insertar al final use el AddLineAtTheEnd");
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe esa línea");
            }
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe ese id");
        }
    }

    //DELETE LINE
    @RequestMapping(value = "/{id}/deleteLine", method = RequestMethod.PUT)
    public ResponseEntity deleteLine(@PathVariable("id") ObjectId id,
                                     @Valid @RequestBody DeleteLineDTO input){
        if(textosService.findById(id).isPresent()){
            if (textosService.findById(id).get().getLineas().length>input.getnLine())
            {
                textosService.deleteLine(id, input);
                return ResponseEntity.status(HttpStatus.OK).body("Linea "+input.getnLine()+" eliminada");
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe esa línea");
            }
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe ese id");
        }
    }

    //MULTIPLE EDITION
    @RequestMapping(value = "/multipleEdition", method = RequestMethod.PUT)
    public ResponseEntity mutipleEdition(@Valid @RequestBody MultipleEditionDTO input) {
        String str = "";
        for (ObjectId id : input.getIds()) {
            if (!textosService.findById(id).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe este id: " + id);
            }else if(textosService.findById(id).get().getLineas().length>input.getnLinea()){
                str+= id+" ";
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe esa línea en el documento: "+ id);
            }
        }
        textosService.multipleEdition(input);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Actualizados los siguientes documentos: "+ str);
    }

    //BUSCAR LINEAS
    @RequestMapping(value = "/{id}/buscar", method = RequestMethod.GET)
    public ResponseEntity buscarLineas(@PathVariable("id") ObjectId id, @Valid @RequestBody BuscarLineaDTO input){
        if(textosService.findById(id).isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(textosService.buscarLine(id, input));
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe ese id");
        }
    }

    //DESHACER
    @RequestMapping(value = "/{id}/deshacer", method = RequestMethod.PUT)
    public ResponseEntity deshacer(@PathVariable("id") ObjectId id){
        if(textosService.findById(id).isPresent()){
            if(textosService.findById(id).get().getHistorico().length>0){
                return ResponseEntity.status(HttpStatus.OK).body(textosService.deshacer(id));
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se puede deshacer más");
            }
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe ese id");
        }
    }

    //DESHACER RANDOM
    @RequestMapping(value = "/{id}/deshacerRandom", method = RequestMethod.PUT)
    public ResponseEntity deshacerRandom(@PathVariable("id") ObjectId id){
        if(textosService.findById(id).isPresent()){
            if(textosService.findById(id).get().getHistorico().length>0){
                return ResponseEntity.status(HttpStatus.OK).body(textosService.deshacerRandom(id));
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se puede deshacer más");
            }
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe ese id");
        }
    }
}
