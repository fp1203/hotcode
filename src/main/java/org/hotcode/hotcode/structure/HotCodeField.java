package org.hotcode.hotcode.structure;

import org.apache.commons.lang.StringUtils;
import org.hotcode.hotcode.HotCodeUtil;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

/**
 * A field of a class
 * 
 * @author hotcode.huangt 13-6-26 PM9:22
 */
public class HotCodeField implements java.lang.Comparable {

    private int    access;
    private String name;
    private String desc;

    public HotCodeField(int access, String name, String desc){
        Preconditions.checkArgument(!Strings.isNullOrEmpty(name) && !Strings.isNullOrEmpty(desc),
                                    "Name and description can not be blank.");
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
        return (o instanceof HotCodeField) && StringUtils.equals(this.toString(), o.toString());
    }

    @Override
    public String toString() {
        return HotCodeUtil.getFieldKey(name, desc);
    }

    @Override
    public int compareTo(Object o) {
        return this.toString().compareTo(o.toString());
    }
}
