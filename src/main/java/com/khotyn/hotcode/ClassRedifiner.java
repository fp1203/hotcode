package com.khotyn.hotcode;

import java.lang.instrument.Instrumentation;

/**
 * Class redifiner, holds the instrumentation to redefine classes.
 * 
 * @author khotyn 2013-06-24 20:22:40
 */
public class ClassRedifiner {

    private static Instrumentation inst;

    public static void setInstrumentation(Instrumentation inst) {
        ClassRedifiner.inst = inst;
    }
}
