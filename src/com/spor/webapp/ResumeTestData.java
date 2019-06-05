package com.spor.webapp;

import com.spor.webapp.model.Link;
import com.spor.webapp.model.Resume;
import com.spor.webapp.model.TextListSection;
import com.spor.webapp.model.TextSection;
import com.spor.webapp.storage.Storage;

import java.util.UUID;

import static com.spor.webapp.model.ContactType.*;
import static com.spor.webapp.model.SectionType.*;

public class ResumeTestData {
    public static final String UUID_1 = UUID.randomUUID().toString();
    public static final String UUID_2 = UUID.randomUUID().toString();
    public static final String UUID_3 = UUID.randomUUID().toString();
    public static final String UUID_4 = UUID.randomUUID().toString();
    public static final Resume RESUME_1;
    public static final Resume RESUME_2;
    public static final Resume RESUME_3;
    public static final Resume RESUME_4;

    static {
        RESUME_1 = new Resume(UUID_1, "Fred");
        RESUME_2 = new Resume(UUID_2, "Alice");
        RESUME_3 = new Resume(UUID_3, "Peter");
        RESUME_4 = new Resume(UUID_4, "Barry");

        RESUME_1.setContacts(PHONE, new Link("+7(921) 855-0482"));
        RESUME_2.setContacts(SKYPE, new Link("grigory.kislin"));
        RESUME_3.setContacts(MAIL, new Link("gkislin@yandex.ru"));
        RESUME_4.setContacts(LINKEDIN, new Link("LinkedIn"));
        RESUME_1.setContacts(HOME_PAGE, new Link("Домашняя страница"));

        RESUME_1.setSections(OBJECTIVE, new TextSection("Ведущий по Java Web и Enterprise технологиям"));
        RESUME_1.setSections(PERSONAL, new TextSection("Аналитический склад ума"));
        RESUME_1.setSections(ACHIEVEMENT, new TextListSection("Реализация протоколов по приему платежей."));
        RESUME_1.setSections(QUALIFICATIONS, new TextListSection("MySQL, SQLite, MS SQL, HSQLDB"));
        RESUME_2.setSections(OBJECTIVE, new TextSection("TEST2"));
        RESUME_2.setSections(QUALIFICATIONS, new TextListSection("Spring, Hibernate"));
    }

    protected Storage storage;
}
