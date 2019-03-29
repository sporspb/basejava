package com.spor.webapp.storage;

import com.spor.webapp.exception.ExistStorageException;
import com.spor.webapp.exception.NotExistStorageException;
import com.spor.webapp.model.Resume;

import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {

    protected abstract void doSave(Resume resume, SK key);

    protected abstract void doUpdate(Resume resume, SK key);

    protected abstract Resume doGet(SK key);

    protected abstract void doDelete(SK key);

    protected abstract List<Resume> getResumeList();

    protected abstract SK getSearchKey(String uuid);

    protected abstract boolean checkKeyExist(SK key);

    @Override
    public void update(Resume resume) {
        SK key = getExistKey(resume.getUuid());
        doUpdate(resume, key);
    }

    @Override
    public void save(Resume resume) {
        SK key = getNotExistKey(resume.getUuid());
        doSave(resume, key);
    }

    @Override
    public Resume get(String uuid) {
        SK key = getExistKey(uuid);
        return doGet(key);
    }

    @Override
    public void delete(String uuid) {
        SK key = getExistKey(uuid);
        doDelete(key);
    }

    @Override
    public final List<Resume> getAllSorted() {
        List<Resume> resumeList = getResumeList();
        resumeList.sort(Resume.RESUME_COMPARATOR);
        return resumeList;
    }

    private SK getExistKey(String uuid) {
        SK key = getSearchKey(uuid);
        if (!checkKeyExist(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    private SK getNotExistKey(String uuid) {
        SK key = getSearchKey(uuid);
        if (checkKeyExist(key)) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }
}