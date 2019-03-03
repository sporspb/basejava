package com.spor.webapp.storage;

import com.spor.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final static int STORAGE_SIZE = 10_000;
    private Resume[] storage = new Resume[STORAGE_SIZE];
    private int size = 0;

    public void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());

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
        int index = getIndex(resume.getUuid());

        if (index == -1) {
            System.out.println("Resume not found");
        } else {
            storage[index] = resume;
        }
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

    public void delete(String uuid) {
        int index = getIndex(uuid);

        if (index == -1) {
            System.out.println("Resume not found");
        } else {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
