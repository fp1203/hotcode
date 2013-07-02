package org.hotcode.hotcode;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

import org.apache.commons.io.IOUtils;
import org.hotcode.hotcode.java.lang.ClassLoaderAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;

/**
 * The entry of the HotCode agent.
 * 
 * @author hotcode 2013-06-24 20:21:43
 */
public class AgentMain {

    public static void premain(String agentArgs, Instrumentation inst) {
        ClassRedefiner.setInstrumentation(inst);

        InputStream is = ClassLoader.getSystemResourceAsStream(Type.getInternalName(ClassLoader.class) + ".class");

        try {
            ClassReader cr = new ClassReader(IOUtils.toByteArray(new BufferedInputStream(is)));
            ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS + ClassWriter.COMPUTE_FRAMES);
            ClassVisitor cv = new ClassLoaderAdapter(cw);
            cr.accept(cv, 0);
            byte[] transformedByte = cw.toByteArray();
            ClassDumper.dump(Type.getInternalName(ClassLoader.class), transformedByte);
            ClassDefinition classDefinition = new ClassDefinition(ClassLoader.class, transformedByte);

            try {
                inst.redefineClasses(classDefinition);
            } catch (ClassNotFoundException | UnmodifiableClassException e) {
                e.printStackTrace(); // To change body of catch statement use File | Settings | File Templates.
            }
        } catch (IOException e) {
            e.printStackTrace(); // To change body of catch statement use File | Settings | File Templates.
        }

    }
}
