package com.spor.webapp.model;

public enum ContactType {
    PHONE("Тел.:"),
    SKYPE("Skype:"),
    MAIL("Почта:"),
    PROFILE("Профиль "),
    LINK("");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}

