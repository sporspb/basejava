package com.spor.webapp.storage;

import com.spor.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    protected List<Resume> storage = new ArrayList<>();

    @Override
    protected void doSave(Resume resume, int index) {
        storage.add(resume);
    }

    @Override
    protected void doUpdate(Resume resume, int index) {
        storage.set(index, resume);
    }

    @Override
    protected Resume doGet(int index) {
        return storage.get(index);
    }

    @Override
    public void doDelete(int index) {
        storage.remove(index);
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    protected int getIndex(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}