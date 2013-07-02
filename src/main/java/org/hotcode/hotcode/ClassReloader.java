package org.hotcode.hotcode;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.hotcode.hotcode.constants.HotCodeConstant;
import org.hotcode.hotcode.structure.HotCodeClass;

/**
 * Class reloader
 * 
 * @author hotcode.huangt 13-6-25 PM5:20
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

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    private boolean reload() {
        byte[] transformedClassFile = ClassTransformer.transform(classReloaderManagerIndex, classIndex,
                                                                 versionedClassFile.reloadAndGetClassFile());
        ClassDumper.dump(originClass.getClassName().replace('.', '/'), transformedClassFile);
        try {
            ClassRedefiner.redefine(classLoader.loadClass(originClass.getClassName()), transformedClassFile);
            Class<?> klass = classLoader.loadClass(originClass.getClassName());

            // reinit class.
            try {
                Method method = klass.getMethod(HotCodeConstant.HOTCODE_CLINIT_METHOD_NAME);
                method.invoke(klass);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace(); // TODO
            }
            return true;
        } catch (ClassNotFoundException e) {
            // TODO
            return false;
        }
    }
}
