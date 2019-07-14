package com.qindel.editor.DTOs;

public class EditLine {
    private String texto;
    private int numeroLinea;

    public EditLine(String texto, int numeroLinea) {
        this.texto = texto;
        this.numeroLinea = numeroLinea;
    }

    public EditLine() {
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getNumeroLinea() {
        return numeroLinea;
    }

    public void setNumeroLinea(int numeroLinea) {
        this.numeroLinea = numeroLinea;
    }
}
