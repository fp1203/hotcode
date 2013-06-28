package com.khotyn.hotcode;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

/**
 * A utility class to dump transformed class to file java.
 * 
 * @author khotyn.huangt 13-6-24 PM8:41
 */
public class ClassDumper {

    public static void dump(String className, byte[] classfile) {
        if (!HotCodeConfiguration.ENABLE_CLASS_DUMP) {
            return;
        }

        String dumpPath = HotCodeConfiguration.CLASS_DUMP_PATH;

        if (StringUtils.isBlank(dumpPath)) {
            dumpPath = "/tmp";
        }

        try {
            FileUtils.writeByteArrayToFile(new File(dumpPath + "/" + className + ".class"), classfile);
        } catch (IOException e) {
            e.printStackTrace(); // TODO
        }
    }
}
