package com.qindel.editor.models;

import com.qindel.editor.DTOs.HistoricoDTO;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Textos {
    @Id
    public ObjectId _id;

    public String[] lineas;
    public String titulo;
    public HistoricoDTO[] historico;

    public Textos(ObjectId _id, String[] lineas, String titulo) {
        this._id = _id;
        this.lineas = lineas;
        this.titulo = titulo;
    }

    public Textos() {
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String[] getLineas() {
        return lineas;
    }

    public void setLineas(String[] lineas) {
        this.lineas = lineas;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public HistoricoDTO[] getHistorico() {
        return historico;
    }

    public void setHistorico(HistoricoDTO[] historico) {
        this.historico = historico;
    }
}
