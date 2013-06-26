package com.khotyn.hotcode;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Version class file.
 * 
 * @author khotyn.huangt 13-6-25 PM5:10
 */
public abstract class VersionedClassFile {

    /**
     * The version of the class file.
     */
    private AtomicLong version;

    public VersionedClassFile(long initialVersion){
        version = new AtomicLong(initialVersion);
    }

    /**
     * @param version
     */
    public void updateVersion(long version) {
        this.version.compareAndSet(this.version.get(), version);
    }

    /**
     * Increment the version
     */
    public void incVersion() {
        this.version.incrementAndGet();
    }

    public abstract boolean changed();
}
