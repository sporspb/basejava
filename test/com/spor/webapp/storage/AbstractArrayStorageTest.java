package com.spor.webapp.storage;

import com.spor.webapp.exception.StorageException;
import com.spor.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() throws Exception {
        storage.clear();
        try {
            for (int i = 0; i < AbstractArrayStorage.STORAGE_SIZE; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            Assert.fail("Storage Overflowed!!!");
        }
        storage.save(new Resume());
    }
}