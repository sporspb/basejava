package com.spor.webapp.storage;

import com.spor.webapp.exception.ExistStorageException;
import com.spor.webapp.exception.NotExistStorageException;
import com.spor.webapp.exception.StorageException;
import com.spor.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
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
    public final void save(Resume resume) {
        int index = getIndex(resume.getUuid());

        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        } else if (size == STORAGE_SIZE) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            saveResume(resume, index);
            size++;
        }
    }

    @Override
    public final void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            storage[index] = resume;
        }
    }

    @Override
    public final Resume get(String uuid) {
        int index = getIndex(uuid);

        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            return storage[index];
        }
    }

    @Override
    public final void delete(String uuid) {
        int index = getIndex(uuid);

        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            deleteResume(index);
            storage[size - 1] = null;
            size--;
        }
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

    protected abstract int getIndex(String uuid);
}