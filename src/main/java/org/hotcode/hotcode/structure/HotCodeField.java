package org.hotcode.hotcode.structure;

import org.apache.commons.lang.StringUtils;

/**
 * A field of a class
 * 
 * @author hotcode.huangt 13-6-26 PM9:22
 */
public class HotCodeField {

    private int    access;
    private String name;
    private String desc;

    public HotCodeField(int access, String name, String desc){
        this.access = access;
        this.name = name;
        this.desc = desc;
    }

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof HotCodeField) {
            HotCodeField hotCodeField = (HotCodeField) o;
            return StringUtils.equals(hotCodeField.getName(), name) && StringUtils.equals(hotCodeField.getDesc(), desc);
        } else {
            return false;
        }
    }
}
