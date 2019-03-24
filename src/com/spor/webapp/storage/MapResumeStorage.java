package com.spor.webapp.storage;

import com.spor.webapp.model.Resume;

public class MapResumeStorage extends AbstractMapStorage {

    @Override
    protected void doSave(Resume resume, Object key) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void doUpdate(Resume resume, Object key) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume doGet(Object key) {
        return (Resume) key;
    }

    @Override
    protected void doDelete(Object key) {
        storage.values().remove(key);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean checkKeyExist(Object key) {
        return storage.containsValue(key);
    }
}