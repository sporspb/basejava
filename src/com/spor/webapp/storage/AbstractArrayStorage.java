package com.spor.webapp.storage;

import com.spor.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected final static int STORAGE_SIZE = 10_000;

    protected Resume[] storage = new Resume[STORAGE_SIZE];
    protected int size = 0;

    public final int size() {
        return size;
    }

    public final void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);

        if (index == -1) {
            System.out.println("Resume not found");
            return null;
        } else {
            return storage[index];
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public final Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public abstract void save(Resume resume);

    public abstract void update(Resume resume);

    public abstract void delete(String uuid);

    protected abstract int getIndex(String uuid);
}
