package com.spor.webapp.storage;

import com.spor.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final int STORAGE_SIZE = 10000;
    private Resume[] storage = new Resume[STORAGE_SIZE];
    private int size = 0;

    public void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    public void save(Resume resume) {
        int index = getMatch(resume.getUuid());

        if (size == STORAGE_SIZE) {
            System.out.println("Not enough space in storage");
        } else if (index != -1) {
            System.out.println("Resume already exist");
        } else {
            storage[size] = resume;
            size++;
        }
    }

    public void update(Resume resume) {
        int index = getMatch(resume.getUuid());

        if (index == -1) {
            System.out.println("Resume not found");
        } else {
            storage[index] = resume;
        }
    }

    public Resume get(String uuid) {
        int index = getMatch(uuid);

        if (index == -1) {
            System.out.println("Resume not found");
        } else {
            return storage[index];
        }
        return null;
    }

    public void delete(String uuid) {
        int index = getMatch(uuid);

        if (index == -1) {
            System.out.println("Resume not found");
        } else {
            size--;
            if (size - index >= 0) {
                System.arraycopy(storage, index + 1, storage, index, size - index);
            }
        }
    }

    private int getMatch(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] resumes = new Resume[size];
        System.arraycopy(storage, 0, resumes, 0, size);
        return resumes;
    }

    public int size() {
        return size;
    }
}
