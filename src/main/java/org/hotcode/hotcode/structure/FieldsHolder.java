package org.hotcode.hotcode.structure;

import java.util.HashMap;
import java.util.Map;

/**
 * A data structure that holds the fields that added to the class.
 * 
 * @author hotcode 2013-06-24 20:22:27
 */
public class FieldsHolder {

    private Map<String, Object> fields = new HashMap<>();

    public Object getField(String fieldKey) {
        return fields.get(fieldKey);
    }

    public void addField(String fieldKey, Object value) {
        fields.put(fieldKey, value);
    }
}
