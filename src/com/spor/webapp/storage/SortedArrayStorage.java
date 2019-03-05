package com.spor.webapp.storage;

import com.spor.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

        @Override
    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        int insertPosition = -index - 1;

        if (size == STORAGE_SIZE) {
            System.out.println("Not enough space in storage");
        } else if (index >= 0) {
            System.out.println("Resume already exist");
        } else {
            System.arraycopy(storage, insertPosition, storage, insertPosition + 1, size - insertPosition);
            storage[insertPosition] = resume;
            size++;
        }
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());

        if (index < 0) {
            System.out.println("Resume not found");
        } else {
            storage[index] = resume;
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);

        if (index < 0) {
            System.out.println("Resume not found");
        } else {
            System.arraycopy(storage, index + 1, storage, index, size - index);
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
