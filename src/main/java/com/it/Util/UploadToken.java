package com.it.Util;

import com.qiniu.util.Auth;

public class UploadToken {
    public static String getToken(){
        String accessKey = "y1hAzyOX_DbjnVXz-rRXX2kp-7hGNZh_fODnsn6p";
        String secretKey = "2R6t9EE9dG_XPaS7rcQle-hBxTRGgJ3NqKDJkYJt";
        String bucket = "threr2";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        return upToken;
    }
}
