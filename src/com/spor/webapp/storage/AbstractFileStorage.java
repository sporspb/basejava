package com.spor.webapp.storage;

import com.spor.webapp.exception.StorageException;
import com.spor.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    protected abstract void doWrite(Resume resume, File file) throws IOException;

    protected abstract Resume doRead(File file) throws IOException;

    @Override
    public void clear() {
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                doDelete(file);
            }
        }
    }

    @Override
    public int size() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Directory read error", null);
        }
        return files.length;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void doUpdate(Resume resume, File file) {
        try {
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void doSave(Resume resume, File file) {
        try {
            file.createNewFile();
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected Resume doGet(File file) {
        Resume resume;
        try {
            resume = doRead(file);
        } catch (IOException e) {
            throw new StorageException("IO error doGet()", file.getName(), e);
        }
        return resume;
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("Delete file error", file.getName());
        }
    }

    @Override
    protected List<Resume> getResumeList() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Directory read error", null);
        }
        List<Resume> resumeList = new ArrayList<>();
        for (File file : files) {
            resumeList.add(doGet(file));
        }
        return resumeList;
    }
}
