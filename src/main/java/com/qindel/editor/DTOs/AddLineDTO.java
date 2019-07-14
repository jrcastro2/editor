package com.qindel.editor.DTOs;

public class AddLineDTO {
    private String linea;

    public AddLineDTO() {
    }

    public AddLineDTO(String linea) {
        this.linea = linea;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }
}
