package com.spor.webapp.model;

public class TextSection extends AbstractSectionData {

    private String text;

    public TextSection(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
