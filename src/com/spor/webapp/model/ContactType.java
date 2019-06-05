package com.spor.webapp.model;

public enum ContactType {
    PHONE("Тел."),
    MOBILE("Мобильный"),
    HOME_PHONE("Домашний тел."),
    SKYPE("Skype") {
        @Override
        public String toHtml0(Link link) {
            String value = link.getName();
            return getTitle() + ": " + toLink("skype:" + value, value);
        }
    },
    MAIL("Почта") {
        @Override
        public String toHtml0(Link link) {
            String value = link.getName();
            return getTitle() + ": " + toLink("mailto:" + value, value);
        }
    },
    LINKEDIN("Профиль LinkedIn") {
        @Override
        public String toHtml0(Link link) {
            String value = link.getName();
            return toLink(value);
        }
    },
    GITHUB("Профиль GitHub") {
        @Override
        public String toHtml0(Link link) {
            String value = link.getName();
            return toLink(value);
        }
    },
    STATCKOVERFLOW("Профиль Stackoverflow") {
        @Override
        public String toHtml0(Link link) {
            String value = link.getName();
            return toLink(value);
        }
    },
    HOME_PAGE("Домашняя страница") {
        @Override
        public String toHtml0(Link link) {
            String value = link.getName();
            return toLink(value);
        }
    };

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static String toLink(String href, String title) {
        return "<a href='" + href + "'>" + title + "</a>";
    }

    protected String toHtml0(Link link) {
        return title + ": " + link.getName();
    }

    public String toHtml(Link link) {
        return (link == null) ? "" : toHtml0(link);
    }

    public String toLink(String href) {
        return toLink(href, title);
    }
}

