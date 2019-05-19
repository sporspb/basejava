package com.spor.webapp.storage;

import com.spor.webapp.Config;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(new SqlStorage(Config.get().getUrl(), Config.get().getLogin(), Config.get().getPassword()));
    }
}
