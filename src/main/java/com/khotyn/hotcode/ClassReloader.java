package com.khotyn.hotcode;

/**
 * Class reloader
 * 
 * @author khotyn.huangt 13-6-25 PM5:20
 */
public class ClassReloader {

    private Long classReloaderManagerIndex;
    private Long               classIndex;
    private Class              klass;
    /**
     * The class file.
     */
    private VersionedClassFile versionedClassFile;

    public ClassReloader(Long classReloaderManagerIndex, Long classIndex, Class klass, VersionedClassFile versionedClassFile){
        this.classReloaderManagerIndex = classReloaderManagerIndex;
        this.classIndex = classIndex;
        this.klass = klass;
        this.versionedClassFile = versionedClassFile;
    }

    public boolean checkAndReload() {
        return versionedClassFile.changed() && reload();
    }

    private boolean reload() {
        byte[] transformedClassFile = ClassTransformer.transform(classReloaderManagerIndex, classIndex,
                                                                 versionedClassFile.reloadAndGetClassFile());
        ClassRedefiner.redefine(klass, transformedClassFile);
        return true;
    }
}
