package com.qindel.editor.DTOs.Response;

import java.util.List;

public class BuscarLineResponseDTO {
    private List<LineaDTO> linea;

    public BuscarLineResponseDTO(List<LineaDTO> linea) {
        this.linea = linea;
    }

    public BuscarLineResponseDTO() {
    }

    public List<LineaDTO> getLinea() {
        return linea;
    }

    public void setLinea(List<LineaDTO> linea) {
        this.linea = linea;
    }
}
