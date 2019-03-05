package com.spor.webapp.storage;

import com.spor.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void update(Resume resume) {

    }

    @Override
    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());

        if (size == STORAGE_SIZE) {
            System.out.println("Not enough space in storage");
        } else if (index >= 0) {
            System.out.println("Resume already exist");
        } else {
            System.arraycopy(storage, index + 1, storage, index, size - index);
            size++;
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);

        if (index < 0) {
            System.out.println("Resume not found");
        } else {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }

    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
