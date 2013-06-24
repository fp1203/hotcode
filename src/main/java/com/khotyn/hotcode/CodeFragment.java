package com.khotyn.hotcode;

import java.lang.reflect.Modifier;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import com.khotyn.hotcode.constants.HotCodeConstant;
import com.khotyn.hotcode.structure.FieldsHolder;

/**
 * Code fragment used to add to some place of a class.
 * 
 * @author khotyn.huangt 13-6-24 PM9:32
 */
public class CodeFragment {

    /**
     * Init static fields in method "<clinit>", should insert this code fragment at the start of "<clinit>".
     * 
     * @param mv
     */
    public static void clinitFieldInit(MethodVisitor mv, int access, String ownerClassInternalName) {
        mv.visitTypeInsn(Opcodes.NEW, Type.getInternalName(FieldsHolder.class));
        mv.visitInsn(Opcodes.DUP);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, Type.getInternalName(FieldsHolder.class), "<init>",
                           Type.getMethodDescriptor(Type.VOID_TYPE));
        mv.visitFieldInsn(Opcodes.PUTSTATIC, ownerClassInternalName, HotCodeConstant.HOTCODE_STATIC_FIELDS,
                          Type.getDescriptor(FieldsHolder.class));

        if (!Modifier.isInterface(access)) {
            mv.visitTypeInsn(Opcodes.NEW, Type.getInternalName(FieldsHolder.class));
            mv.visitInsn(Opcodes.DUP);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, Type.getInternalName(FieldsHolder.class), "<init>",
                               Type.getMethodDescriptor(Type.VOID_TYPE));
            mv.visitFieldInsn(Opcodes.PUTSTATIC, ownerClassInternalName, HotCodeConstant.HOTCODE_INSTANCE_FIELDS,
                              Type.getDescriptor(FieldsHolder.class));
        }
    }
}
