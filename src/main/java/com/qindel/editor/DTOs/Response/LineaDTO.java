package com.qindel.editor.DTOs.Response;

public class LineaDTO {
    private String texto;
    private int nLinea;

    public LineaDTO() {
    }

    public LineaDTO(String texto, int nLinea) {
        this.texto = texto;
        this.nLinea = nLinea;
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
}
