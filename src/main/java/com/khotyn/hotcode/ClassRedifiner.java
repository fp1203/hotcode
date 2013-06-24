package com.khotyn.hotcode;

import java.lang.instrument.Instrumentation;

/**
 * Class redifiner, holds the instrumentation to redefine classes.
 * khotyn 13-6-24 下午8:02
 */
public class ClassRedifiner {
    private static Instrumentation inst;

    public static void setInstrumentation(Instrumentation inst) {
        ClassRedifiner.inst = inst;
    }
}
