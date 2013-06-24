package com.khotyn.hotcode;

import org.objectweb.asm.ClassReader;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * The entry of the hotcode agent.
 * khotyn 13-6-24 下午7:15
 */
public class AgentMain {
    public static void premain(String agentArgs, Instrumentation inst) {
        ClassRedifiner.setInstrumentation(inst);
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
                ClassReader cr = new ClassReader(classfileBuffer);
                return null;
            }
        });
    }
}
