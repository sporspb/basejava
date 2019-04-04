package com.spor.webapp.model;

import java.util.Objects;

public class TextSection extends AbstractSection {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TextSection)) return false;
        TextSection that = (TextSection) o;
        return getText().equals(that.getText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getText());
    }
}
