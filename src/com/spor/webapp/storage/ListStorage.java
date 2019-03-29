package com.spor.webapp.storage;

import com.spor.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {

    protected List<Resume> storage = new ArrayList<>();

    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void doSave(Resume resume, Integer key) {
        storage.add(resume);
    }

    @Override
    protected void doUpdate(Resume resume, Integer key) {
        storage.set(key, resume);
    }

    @Override
    protected Resume doGet(Integer key) {
        return storage.get(key);
    }

    @Override
    public void doDelete(Integer key) {
        storage.remove((int) key);
    }

    @Override
    public List<Resume> getResumeList() {
        return new ArrayList<>(storage);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected boolean checkKeyExist(Integer key) {
        return key >= 0;
    }
}