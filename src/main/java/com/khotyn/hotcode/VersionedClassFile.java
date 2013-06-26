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
    protected AtomicLong version = new AtomicLong(0);

    protected byte[]     classFile;

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

    public long getVersion() {
        return version.longValue();
    }

    public byte[] getClassFile() {
        return classFile;
    }

    public void setClassFile(byte[] classFile) {
        this.classFile = classFile;
    }

    /**
     * Check if class file is changed.
     * 
     * @return
     */
    public abstract boolean changed();

    /**
     * Reload class file.
     * 
     * @return
     */
    public abstract byte[] reloadAndGetClassFile();
}
