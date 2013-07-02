package org.hotcode.hotcode;

import java.lang.reflect.Modifier;

import org.hotcode.hotcode.structure.FieldsHolder;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;

import org.hotcode.hotcode.constants.HotCodeConstant;

/**
 * Code fragment used to add to some place of a class.
 * 
 * @author hotcode.huangt 13-6-24 PM9:32
 */
public class CodeFragment {

    /**
     * Init static fields in method "<clinit>", should insert this code fragment at the start of "<clinit>".
     * 
     * @param mv
     */
    public static void clinitFieldInit(MethodVisitor mv, int access, String ownerClassInternalName,
                                       Long classReloaderManagerIndex, Long classReloaderIndex) {
        mv.visitTypeInsn(Opcodes.NEW, Type.getInternalName(FieldsHolder.class));
        mv.visitInsn(Opcodes.DUP);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, Type.getInternalName(FieldsHolder.class), "<init>",
                           Type.getMethodDescriptor(Type.VOID_TYPE));
        mv.visitFieldInsn(Opcodes.PUTSTATIC, ownerClassInternalName, HotCodeConstant.HOTCODE_STATIC_FIELDS,
                          Type.getDescriptor(FieldsHolder.class));

        mv.visitLdcInsn(classReloaderManagerIndex);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, Type.getInternalName(HotCode.class), "getClassReloaderManager",
                           Type.getMethodDescriptor(Type.getType(ClassReloaderManager.class), Type.LONG_TYPE));
        mv.visitLdcInsn(classReloaderIndex);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, Type.getInternalName(ClassReloaderManager.class), "getClassReloader",
                           Type.getMethodDescriptor(Type.getType(ClassReloader.class), Type.LONG_TYPE));
        mv.visitFieldInsn(Opcodes.PUTSTATIC, ownerClassInternalName, HotCodeConstant.HOTCODE_CLASS_RELOADER_FIELDS,
                          Type.getDescriptor(ClassReloader.class));

        if (!Modifier.isInterface(access)) {
            mv.visitTypeInsn(Opcodes.NEW, Type.getInternalName(FieldsHolder.class));
            mv.visitInsn(Opcodes.DUP);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, Type.getInternalName(FieldsHolder.class), "<init>",
                               Type.getMethodDescriptor(Type.VOID_TYPE));
            mv.visitFieldInsn(Opcodes.PUTSTATIC, ownerClassInternalName, HotCodeConstant.HOTCODE_INSTANCE_FIELDS,
                              Type.getDescriptor(FieldsHolder.class));
        }
    }

    /**
     * Insert this code fragment at the start of a method, so it can check the update of a class file before every
     * method invoke.
     * 
     * @param ga
     * @param methodAccess
     * @param methodName
     * @param methodDesc
     * @param ownerClassInternalName
     */
    public static void beforeMethodCheck(MethodVisitor mv, int methodAccess, String methodName, String methodDesc,
                                         String ownerClassInternalName) {
        GeneratorAdapter ga = new GeneratorAdapter(mv, methodAccess, methodName, methodDesc);
        Label label = new Label();
        ga.visitFieldInsn(Opcodes.GETSTATIC, ownerClassInternalName, HotCodeConstant.HOTCODE_CLASS_RELOADER_FIELDS,
                          Type.getDescriptor(ClassReloader.class));
        ga.visitMethodInsn(Opcodes.INVOKEVIRTUAL, Type.getInternalName(ClassReloader.class), "checkAndReload",
                           Type.getMethodDescriptor(Type.BOOLEAN_TYPE));
        ga.visitInsn(Opcodes.ICONST_1);
        ga.visitJumpInsn(Opcodes.IF_ICMPNE, label);

        if (!Modifier.isStatic(methodAccess)) {
            ga.loadThis();
        }

        ga.loadArgs();
        ga.visitMethodInsn(Modifier.isStatic(methodAccess) ? Opcodes.INVOKESTATIC : Opcodes.INVOKESPECIAL,
                           ownerClassInternalName, methodName, methodDesc);
        ga.returnValue();
        ga.visitLabel(label);
    }
}
