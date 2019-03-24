package com.spor.webapp.storage;

import com.spor.webapp.exception.ExistStorageException;
import com.spor.webapp.exception.NotExistStorageException;
import com.spor.webapp.model.Resume;

import java.util.List;

public abstract class AbstractStorage implements Storage {

    protected abstract void doSave(Resume resume, Object key);

    protected abstract void doUpdate(Resume resume, Object key);

    protected abstract Resume doGet(Object key);

    protected abstract void doDelete(Object key);

    protected abstract List<Resume> getResumeList();

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean checkKeyExist(Object key);

    @Override
    public void update(Resume resume) {
        Object key = getExistKey(resume.getUuid());
        doUpdate(resume, key);
    }

    @Override
    public void save(Resume resume) {
        Object key = getNotExistKey(resume.getUuid());
        doSave(resume, key);
    }

    @Override
    public Resume get(String uuid) {
        Object key = getExistKey(uuid);
        return doGet(key);
    }

    @Override
    public void delete(String uuid) {
        Object key = getExistKey(uuid);
        doDelete(key);
    }

    public final List<Resume> getAllSorted() {
        List<Resume> resumeList = getResumeList();
        resumeList.sort(Resume.RESUME_COMPARATOR);
        return resumeList;
    }

    private Object getExistKey(String uuid) {
        Object key = getSearchKey(uuid);
        if (!checkKeyExist(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    private Object getNotExistKey(String uuid) {
        Object key = getSearchKey(uuid);
        if (checkKeyExist(key)) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }
}