package org.hotcode.hotcode;

import org.hotcode.hotcode.asm.adapters.BeforeMethodCheckAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import org.hotcode.hotcode.asm.adapters.AddClassReloaderAdapter;
import org.hotcode.hotcode.asm.adapters.AddFieldsHolderAdapter;
import org.hotcode.hotcode.asm.adapters.ClinitClassAdapter;

/**
 * @author hotcode.huangt 13-6-26 PM2:17
 */
public class ClassTransformer {

    public static byte[] transform(Long classLoaderIndex, Long classIndex, byte[] classFile) {
        ClassReader cr = new ClassReader(classFile);
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS + ClassWriter.COMPUTE_FRAMES);
        ClassVisitor cv = new AddFieldsHolderAdapter(cw);
        cv = new AddClassReloaderAdapter(cv);
        cv = new ClinitClassAdapter(cv, classLoaderIndex, classIndex);
        cv = new BeforeMethodCheckAdapter(cv);
        cr.accept(cv, 0);
        return cw.toByteArray();
    }
}
