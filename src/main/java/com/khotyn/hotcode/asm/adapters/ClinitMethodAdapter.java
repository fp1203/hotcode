package com.khotyn.hotcode.asm.adapters;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.khotyn.hotcode.CodeFragment;

/**
 * Method adapter that changes the "<clinit>" method.
 * 
 * @author khotyn.huangt 13-6-24 PM8:55
 */
public class ClinitMethodAdapter extends MethodVisitor {

    private int    access;
    private String classInternalName;

    public ClinitMethodAdapter(int access, String classInternalName, MethodVisitor mv){
        super(Opcodes.ASM4, mv);
        this.access = access;
        this.classInternalName = classInternalName;
    }

    @Override
    public void visitCode() {
        CodeFragment.clinitFieldInit(mv, access, classInternalName);
        super.visitCode();
    }
}
