package org.hotcode.hotcode.asm.adapters;

import org.apache.commons.lang.StringUtils;
import org.hotcode.hotcode.CodeFragment;
import org.hotcode.hotcode.constants.HotCodeConstant;
import org.hotcode.hotcode.structure.HotCodeMethod;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * Add "<clinit>" to class if not exist, or else transform the "<clinit>" methods. Add all the code in "<clinit>" to
 * {@link HotCodeConstant#HOTCODE_CLINIT_METHOD_NAME} to class reinitialization.
 * 
 * @author hotcode.huangt 13-6-24 PM10:13
 */
public class ClinitClassAdapter extends ClassVisitor {

    private HotCodeMethod clinitMethod;
    private String        classInternalName;
    private Long          classReloaderManagerIndex;
    private Long          classReloaderIndex;

    public ClinitClassAdapter(ClassVisitor cv, Long classReloaderManagerIndex, Long classReloaderIndex){
        super(Opcodes.ASM4, cv);
        this.classReloaderManagerIndex = classReloaderManagerIndex;
        this.classReloaderIndex = classReloaderIndex;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.classInternalName = name;
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        if (StringUtils.equals(name, "<clinit>")) {
            clinitMethod = new HotCodeMethod(access, name, desc, signature, exceptions);
            MethodVisitor mv = super.visitMethod(access | Opcodes.ACC_PUBLIC,
                                                 HotCodeConstant.HOTCODE_CLINIT_METHOD_NAME, desc, signature,
                                                 exceptions);
            return new MethodVisitor(Opcodes.ASM4, mv) {

                @Override
                public void visitCode() {
                    CodeFragment.clinitFieldInit(mv, classInternalName, classReloaderManagerIndex, classReloaderIndex);
                    super.visitCode();
                }
            };
        }

        return super.visitMethod(access, name, desc, signature, exceptions);
    }

    @Override
    public void visitEnd() {
        if (clinitMethod != null) {
            MethodVisitor mv = cv.visitMethod(clinitMethod.getAccess() | Opcodes.ACC_PUBLIC, clinitMethod.getName(),
                                              clinitMethod.getDesc(), clinitMethod.getSignature(),
                                              clinitMethod.getExceptions());

            mv.visitMethodInsn(Opcodes.INVOKESTATIC, classInternalName, HotCodeConstant.HOTCODE_CLINIT_METHOD_NAME,
                               clinitMethod.getDesc());
            mv.visitMaxs(0, 0);
            mv.visitInsn(Opcodes.RETURN);
            mv.visitEnd();
        } else {
            MethodVisitor mv = cv.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC,
                                              HotCodeConstant.HOTCODE_CLINIT_METHOD_NAME,
                                              Type.getMethodDescriptor(Type.VOID_TYPE), null, null);
            mv.visitCode();
            CodeFragment.clinitFieldInit(mv, classInternalName, classReloaderManagerIndex, classReloaderIndex);
            mv.visitInsn(Opcodes.RETURN);
            mv.visitMaxs(0, 0);
            mv.visitEnd();

            mv = cv.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "<clinit>",
                                Type.getMethodDescriptor(Type.VOID_TYPE), null, null);
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, classInternalName, HotCodeConstant.HOTCODE_CLINIT_METHOD_NAME,
                               Type.getMethodDescriptor(Type.VOID_TYPE));
            mv.visitInsn(Opcodes.RETURN);
            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }

        super.visitEnd();
    }
}
