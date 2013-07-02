package org.hotcode.hotcode;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author hotcode.huangt 13-6-26 PM4:36
 */
public class ClassReloaderManager {

    private ClassLoader              classLoader;
    /**
     * Index generator.
     */
    private AtomicLong               indexGenerator   = new AtomicLong(0);

    /**
     * Map from a internal name of a class to index.
     */
    private Map<String, Long>        classMap         = new HashMap<String, Long>();
    /**
     * Map from indexGenerator to class reloader.
     */
    private Map<Long, ClassReloader> classReloaderMap = new HashMap<Long, ClassReloader>();

    public ClassReloaderManager(ClassLoader classLoader){
        this.classLoader = classLoader;
    }

    public Long getIndex(String classInternalName) {
        return classMap.get(classInternalName);
    }

    public Long getNextAvailableIndex() {
        return indexGenerator.incrementAndGet();
    }

    public ClassReloader getClassReloader(long index) {
        return classReloaderMap.get(index);
    }

    public void putClassReloader(Long index, String classInternalName, ClassReloader classReloader) {
        classMap.put(classInternalName, index);
        classReloaderMap.put(index, classReloader);
    }

    public ClassLoader getClassLoader() {
        return this.classLoader;
    }
}
