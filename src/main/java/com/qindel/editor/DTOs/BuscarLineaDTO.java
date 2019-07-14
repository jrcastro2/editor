package com.qindel.editor.DTOs;

public class BuscarLineaDTO {
    private String texto;

    public BuscarLineaDTO(String texto) {
        this.texto = texto;
    }

    public BuscarLineaDTO() {
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
