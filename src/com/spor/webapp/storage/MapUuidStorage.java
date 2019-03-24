package com.spor.webapp.storage;

import com.spor.webapp.model.Resume;

public class MapUuidStorage extends AbstractMapStorage {

    @Override
    protected void doUpdate(Resume resume, Object key) {
        storage.put((String) key, resume);
    }

    @Override
    protected Resume doGet(Object key) {
        return storage.get(key);
    }

    @Override
    protected void doDelete(Object key) {
        storage.remove(key);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean checkKeyExist(Object key) {
        return storage.containsKey(key);
    }
}