package com.spor.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Organisation {

    private Link link;
    private LocalDate startDate;
    private LocalDate endDate;
    private String title;
    private String description;

    public Organisation(Link link, LocalDate startDate, LocalDate endDate, String title, String description) {
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(endDate, "endDate must not be null");
        Objects.requireNonNull(title, "title must not be null");
        this.link = link;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Organisation{" +
                "link=" + link +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organisation)) return false;
        Organisation organisation = (Organisation) o;
        return Objects.equals(getLink(), organisation.getLink()) &&
                Objects.equals(getStartDate(), organisation.getStartDate()) &&
                Objects.equals(getEndDate(), organisation.getEndDate()) &&
                Objects.equals(getTitle(), organisation.getTitle()) &&
                Objects.equals(getDescription(), organisation.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLink(), getStartDate(), getEndDate(), getTitle(), getDescription());
    }
}
