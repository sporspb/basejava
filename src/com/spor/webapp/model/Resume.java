package com.spor.webapp.model;

import java.util.*;

/**
 * Initial resume class
 */
public class Resume {

    // Unique identifier
    private final String uuid;
    private final String fullName;
    public static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    private Map<ContactType, ContactData> contacts = new EnumMap<>(ContactType.class);
    private Map<SectionType, AbstractSectionData> sections = new EnumMap<>(SectionType.class);

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setContacts(ContactType type, ContactData data) {
        contacts.put(type, data);
    }

    public void setSections(SectionType type, AbstractSectionData data) {
        sections.put(type, data);
    }

    public Map<ContactType, ContactData> getContacts() {
        return contacts;
    }

    public Map<SectionType, AbstractSectionData> getSections() {
        return sections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return fullName.equals(resume.fullName) && (uuid.equals(resume.uuid));
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid) + Objects.hash(fullName);
    }

    @Override
    public String toString() {
        return uuid + " " + fullName + " " + contacts + " " + sections;
    }
}