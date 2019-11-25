package com.it.javabean;

public class FileInfo {
    private String originalName;
    private String uuidName;

    public FileInfo(String originalName, String uuidName) {
        this.originalName = originalName;
        this.uuidName = uuidName;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getUuidName() {
        return uuidName;
    }

    public void setUuidName(String uuidName) {
        this.uuidName = uuidName;
    }
}
