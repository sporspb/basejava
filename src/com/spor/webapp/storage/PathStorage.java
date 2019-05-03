package com.spor.webapp.storage;

import com.spor.webapp.exception.StorageException;
import com.spor.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PathStorage extends AbstractStorage<Path> {

    private Path directory;
    private Strategy strategy;

    protected PathStorage(String dir, Strategy strategy) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
        this.strategy = strategy;
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null, e);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Path size error", null, e);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected void doUpdate(Resume r, Path Path) {
        try {
            doWrite(r, new BufferedOutputStream(Files.newOutputStream(Path)));
        } catch (IOException e) {
            throw new StorageException("Path write error", r.getUuid(), e);
        }
    }

    @Override
    protected boolean isExist(Path Path) {
        return Files.exists(Path);
    }

    @Override
    protected void doSave(Resume r, Path Path) {
        doUpdate(r, Path);
    }

    @Override
    protected Resume doGet(Path Path) {
        try {
            return doRead(new BufferedInputStream(Files.newInputStream((Path))));
        } catch (IOException e) {
            throw new StorageException("Path read error", Path.toString(), e);
        }
    }

    @Override
    protected void doDelete(Path Path) {
        try {
            Files.deleteIfExists(Path);
        } catch (IOException e) {
            throw new StorageException("Path delete error", Path.toString(), e);
        }
    }

    @Override
    protected List<Resume> getResumeList() {
        List<Resume> resumeList = new ArrayList<>();
        try {
            Files.list(directory).forEach(path -> resumeList.add(doGet(path)));
        } catch (IOException e) {
            throw new StorageException("Path read error", null);
        }
        return resumeList;
    }

    private void doWrite(Resume resume, OutputStream os) throws IOException {
        strategy.doWrite(resume, os);
    }

    private Resume doRead(InputStream is) throws IOException {
        return strategy.doRead(is);
    }
}