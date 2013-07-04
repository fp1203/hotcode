package org.hotcode.hotcode.asm.adapters;

import org.hotcode.hotcode.*;
import org.hotcode.hotcode.constants.HotCodeConstant;
import org.hotcode.hotcode.structure.FieldsHolder;
import org.hotcode.hotcode.structure.HotCodeClass;
import org.hotcode.hotcode.structure.HotCodeField;
import org.objectweb.asm.*;
import org.objectweb.asm.commons.GeneratorAdapter;

/**
 * Replace the field access of a class
 * 
 * @author hotcode.huangt 13-6-26 PM9:32
 */
public class FieldTransformAdapter extends ClassVisitor {

    private ClassReloaderManager classReloaderManager;
    private ClassReloader        classReloader;
    private HotCodeClass         originClass;

    public FieldTransformAdapter(ClassVisitor cv, long classReloaderManagerIndex, long classReloaderIndex){
        super(Opcodes.ASM4, cv);
        classReloaderManager = HotCode.getClassReloaderManager(classReloaderManagerIndex);
        classReloader = classReloaderManager.getClassReloader(classReloaderIndex);
        originClass = classReloader.getOriginClass();
    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        return null;
    }

    @Override
    public MethodVisitor visitMethod(final int access, final String name, final String desc, String signature,
                                     String[] exceptions) {
        return new MethodVisitor(Opcodes.ASM4, super.visitMethod(access, name, desc, signature, exceptions)) {

            GeneratorAdapter ga = new GeneratorAdapter(mv, access, name, desc);

            @Override
            public void visitFieldInsn(int opcode, String owner, String name, String desc) {
                Long ownerReloaderIndex = classReloaderManager.getIndex(owner);

                if (ownerReloaderIndex != null && !HotCodeConstant.HOTCODE_ADDED_FIELDS.contains(name)
                    && !classReloaderManager.getClassReloader(ownerReloaderIndex).getOriginClass().hasField(name, desc)) {

                    if (opcode == Opcodes.GETSTATIC) {
                        ga.visitFieldInsn(Opcodes.GETSTATIC, owner, HotCodeConstant.HOTCODE_STATIC_FIELDS,
                                          Type.getDescriptor(FieldsHolder.class));
                        ga.visitLdcInsn(HotCodeUtil.getFieldKey(name, desc));
                        ga.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                                           Type.getDescriptor(FieldsHolder.class),
                                           "getField",
                                           Type.getMethodDescriptor(Type.getType(Object.class),
                                                                    Type.getType(String.class)));
                        ga.unbox(Type.getType(desc));
                    } else if (opcode == Opcodes.PUTSTATIC) {
                        ga.box(Type.getType(desc));
                        ga.visitFieldInsn(Opcodes.GETSTATIC, owner, HotCodeConstant.HOTCODE_STATIC_FIELDS,
                                          Type.getDescriptor(FieldsHolder.class));
                        ga.visitInsn(Opcodes.SWAP);
                        ga.visitLdcInsn(HotCodeUtil.getFieldKey(name, desc));
                        ga.visitInsn(Opcodes.SWAP);
                        ga.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                                           Type.getDescriptor(FieldsHolder.class),
                                           "addField",
                                           Type.getMethodDescriptor(Type.VOID_TYPE, Type.getType(String.class),
                                                                    Type.getType(Object.class)));
                    } else if (opcode == Opcodes.GETFIELD) {
                        CodeFragment.initHotCodeInstanceFieldIfNull(mv, owner);
                        ga.visitVarInsn(Opcodes.ALOAD, 0);
                        ga.visitFieldInsn(Opcodes.GETFIELD, owner, HotCodeConstant.HOTCODE_INSTANCE_FIELDS,
                                          Type.getDescriptor(FieldsHolder.class));
                        ga.visitLdcInsn(HotCodeUtil.getFieldKey(name, desc));
                        ga.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                                           Type.getDescriptor(FieldsHolder.class),
                                           "getField",
                                           Type.getMethodDescriptor(Type.getType(Object.class),
                                                                    Type.getType(String.class)));
                        ga.unbox(Type.getType(desc));
                    } else if (opcode == Opcodes.PUTFIELD) {
                        ga.box(Type.getType(desc));
                        ga.visitInsn(Opcodes.SWAP);
                        CodeFragment.initHotCodeInstanceFieldIfNull(mv, owner);
                        ga.visitVarInsn(Opcodes.ALOAD, 0);
                        ga.visitFieldInsn(Opcodes.GETFIELD, owner, HotCodeConstant.HOTCODE_INSTANCE_FIELDS,
                                          Type.getDescriptor(FieldsHolder.class));
                        ga.visitInsn(Opcodes.SWAP);
                        ga.visitLdcInsn(HotCodeUtil.getFieldKey(name, desc));
                        ga.visitInsn(Opcodes.SWAP);
                        ga.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                                           Type.getDescriptor(FieldsHolder.class),
                                           "addField",
                                           Type.getMethodDescriptor(Type.VOID_TYPE, Type.getType(String.class),
                                                                    Type.getType(Object.class)));
                    }
                } else {
                    super.visitFieldInsn(opcode, owner, name, desc);
                }
            }
        };
    }

    @Override
    public void visitEnd() {
        for (HotCodeField field : originClass.getFields()) {
            cv.visitField(field.getAccess(), field.getName(), field.getDesc(), null, null);
        }

        super.visitEnd();
    }
}
