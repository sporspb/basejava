package com.spor.webapp.storage;

import com.spor.webapp.exception.ExistStorageException;
import com.spor.webapp.exception.NotExistStorageException;
import com.spor.webapp.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import static com.spor.webapp.model.ContactType.*;
import static com.spor.webapp.model.SectionType.*;
import static com.spor.webapp.storage.AbstractStorage.RESUME_COMPARATOR;
import static java.util.Arrays.asList;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = new File("C:\\Users\\Spor\\IdeaProjects\\storage");

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;
    protected Storage storage;

    static {
        RESUME_1 = new Resume(UUID_1, "fullName1");
        RESUME_2 = new Resume(UUID_2, "fullName2");
        RESUME_3 = new Resume(UUID_3, "fullName3");
        RESUME_4 = new Resume(UUID_4, "fullName4");

        RESUME_1.setContacts(PHONE, new Link("+7(921) 855-0482", ""));
        RESUME_1.setContacts(SKYPE, new Link("grigory.kislin", "skype:grigory.kislin"));
        RESUME_1.setContacts(MAIL, new Link("gkislin@yandex.ru", "mailto:gkislin@yandex.ru"));
        RESUME_1.setContacts(PROFILE, new Link("LinkedIn", "https://www.linkedin.com/in/gkislin"));
        RESUME_1.setContacts(LINK, new Link("Домашняя страница", "http://gkislin.ru/"));
        RESUME_1.setSections(OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        RESUME_1.setSections(PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
        RESUME_1.setSections(ACHIEVEMENT, new TextListSection("Реализация протоколов по приему платежей."));
        RESUME_1.setSections(QUALIFICATIONS, new TextListSection("MySQL, SQLite, MS SQL, HSQLDB "));
        RESUME_1.setSections(EXPERIENCE, new OrganisationSection(
                new Organisation(
                        new Link("Alcatel", "http://www.alcatel.ru/"),
                        new Position(
                                LocalDate.of(1997, 9, 1),
                                LocalDate.of(2005, 1, 1),
                                "Инженер по аппаратному и программному тестированию",
                                "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM)."

                        ))));

    }

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void save() throws Exception {
        storage.save(RESUME_4);
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(RESUME_4, storage.get(UUID_4));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(RESUME_2);
    }

    @Test
    public void update() throws Exception {
        Resume resume = new Resume(UUID_2, "fullName2");
        storage.update(resume);
        Assert.assertEquals(resume, storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(RESUME_4);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_2);
        Assert.assertEquals(2, storage.size());
        storage.get(UUID_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete(UUID_4);
    }

    @Test
    public void get() throws Exception {
        Assert.assertEquals(RESUME_2, storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get(UUID_4);
    }

    @Test
    public void getAllSorted() throws Exception {
        List<Resume> resumeList = asList(RESUME_1, RESUME_2, RESUME_3);
        resumeList.sort(RESUME_COMPARATOR);
        List<Resume> getAllResumes = storage.getAllSorted();
        Assert.assertEquals(resumeList, getAllResumes);
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }
}