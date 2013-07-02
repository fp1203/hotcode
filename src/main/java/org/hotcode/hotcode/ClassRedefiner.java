package org.hotcode.hotcode;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

/**
 * Class redefiner, holds the instrumentation to redefine classes.
 * 
 * @author hotcode 2013-06-24 20:22:40
 */
public class ClassRedefiner {

    private static Instrumentation inst;

    public static void setInstrumentation(Instrumentation inst) {
        ClassRedefiner.inst = inst;
    }

    public static void redefine(Class<?> klass, byte[] classFile) {
        try {
            inst.redefineClasses(new ClassDefinition(klass, classFile));
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // TODO
        } catch (UnmodifiableClassException e) {
            e.printStackTrace(); // TODO
        }
    }
}
