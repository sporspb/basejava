package com.spor.webapp.storage;

import com.spor.webapp.model.Resume;

public abstract class AbstractArrayStorage implements Storage {
    protected final static int STORAGE_SIZE = 10_000;

    protected Resume[] storage = new Resume[STORAGE_SIZE];
    protected int size = 0;

    public int size() {
        return size;
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

    protected abstract int getIndex(String uuid);
}
