package com.qindel.editor.DTOs;

public class HistoricoDTO {

    private boolean borrar;
    private boolean editar;
    private boolean insertar;
    private String texto;
    private int nLinea;

    public HistoricoDTO() {
    }

    public HistoricoDTO(boolean borrar, boolean editar, boolean insertar, String texto, int nLinea) {
        this.borrar = borrar;
        this.editar = editar;
        this.insertar = insertar;
        this.texto = texto;
        this.nLinea = nLinea;
    }

    public boolean isBorrar() {
        return borrar;
    }

    public void setBorrar(boolean borrar) {
        this.borrar = borrar;
    }

    public boolean isEditar() {
        return editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getnLinea() {
        return nLinea;
    }

    public void setnLinea(int nLinea) {
        this.nLinea = nLinea;
    }

    public boolean isInsertar() {
        return insertar;
    }

    public void setInsertar(boolean insertar) {
        this.insertar = insertar;
    }
}
