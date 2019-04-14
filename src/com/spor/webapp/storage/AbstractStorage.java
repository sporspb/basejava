package com.spor.webapp.storage;

import com.spor.webapp.exception.ExistStorageException;
import com.spor.webapp.exception.NotExistStorageException;
import com.spor.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {

    public static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    protected abstract void doSave(Resume resume, SK key);

    protected abstract void doUpdate(Resume resume, SK key);

    protected abstract Resume doGet(SK key);

    protected abstract void doDelete(SK key);

    protected abstract List<Resume> getResumeList();

    protected abstract SK getSearchKey(String uuid);

    protected abstract boolean isExist(SK key);

    @Override
    public void update(Resume resume) {
        LOG.info("Update" + resume);
        SK key = getExistKey(resume.getUuid());
        doUpdate(resume, key);
    }

    @Override
    public void save(Resume resume) {
        LOG.info("Save" + resume);
        SK key = getNotExistKey(resume.getUuid());
        doSave(resume, key);
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get" + uuid);
        SK key = getExistKey(uuid);
        return doGet(key);
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete" + uuid);
        SK key = getExistKey(uuid);
        doDelete(key);
    }

    @Override
    public final List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> resumeList = getResumeList();
        resumeList.sort(RESUME_COMPARATOR);
        return resumeList;
    }

    private SK getExistKey(String uuid) {
        SK key = getSearchKey(uuid);
        if (!isExist(key)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    private SK getNotExistKey(String uuid) {
        SK key = getSearchKey(uuid);
        if (isExist(key)) {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return key;
    }
}