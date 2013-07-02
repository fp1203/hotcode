package org.hotcode.hotcode.asm.adapters;

import org.hotcode.hotcode.ClassReloaderManager;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Replace the field access of a class
 * 
 * @author hotcode.huangt 13-6-26 PM9:32
 */
public class FieldAccessReplaceAdapter extends ClassVisitor {

    private ClassReloaderManager classReloaderManager;

    public FieldAccessReplaceAdapter(ClassVisitor cv, ClassReloaderManager classReloaderManager){
        super(Opcodes.ASM4, cv);
        this.classReloaderManager = classReloaderManager;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {

        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);

        return new MethodVisitor(Opcodes.ASM4, mv) {

            @Override
            public void visitFieldInsn(int opcode, String owner, String name, String desc) {
                Long index = classReloaderManager.getIndex(owner);

                if (index != null
                    && !classReloaderManager.getClassReloader(index).getOriginClass().hasField(name, desc)) {

                    if (opcode == Opcodes.GETSTATIC) {

                    }
                }

                super.visitFieldInsn(opcode, owner, name, desc);
            }
        };
    }
}
