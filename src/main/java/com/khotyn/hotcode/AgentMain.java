package com.khotyn.hotcode;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import com.khotyn.hotcode.asm.adapters.AddClassReloaderAdapter;
import com.khotyn.hotcode.asm.adapters.AddFieldsHolderAdapter;
import com.khotyn.hotcode.asm.adapters.BeforeMethodCheckAdapter;
import com.khotyn.hotcode.asm.adapters.ClinitClassAdapter;

/**
 * The entry of the HotCode agent.
 * 
 * @author khotyn 2013-06-24 20:21:43
 */
public class AgentMain {

    private static final String[] SKIP_PKGS = { "java", "javax", "sun", "com/apple/java" };

    public static void premain(String agentArgs, Instrumentation inst) {
        ClassRedifiner.setInstrumentation(inst);
        inst.addTransformer(new ClassFileTransformer() {

            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain, byte[] classfileBuffer)
                                                                                              throws IllegalClassFormatException {
                for (String SKIP_PKG : SKIP_PKGS) {
                    if (className.startsWith(SKIP_PKG)) {
                        return classfileBuffer;
                    }
                }

                ClassReader cr = new ClassReader(classfileBuffer);
                ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS + ClassWriter.COMPUTE_FRAMES);
                ClassVisitor cv = new AddFieldsHolderAdapter(cw);
                cv = new AddClassReloaderAdapter(cv);
                cv = new ClinitClassAdapter(cv);
                cv = new BeforeMethodCheckAdapter(cv);
                cr.accept(cv, 0);
                byte[] classRedefined = cw.toByteArray();
                ClassDumper.dump(className, classRedefined);
                return classRedefined;
            }
        });
    }
}
