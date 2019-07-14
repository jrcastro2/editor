package com.qindel.editor.DTOs;

public class DeleteLineDTO {
    private int nLine;

    public DeleteLineDTO(int nLine) {
        this.nLine = nLine;
    }

    public DeleteLineDTO() {
    }

    public int getnLine() {
        return nLine;
    }

    public void setnLine(int nLine) {
        this.nLine = nLine;
    }
}
