package com.karmahostage.tzeentch.core;

public enum FileTypes {
    JPG("jpg", "image/*");

    private String extension;
    private String mediatype;

    FileTypes(String extension, String mediatype) {
        this.extension = extension;
        this.mediatype = mediatype;
    }

    public String getExtension() {
        return extension;
    }

    public String getMediatype() {
        return mediatype;
    }
}
