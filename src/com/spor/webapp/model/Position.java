package com.spor.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Position {
    private LocalDate startDate;
    private LocalDate endDate;
    private String title;
    private String description;

    public Position(LocalDate startDate, LocalDate endDate, String title, String description) {
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(endDate, "endDate must not be null");
        Objects.requireNonNull(title, "title must not be null");
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        Position position = (Position) o;
        return getStartDate().equals(position.getStartDate()) &&
                getEndDate().equals(position.getEndDate()) &&
                getTitle().equals(position.getTitle()) &&
                Objects.equals(getDescription(), position.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStartDate(), getEndDate(), getTitle(), getDescription());
    }
}
