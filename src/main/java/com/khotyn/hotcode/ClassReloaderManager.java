package com.khotyn.hotcode;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author khotyn.huangt 13-6-26 PM4:36
 */
public class ClassReloaderManager {

    private ClassLoader              classLoader;
    /**
     * Index generator.
     */
    private AtomicLong               indexGenerator   = new AtomicLong(0);

    /**
     * Map from a class of a class loader to index.
     */
    private Map<Class, Long>         classMap         = new HashMap<Class, Long>();
    /**
     * Map from indexGenerator to class reloader.
     */
    private Map<Long, ClassReloader> classReloaderMap = new HashMap<Long, ClassReloader>();

    public ClassReloaderManager(ClassLoader classLoader){
        this.classLoader = classLoader;
    }

    public Long getIndex(Class klass) {
        return classMap.get(klass);
    }

    public Long getNextAvailableIndex() {
        return indexGenerator.incrementAndGet();
    }

    public ClassReloader getClassReloader(long index) {
        return classReloaderMap.get(index);
    }

    public void putClassReloader(Long index, Class klass, ClassReloader classReloader) {
        classMap.put(klass, index);
        classReloaderMap.put(index, classReloader);
    }
}
