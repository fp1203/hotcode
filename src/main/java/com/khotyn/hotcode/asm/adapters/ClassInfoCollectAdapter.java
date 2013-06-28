package com.khotyn.hotcode.asm.adapters;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;

import com.khotyn.hotcode.structure.HotCodeClass;
import com.khotyn.hotcode.structure.HotCodeField;

/**
 * Collect the basic info of a class
 * 
 * @author khotyn.huangt 13-6-26 PM9:19
 */
public class ClassInfoCollectAdapter extends ClassVisitor {

    private HotCodeClass hotCodeClass;

    public ClassInfoCollectAdapter(ClassVisitor cv, HotCodeClass hotCodeClass){
        super(Opcodes.ASM4, cv);
        this.hotCodeClass = hotCodeClass;
    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        hotCodeClass.getFields().add(new HotCodeField(access, name, desc));
        return super.visitField(access, name, desc, signature, value);
    }
}
