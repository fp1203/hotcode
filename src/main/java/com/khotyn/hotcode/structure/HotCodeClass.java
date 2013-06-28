package com.khotyn.hotcode.structure;

import java.util.ArrayList;
import java.util.List;

/**
 * @author khotyn.huangt 13-6-26 PM9:26
 */
public class HotCodeClass {

    private String             className;
    private List<HotCodeField> fields = new ArrayList<HotCodeField>();

    public List<HotCodeField> getFields() {
        return fields;
    }

    public void setFields(List<HotCodeField> fields) {
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
