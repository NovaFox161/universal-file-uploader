package com.cloudcraftgaming.universalfileuploader.network;

/**
 * Created by Nova Fox on 10/3/17.
 * Website: www.cloudcraftgaming.com
 * For Project: universal-file-uploader
 */

public enum Host {
    NOTHING_DOMAINS("Nothing Domains", "https://nothing.domains/api/upload/pomf", "files[]", HostType.POMF);

    private String name;
    private String url;
    private String fieldName;
    private HostType type;

    Host(String _name, String _url, String _fieldName, HostType _type) {
        name = _name;
        url = _url;
        fieldName = _fieldName;
        type = _type;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getFieldName() {
        return fieldName;
    }

    public HostType getType() {
        return type;
    }

    public static Host fromName(String value) {
        for (Host h : Host.values()) {
            if (h.getName().equalsIgnoreCase(value)) {
                return h;
            }
        }
        return null;
    }
}