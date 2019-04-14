package com.spor.webapp.storage;

import com.spor.webapp.exception.StorageException;
import com.spor.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
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
    protected final void doSave(Resume resume, Integer key) {
        if (size == STORAGE_SIZE) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            saveResume(resume, key);
            size++;
        }
    }

    @Override
    protected final void doUpdate(Resume resume, Integer key) {
        storage[key] = resume;
    }

    @Override
    protected final Resume doGet(Integer key) {
        return storage[key];
    }

    @Override
    protected final void doDelete(Integer key) {
        deleteResume(key);
        storage[size - 1] = null;
        size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    protected final List<Resume> getResumeList() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
    }

    @Override
    protected boolean isExist(Integer key) {
        return key >= 0;
    }

    protected abstract void saveResume(Resume resume, int index);

    protected abstract void deleteResume(int index);
}