package com.spor.webapp.storage;

import com.spor.webapp.exception.ExistStorageException;
import com.spor.webapp.exception.NotExistStorageException;
import com.spor.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract void doSave(Resume resume, int index);

    protected abstract void doUpdate(Resume resume, int index);

    protected abstract Resume doGet(int index);

    protected abstract void doDelete(int index);

    @Override
    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            doUpdate(resume, index);
        }
    }

    @Override
    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            doSave(resume, index);
        }
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            return doGet(index);
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            doDelete(index);
        }
    }

    protected abstract int getIndex(String uuid);
}