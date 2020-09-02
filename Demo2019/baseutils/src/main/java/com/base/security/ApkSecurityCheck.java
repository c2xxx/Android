package com.base.security;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

public class ApkSecurityCheck {


    public String apkSha1(Context context) {

        String apkPath = context.getPackageCodePath();

        MessageDigest msgDigest = null;

        try {

            msgDigest = MessageDigest.getInstance("SHA-1");

            byte[] bytes = new byte[1024];

            int byteCount;

            FileInputStream fis = new FileInputStream(new File(apkPath));

            while ((byteCount = fis.read(bytes)) > 0) {

                msgDigest.update(bytes, 0, byteCount);

            }

            BigInteger bi = new BigInteger(1, msgDigest.digest());

            String sha = bi.toString(16);

            fis.close();

            //这里添加从服务器中获取哈希值然后进行对比校验
            return sha;

        } catch (Exception e) {

            e.printStackTrace();

        }


        return null;
    }

    /**
     * 判断手机是否ROOT
     */
    public boolean isSystemRoot() {

        boolean root = false;

        try {
            if ((!new File("/system/bin/su").exists())
                    && (!new File("/system/xbin/su").exists())) {
                root = false;
            } else {
                root = true;
            }

        } catch (Exception e) {
        }

        return root;
    }
}
