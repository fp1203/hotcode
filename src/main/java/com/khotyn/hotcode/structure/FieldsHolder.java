package com.khotyn.hotcode.structure;

import java.util.HashMap;
import java.util.Map;

/**
 * A data structure that holds the fields that added to the class.
 * 
 * @author khotyn 2013-06-24 20:22:27
 */
public class FieldsHolder {
    private Map<String, Object> fields = new HashMap<String, Object>();

    public Map<String, Object> getFields() {
        return fields;
    }

    public void setFields(Map<String, Object> fields) {
        this.fields = fields;
    }
}
