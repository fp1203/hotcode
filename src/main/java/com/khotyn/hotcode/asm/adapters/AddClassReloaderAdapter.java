package com.khotyn.hotcode.asm.adapters;

import java.lang.reflect.Modifier;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import com.khotyn.hotcode.ClassReloader;
import com.khotyn.hotcode.constants.HotCodeConstant;

/**
 * Add {@link com.khotyn.hotcode.ClassReloader} to the class.
 * 
 * @author khotyn.huangt 13-6-26 AM9:59
 */
public class AddClassReloaderAdapter extends ClassVisitor {

    public AddClassReloaderAdapter(ClassVisitor cv){
        super(Opcodes.ASM4, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        if (!Modifier.isInterface(access)) {
            cv.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, HotCodeConstant.HOTCODE_CLASS_RELOADER_FIELDS,
                          Type.getDescriptor(ClassReloader.class), null, null);
        }
        super.visit(version, access, name, signature, superName, interfaces);
    }
}
