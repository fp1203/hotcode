package com.khotyn.hotcode.asm.adapters;

import org.apache.commons.lang.StringUtils;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import com.khotyn.hotcode.CodeFragment;

/**
 * Add "<clinit>" to class if not exist, or else transform the "<clinit>" methods.
 * 
 * @author khotyn.huangt 13-6-24 PM10:13
 */
public class ClinitClassAdapter extends ClassVisitor {

    private boolean hasClinitMethod = false;
    private String  classInternalName;
    private int     access;

    public ClinitClassAdapter(ClassVisitor cv){

        super(Opcodes.ASM4, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.classInternalName = name;
        this.access = access;
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);

        if (StringUtils.equals(name, "<clinit>")) {
            hasClinitMethod = true;
            mv = new ClinitMethodAdapter(access, classInternalName, mv);
        }

        return mv;
    }

    @Override
    public void visitEnd() {
        if (hasClinitMethod) {
            return;
        }

        MethodVisitor mv = cv.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "<clinit>",
                                          Type.getMethodDescriptor(Type.VOID_TYPE), null, null);
        mv.visitCode();
        CodeFragment.clinitFieldInit(mv, access, classInternalName);
        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();
        super.visitEnd();
    }
}
