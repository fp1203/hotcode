package com.khotyn.hotcode.structure;

/**
 * Method of a class.
 * 
 * @author khotyn.huangt 13-6-25 PM3:16
 */
public class HotCodeMethod {

    private int      access;
    private String   name;
    private String   desc;
    private String   signature;
    private String[] exceptions;

    public HotCodeMethod(int access, String name, String desc, String signature, String[] exceptions){
        this.access = access;
        this.name = name;
        this.desc = desc;
        this.signature = signature;
        this.exceptions = exceptions;
    }

    public int getAccess() {
        return access;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getSignature() {
        return signature;
    }

    public String[] getExceptions() {
        return exceptions;
    }
}
