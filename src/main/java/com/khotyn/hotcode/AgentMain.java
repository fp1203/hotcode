package com.khotyn.hotcode;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import com.khotyn.hotcode.asm.adapters.AddFieldsHolderAdapter;
import com.khotyn.hotcode.asm.adapters.ClinitClassAdapter;

/**
 * The entry of the HotCode agent.
 * 
 * @author khotyn 2013-06-24 20:21:43
 */
public class AgentMain {

    public static void premain(String agentArgs, Instrumentation inst) {
        ClassRedifiner.setInstrumentation(inst);
        inst.addTransformer(new ClassFileTransformer() {

            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain, byte[] classfileBuffer)
                                                                                              throws IllegalClassFormatException {

                ClassReader cr = new ClassReader(classfileBuffer);
                ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS + ClassWriter.COMPUTE_FRAMES);
                ClassVisitor cv = new AddFieldsHolderAdapter(cw);
                cv = new ClinitClassAdapter(cv);
                cr.accept(cv, ClassReader.SKIP_FRAMES + ClassReader.SKIP_DEBUG);
                byte[] classRedefined = cw.toByteArray();
                ClassDumper.dump(className, classRedefined);
                return classRedefined;
            }
        });
    }
}
