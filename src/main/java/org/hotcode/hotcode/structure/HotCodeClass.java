package org.hotcode.hotcode.structure;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author hotcode.huangt 13-6-26 PM9:26
 */
public class HotCodeClass {

    private String            className;
    private Set<HotCodeField> fields = new TreeSet<>();

    public Set<HotCodeField> getFields() {
        return fields;
    }

    public void setFields(Set<HotCodeField> fields) {
        this.fields = fields;
    }

    public boolean hasField(String name, String desc) {
        return fields.contains(new HotCodeField(0, name, desc));
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
