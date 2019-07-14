package com.qindel.editor.DTOs;

import org.bson.types.ObjectId;

public class MultipleEditionDTO {
    private ObjectId[] ids;
    private int nLinea;
    private boolean borrar;
    private String texto;

    public MultipleEditionDTO() {
    }

    public MultipleEditionDTO(ObjectId[] ids, int nLinea, boolean borrar, String texto) {
        this.ids = ids;
        this.nLinea = nLinea;
        this.borrar = borrar;
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public ObjectId[] getIds() {
        return ids;
    }

    public void setIds(ObjectId[] ids) {
        this.ids = ids;
    }

    public int getnLinea() {
        return nLinea;
    }

    public void setnLinea(int nLinea) {
        this.nLinea = nLinea;
    }

    public boolean isBorrar() {
        return borrar;
    }

    public void setBorrar(boolean borrar) {
        this.borrar = borrar;
    }
}
