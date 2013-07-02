package org.hotcode.hotcode;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

/**
 * @author khotyn.huangt 13-7-2 PM7:45
 */
public class HotCodeUtil {

    private static final char FIELD_DELIMITER = '-';

    public static String getFieldKey(String name, String desc) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(name) && !Strings.isNullOrEmpty(desc),
                                    "Name and desc can not be null.");
        return FIELD_DELIMITER + name + FIELD_DELIMITER + desc;
    }
}
