package com.khotyn.hotcode;

import com.khotyn.hotcode.structure.HotCodeClass;

/**
 * Class reloader
 * 
 * @author khotyn.huangt 13-6-25 PM5:20
 */
public class ClassReloader {

    private Long               classReloaderManagerIndex;
    private Long               classIndex;
    private HotCodeClass       originClass;
    private VersionedClassFile versionedClassFile;
    private ClassLoader        classLoader;

    public ClassReloader(Long classReloaderManagerIndex, Long classIndex, VersionedClassFile versionedClassFile,
                         HotCodeClass originClass, ClassLoader classLoader){
        this.classReloaderManagerIndex = classReloaderManagerIndex;
        this.classIndex = classIndex;
        this.versionedClassFile = versionedClassFile;
        this.originClass = originClass;
        this.classLoader = classLoader;
    }

    public boolean checkAndReload() {
        return versionedClassFile.changed() && reload();
    }

    public HotCodeClass getOriginClass() {
        return originClass;
    }

    private boolean reload() {
        byte[] transformedClassFile = ClassTransformer.transform(classReloaderManagerIndex, classIndex,
                                                                 versionedClassFile.reloadAndGetClassFile());
        try {
            ClassRedefiner.redefine(classLoader.loadClass(originClass.getClassName()), transformedClassFile);
            return true;
        } catch (ClassNotFoundException e) {
            // TODO
            return false;
        }
    }
}
