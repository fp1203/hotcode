package com.khotyn.hotcode;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import com.khotyn.hotcode.asm.adapters.AddClassReloaderAdapter;
import com.khotyn.hotcode.asm.adapters.AddFieldsHolderAdapter;
import com.khotyn.hotcode.asm.adapters.BeforeMethodCheckAdapter;
import com.khotyn.hotcode.asm.adapters.ClinitClassAdapter;

/**
 * @author khotyn.huangt 13-6-26 PM2:17
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
