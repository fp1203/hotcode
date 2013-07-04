package org.hotcode.hotcode.asm.adapters;

import java.util.Set;

import org.hotcode.hotcode.structure.HotCodeClass;
import org.hotcode.hotcode.structure.HotCodeField;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author khotyn.huangt 13-7-4 PM2:57
 */
public class FieldReorderAdapter extends ClassVisitor {

    private HotCodeClass hotCodeClass;

    public FieldReorderAdapter(HotCodeClass hotCodeClass, ClassVisitor cv){
        super(Opcodes.ASM4, cv);
        this.hotCodeClass = hotCodeClass;
    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        return null;
    }

    @Override
    public void visitEnd() {
        Set<HotCodeField> fields = hotCodeClass.getFields();

        for (HotCodeField field : fields) {
            cv.visitField(field.getAccess(), field.getName(), field.getDesc(), null, null);
        }

        super.visitEnd();
    }
}
