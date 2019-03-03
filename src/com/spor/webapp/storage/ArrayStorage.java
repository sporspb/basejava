package com.spor.webapp.storage;

import com.spor.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    public void save(Resume resume) {
        for (int i = 0; i < size; i++) {
            if (resume.toString().equals(storage[i].toString())) {
                System.out.println("Resume already exist");
                return;
            }
        }
        storage[size] = resume;
        size++;
    }

    public void update(Resume resume) {
        for (int i = 0; i < size; i++) {
            if (resume.toString().equals(storage[i].toString())) {
                storage[i] = resume;
                return;
            }
        }
        System.out.println("Resume not found");
    }

    public Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    public void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                size--;
                if (size - i >= 0) {
                    System.arraycopy(storage, i + 1, storage, i, size - i);
                }
            }
        }
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
