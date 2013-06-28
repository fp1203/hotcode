package com.khotyn.hotcode;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

/**
 * @author khotyn.huangt 13-6-26 AM11:21
 */
public class FileSystemVersionedClassFile extends VersionedClassFile {

    /**
     * The class file in the file java.
     */
    private File file;

    public FileSystemVersionedClassFile(File file){
        try {
            this.file = file;
            updateVersion(file.lastModified());
            setClassFile(FileUtils.readFileToByteArray(file));
        } catch (IOException e) {
            e.printStackTrace(); // TODO
        }
    }

    @Override
    public boolean changed() {
        return file.lastModified() > getVersion();
    }

    @Override
    public byte[] reloadAndGetClassFile() {
        updateVersion(file.lastModified());
        try {
            setClassFile(FileUtils.readFileToByteArray(file));
            return classFile;
        } catch (IOException e) {
            e.printStackTrace(); // TODO
            return null;
        }
    }
}
