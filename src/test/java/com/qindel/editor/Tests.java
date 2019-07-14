package com.qindel.editor;

import com.qindel.editor.DTOs.*;
import com.qindel.editor.DTOs.Response.BuscarLineResponseDTO;
import com.qindel.editor.DTOs.Response.LineaDTO;
import com.qindel.editor.models.Textos;
import com.qindel.editor.repositories.TextosRepository;
import com.qindel.editor.services.TextosService;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=EditorApplication.class)
public class Tests {

    @Autowired
    TextosRepository textosRepository;

    @Autowired
    TextosService textosService;

    @Test
    public void getNumeroLineas_success() {
        int expected  = 11;
        ObjectId id = new ObjectId("5d22fa5ec9549c20c0ce527d");
        int numberLines = textosService.findById(id).get().getLineas().length;
        assertEquals(expected, numberLines);
    }

    @Test
    public void getLinea_success(){
        String expected = "La guitarra";
        ObjectId id = new ObjectId("5d22fa5ec9549c20c0ce527d");
        String real = textosService.findById(id).get().getLineas()[0];
        assertEquals(expected, real);
    }

    @Test
    public void getAllText_success(){
        String expected = "La guitarra hace llorar a los sueños." +
                " El sollozo de las almas perdidas. se escapa por su boca redonda." +
                " Y como la tarántula, teje una gran estrella para cazar suspiros," +
                " que flotan en su negro aljibe de madera. ";
        ObjectId id = new ObjectId("5d22fa5ec9549c20c0ce527d");
        String real = textosService.getAlltext(id);
        assertEquals(expected, real);
    }

    @Test
    public void searchLine_success(){
        BuscarLineaDTO input = new BuscarLineaDTO("de");
        ObjectId id = new ObjectId("5d22fa5ec9549c20c0ce527d");
        List<LineaDTO> list = new ArrayList<>();
        list.add(new LineaDTO("El sollozo de las almas",2));
        list.add(new LineaDTO("aljibe de madera.",10));
        BuscarLineResponseDTO expected = new BuscarLineResponseDTO(list);
        BuscarLineResponseDTO real = textosService.buscarLine(id, input);
        for(int i = 0; i<list.size(); i++){
            assertEquals(list.get(i).getnLinea(),real.getLinea().get(i).getnLinea());
            assertEquals(list.get(i).getTexto(),real.getLinea().get(i).getTexto());
        }
    }

    @Test
    public void addLineAtEnd_success(){
        int expected = 12;
        String expectedS = "PROBANDO AÑADIR AL FINAL";
        AddLineDTO linea = new AddLineDTO("PROBANDO AÑADIR AL FINAL");
        ObjectId id = new ObjectId("5d243d4180d5bc1670222dbb");
        textosService.addLineAtEndService(id,linea);
        assertEquals(expected, textosService.findById(id).get().getLineas().length);
        assertEquals(expectedS, textosService.findById(id).get().getLineas()[11]);
        textosService.deshacer(id);
    }

    @Test
    public void EditLine_success(){
        EditLine input = new EditLine("Probando a editar el texto", 5);
        ObjectId id = new ObjectId("5d243d4180d5bc1670222dbb");
        textosService.editLine(id,input);
        assertEquals(input.getTexto(), textosService.findById(id).get().getLineas()[input.getNumeroLinea()]);
        textosService.deshacer(id);
    }

    @Test
    public void insertLine_success(){
        int expected = 12;
        EditLine input = new EditLine("Probando a inertar una linea para deshacer"
                , 1);
        ObjectId id = new ObjectId("5d243d4180d5bc1670222dbb");
        textosService.insertLine(id,input);
        assertEquals(input.getTexto(), textosService.findById(id).get().getLineas()[input.getNumeroLinea()]);
        assertEquals(expected, textosService.findById(id).get().getLineas().length);
        textosService.deshacer(id);
    }

    @Test
    public void deleteLine_success(){
        int expected = 10;
        DeleteLineDTO input = new DeleteLineDTO(4);
        ObjectId id = new ObjectId("5d243d4180d5bc1670222dbb");
        String expectedS = textosService.findById(id).get().getLineas()[input.getnLine()];
        textosService.deleteLine(id,input);
        assertEquals(expectedS, textosService.findById(id).get().getHistorico()[0].getTexto());
        assertEquals(expected, textosService.findById(id).get().getLineas().length);
        textosService.deshacer(id);
    }

    @Test
    public void multipleEdition_success(){
        int expected = 10;
        ObjectId[] ids = {new ObjectId("5d22fa5ec9549c20c0ce527d"),new ObjectId("5d243d4180d5bc1670222dbb")};
        //Probando a borrar
        MultipleEditionDTO input = new MultipleEditionDTO(ids, 1,true,"");
        textosService.multipleEdition(input);
        for (ObjectId id:
             ids) {
            assertEquals(expected, textosService.findById(id).get().getLineas().length);
            textosService.deshacer(id);
        }
    }

    @Test
    public void deshacer_success(){
        int expectedLength = 11;
        DeleteLineDTO input = new DeleteLineDTO(4);
        ObjectId id = new ObjectId("5d243d4180d5bc1670222dbb");

        textosService.deleteLine(id,input);
        String expectedS = textosService.findById(id).get().getHistorico()[0].getTexto();

        textosService.deshacer(id);

        assertEquals(expectedLength,textosService.findById(id).get().getLineas().length);
        assertEquals(expectedS,textosService.findById(id).get().getLineas()[input.getnLine()]);
    }


}
