package com.khotyn.hotcode;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Universe
 * 
 * @author khotyn.huangt 13-6-26 PM5:13
 */
public class HotCode {

    private static AtomicLong                      indexGenerator          = new AtomicLong(0);
    private static Map<ClassLoader, Long>          classLoaderIndexMap     = new HashMap<ClassLoader, Long>();
    private static Map<Long, ClassReloaderManager> classReloaderManagerMap = new HashMap<Long, ClassReloaderManager>();

    public static Long getIndex(ClassLoader classLoader) {
        return classLoaderIndexMap.get(classLoader);
    }

    public static ClassReloaderManager getClassReloaderManager(long index) {
        return classReloaderManagerMap.get(index);
    }

    public static Long putClassReloaderManager(ClassLoader classLoader, ClassReloaderManager classReloaderManager) {
        Long index = indexGenerator.incrementAndGet();
        classLoaderIndexMap.put(classLoader, index);
        classReloaderManagerMap.put(index, classReloaderManager);
        return index;
    }

    public static void registerClassLoader(ClassLoader classLoader) {
        if (classLoaderIndexMap.get(classLoader) != null) {
            return;
        }

        putClassReloaderManager(classLoader, new ClassReloaderManager(classLoader));
    }
}
