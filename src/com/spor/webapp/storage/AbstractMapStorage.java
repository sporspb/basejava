package com.spor.webapp.storage;

import com.spor.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractMapStorage extends AbstractStorage {

    Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void doSave(Resume resume, Object key) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = new ArrayList<>(storage.values());
        list.sort(Resume.RESUME_COMPARATOR);
        return list;
    }

    @Override
    public int size() {
        return storage.size();
    }
}