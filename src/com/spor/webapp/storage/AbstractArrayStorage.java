package com.spor.webapp.storage;

import com.spor.webapp.exception.StorageException;
import com.spor.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected final static int STORAGE_SIZE = 10_000;

    protected Resume[] storage = new Resume[STORAGE_SIZE];
    protected int size = 0;

    @Override
    public final int size() {
        return size;
    }

    @Override
    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected final void doSave(Resume resume, int index) {
        if (size == STORAGE_SIZE) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            saveResume(resume, index);
            size++;
        }
    }

    @Override
    protected final void doUpdate(Resume resume, int index) {
        storage[index] = resume;
    }

    @Override
    protected final Resume doGet(int index) {
        return storage[index];
    }

    @Override
    protected final void doDelete(int index) {
        deleteResume(index);
        storage[size - 1] = null;
        size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public final Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    protected abstract void saveResume(Resume resume, int index);

    protected abstract void deleteResume(int index);
}