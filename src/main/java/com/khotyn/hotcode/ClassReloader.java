package com.khotyn.hotcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Class reloader
 * 
 * @author khotyn.huangt 13-6-25 PM5:20
 */
public class ClassReloader {

    /**
     * Class to be reload.
     */
    private Class<?>                          klass;
    private Map<Class<?>, VersionedClassFile> monitoredClassFile = new HashMap<Class<?>, VersionedClassFile>();

    public ClassReloader(String className){
        try {
            klass = Thread.currentThread().getContextClassLoader().loadClass(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean checkAndReload() {
        VersionedClassFile classFile = monitoredClassFile.get(klass);

        return classFile != null && classFile.changed() && reload();
    }

    private boolean reload() {
        return false;
    }
}
