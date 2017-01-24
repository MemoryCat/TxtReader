package com.memorycat.app.txtreader.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by xie on 2017/1/24.
 */

public class FileUtil {

    public static String loadFileToString(File file) {
        StringBuffer ret = new StringBuffer();
        if (file.exists() && file.length() > 0) {
            try {

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), testFileCharset(file)));
                for (String s = null; (s = bufferedReader.readLine()) != null; ret.append(s + "\n"))
                    ;
                bufferedReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ret.toString();
    }

    /**
     * 测试文件编码
     *
     * @param file
     * @return
     */
    private static String testFileCharset(File file) {
        String charset = "gbk";
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bytes = new byte[2];
            if (fileInputStream.read(bytes) == 2) {
                if (bytes[0] == 0xef && bytes[1] == 0xbb) {
                    charset = "UTF-8";
                } else if (bytes[0] == 0xff && bytes[1] == 0xfe) {
                    charset = "Unicode";
                } else if (bytes[0] == 0xfe && bytes[1] == 0xff) {
                    charset = "UTF-16BE";
                }
            }
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return charset;
    }

}
