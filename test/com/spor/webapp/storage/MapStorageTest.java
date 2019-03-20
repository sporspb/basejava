package com.spor.webapp.storage;

import com.spor.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

import java.util.Objects;

public class MapStorageTest extends AbstractStorageTest {

    public MapStorageTest() {
        super(new MapStorage());
    }

    @Test
    public void getAll() throws Exception {
        Resume[] resumes1 = storage.getAll();

        for (Resume resume : resumes1) {
            boolean findEqual = false;
            for (Resume resume1 : resumes) {
                if (Objects.equals(resume, resume1)) {
                    findEqual = true;
                    break;
                }
            }
            if (!findEqual) Assert.fail("Arrays contains not equal elements.");
        }
    }
}