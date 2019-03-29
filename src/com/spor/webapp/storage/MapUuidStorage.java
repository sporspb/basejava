package com.spor.webapp.storage;

import com.spor.webapp.model.Resume;

public class MapUuidStorage extends AbstractMapStorage<String> {

    @Override
    protected void doUpdate(Resume resume, String key) {
        storage.put(key, resume);
    }

    @Override
    protected Resume doGet(String key) {
        return storage.get(key);
    }

    @Override
    protected void doDelete(String key) {
        storage.remove(key);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean checkKeyExist(String key) {
        return storage.containsKey(key);
    }
}