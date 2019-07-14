package com.qindel.editor.services.impl;

import com.qindel.editor.DTOs.*;
import com.qindel.editor.DTOs.Response.BuscarLineResponseDTO;
import com.qindel.editor.DTOs.Response.LineaDTO;
import com.qindel.editor.models.Textos;
import com.qindel.editor.repositories.TextosRepository;
import com.qindel.editor.services.TextosService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
public class TextosServiceImpl implements TextosService {

    @Autowired
    private TextosRepository textosRepository;

    public Textos addLineAtEndService(ObjectId id, AddLineDTO linea){
        Textos textoRecuperado = textosRepository.findBy_id(id).get();

        //historico
        HistoricoDTO h = new HistoricoDTO(false,false,true,linea.getLinea(),textoRecuperado.getLineas().length);
        HistoricoDTO[] arrayHis =  Arrays.copyOf(textoRecuperado.getHistorico(), textoRecuperado.getHistorico().length +1);
        arrayHis[textoRecuperado.getHistorico().length] = h;
        textoRecuperado.setHistorico(arrayHis);

        String[] array;
        array = Arrays.copyOf(textoRecuperado.getLineas(), textoRecuperado.getLineas().length +1);
        array[array.length -1] = linea.getLinea();
        textoRecuperado.setLineas(array);
        textosRepository.save(textoRecuperado);
        return textoRecuperado;
    }
    public List<Textos> getAllTextos(){
        return textosRepository.findAll();
    }

    public Optional<Textos> findById(ObjectId id){
        return textosRepository.findBy_id(id);
    }

    public Textos editLine(ObjectId id, EditLine input){
        Textos textoRecuperado = textosRepository.findBy_id(id).get();

        //historico
        HistoricoDTO h = new HistoricoDTO(false,true,false,textoRecuperado.getLineas()[input.getNumeroLinea()],input.getNumeroLinea());
        HistoricoDTO[] arrayHis =  Arrays.copyOf(textoRecuperado.getHistorico(), textoRecuperado.getHistorico().length +1);
        arrayHis[textoRecuperado.getHistorico().length] = h;
        textoRecuperado.setHistorico(arrayHis);

        String[] array;
        array = textoRecuperado.getLineas();
        array[input.getNumeroLinea()] = input.getTexto();
        textoRecuperado.setLineas(array);
        textosRepository.save(textoRecuperado);
        return textoRecuperado;
    }

    public String getAlltext(ObjectId id){
        Textos textoRecuperado = textosRepository.findBy_id(id).get();
        String str = "";
        for(int i = 0; i<textoRecuperado.getLineas().length; i++){
            str += textoRecuperado.getLineas()[i]+" ";
        }
        return str;
    }

    public Textos save(Textos texto){
        return textosRepository.save(texto);
    }

    public Textos insertLine(ObjectId id, EditLine input){
        Textos textoModifcar = textosRepository.findBy_id(id).get();
        String[] lineas = textoModifcar.getLineas();

        //historico
        HistoricoDTO h;
        if(lineas.length>0){
            h = new HistoricoDTO(false,false,true,lineas[input.getNumeroLinea()],input.getNumeroLinea());
        }else{
            h = new HistoricoDTO(false,false,true,"",0);
        }
        HistoricoDTO[] arrayHis =  Arrays.copyOf(textoModifcar.getHistorico(), textoModifcar.getHistorico().length +1);
        arrayHis[textoModifcar.getHistorico().length] = h;
        textoModifcar.setHistorico(arrayHis);

        String[] array;
        array = Arrays.copyOf(textoModifcar.getLineas(), textoModifcar.getLineas().length +1);
        for(int i=array.length-1; i > input.getNumeroLinea(); i--){
            array[i] = array[i-1];
        }
        array[input.getNumeroLinea()] = input.getTexto();
        textoModifcar.setLineas(array);
        return textosRepository.save(textoModifcar);
    }

    public void deleteLine(ObjectId id, DeleteLineDTO input){
        Textos textoRecuperado = textosRepository.findBy_id(id).get();
        String[] lineas = textoRecuperado.getLineas();

        //historico
        HistoricoDTO h = new HistoricoDTO(true,false,false,lineas[input.getnLine()],input.getnLine());
        HistoricoDTO[] arrayHis =  Arrays.copyOf(textoRecuperado.getHistorico(), textoRecuperado.getHistorico().length +1);
        arrayHis[textoRecuperado.getHistorico().length] = h;
        textoRecuperado.setHistorico(arrayHis);

        for(int i = input.getnLine(); i<lineas.length-1; i++){
            lineas[i] = lineas[i+1];
        }
        String[] array = Arrays.copyOf(lineas, lineas.length -1);
        textoRecuperado.setLineas(array);

        textosRepository.save(textoRecuperado);
    }

    public void multipleEdition(MultipleEditionDTO input){
        for (ObjectId id:
             input.getIds()) {
            Textos textoRecuperado = textosRepository.findBy_id(id).get();
            if(input.isBorrar()){
                String[] lineas = textoRecuperado.getLineas();

                //historico
                HistoricoDTO h = new HistoricoDTO(true,false,false,lineas[input.getnLinea()],input.getnLinea());
                HistoricoDTO[] arrayHis =  Arrays.copyOf(textoRecuperado.getHistorico(), textoRecuperado.getHistorico().length +1);
                arrayHis[textoRecuperado.getHistorico().length] = h;
                textoRecuperado.setHistorico(arrayHis);

                for(int i = input.getnLinea(); i<lineas.length-1; i++){
                    lineas[i] = lineas[i+1];
                }
                String[] array = Arrays.copyOf(lineas, lineas.length -1);
                textoRecuperado.setLineas(array);
                textosRepository.save(textoRecuperado);
            }else{
                String[] lineas = textoRecuperado.getLineas();

                //historico
                HistoricoDTO h = new HistoricoDTO(false,false,true,lineas[input.getnLinea()],input.getnLinea());
                HistoricoDTO[] arrayHis =  Arrays.copyOf(textoRecuperado.getHistorico(), textoRecuperado.getHistorico().length +1);
                arrayHis[textoRecuperado.getHistorico().length] = h;
                textoRecuperado.setHistorico(arrayHis);

                String[] array;
                array = Arrays.copyOf(textoRecuperado.getLineas(), textoRecuperado.getLineas().length +1);
                for(int i=array.length-1; i > input.getnLinea(); i--){
                    array[i] = array[i-1];
                }
                array[input.getnLinea()] = input.getTexto();
                textoRecuperado.setLineas(array);
                textosRepository.save(textoRecuperado);
            }
        }
    }

    public BuscarLineResponseDTO buscarLine(ObjectId id, BuscarLineaDTO input){
        Textos textoRecuperado = textosRepository.findBy_id(id).get();
        String[] lineas = textoRecuperado.getLineas();

        BuscarLineResponseDTO response = new BuscarLineResponseDTO();
        List<LineaDTO> list = new ArrayList<>();

        for(int i = 0; i<lineas.length; i++){
            String[] contenido = lineas[i].split(" ");
            for(String palabra: contenido){
                if(palabra.equals(input.getTexto())){
                    LineaDTO l = new LineaDTO(lineas[i], i);
                    list.add(l);
                }
            }
        }
        response.setLinea(list);
        return response;
    }

    public Textos deshacer(ObjectId id){
        Textos textoRecuperado = textosRepository.findBy_id(id).get();

        HistoricoDTO ultimoCambio = textoRecuperado.getHistorico()[textoRecuperado.getHistorico().length-1];

        if(ultimoCambio.isBorrar()){
            String[] array;
            array = Arrays.copyOf(textoRecuperado.getLineas(), textoRecuperado.getLineas().length +1);
            String[] lineas = textoRecuperado.getLineas();
            for(int i=array.length-1; i > ultimoCambio.getnLinea(); i--){
                array[i] = array[i-1];
            }
            array[ultimoCambio.getnLinea()] = ultimoCambio.getTexto();
            textoRecuperado.setLineas(array);

        }else if( ultimoCambio.isEditar()){
            String[] array;
            array = textoRecuperado.getLineas();
            array[ultimoCambio.getnLinea()] = ultimoCambio.getTexto();
            textoRecuperado.setLineas(array);

        }else if(ultimoCambio.isInsertar()){
            String[] lineas = textoRecuperado.getLineas();

            for(int i = ultimoCambio.getnLinea(); i<lineas.length-1; i++){
                lineas[i] = lineas[i+1];
            }
            String[] array = Arrays.copyOf(lineas, lineas.length -1);
            textoRecuperado.setLineas(array);

        }

        HistoricoDTO[] array;
        HistoricoDTO[] array2 = textoRecuperado.getHistorico();
        array2[textoRecuperado.getHistorico().length-1] = null;
        array = Arrays.copyOf(array2, array2.length -1);

        textoRecuperado.setHistorico(array);

        textosRepository.save(textoRecuperado);
        return textoRecuperado;
    }

    public Textos deshacerRandom(ObjectId id){
        Textos textoRecuperado = textosRepository.findBy_id(id).get();

        Random rand = new Random();

        int random = rand.nextInt(textoRecuperado.getHistorico().length+1);

        for(int i=0; i<random; i++){
            deshacer(id);
        }
        Textos textoRecuperado2 = textosRepository.findBy_id(id).get();
        return textoRecuperado2;
    }




}
