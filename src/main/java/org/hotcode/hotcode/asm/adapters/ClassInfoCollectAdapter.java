package org.hotcode.hotcode.asm.adapters;

import org.hotcode.hotcode.structure.HotCodeClass;
import org.hotcode.hotcode.structure.HotCodeField;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Collect the basic info of a class
 * 
 * @author hotcode.huangt 13-6-26 PM9:19
 */
public class ClassInfoCollectAdapter extends ClassVisitor {

    private HotCodeClass hotCodeClass;

    public ClassInfoCollectAdapter(ClassVisitor cv, HotCodeClass hotCodeClass){
        super(Opcodes.ASM4, cv);
        this.hotCodeClass = hotCodeClass;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        hotCodeClass.setClassName(name.replace('/', '.'));
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        hotCodeClass.getFields().add(new HotCodeField(access, name, desc));
        return super.visitField(access, name, desc, signature, value);
    }
}
