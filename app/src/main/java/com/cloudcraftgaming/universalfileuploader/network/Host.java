package com.cloudcraftgaming.universalfileuploader.network;

/**
 * Created by Nova Fox on 10/3/17.
 * Website: www.cloudcraftgaming.com
 * For Project: universal-file-uploader
 */

public enum Host {
    NOTHING_DOMAINS("Nothing Domains", "https://nothing.domains/api/upload/pomf", "files[]", 500, HostType.POMF),
    MIX_TAPE("mixtape.moe", "https://mixtape.moe/upload.php", "files[]", 100, HostType.POMF),
    POMF_CAT("pomf.cat", "http://pomf.cat/upload.php", "files[]", 75, HostType.POMF),
    POMFE_CO("pomfe.co", "https://pomfe.co/upload.php", "files[]", 100, HostType.POMF),
    VIDGA_ME("vidga.me", "https://vidga.me/upload.php", "files[]", 100, HostType.POMF);

    private String name;
    private String url;
    private String fieldName;
    private long sizeLimitMb;
    private HostType type;

    Host(String _name, String _url, String _fieldName, long _sizeLimitMb, HostType _type) {
        name = _name;
        url = _url;
        fieldName = _fieldName;
        sizeLimitMb = _sizeLimitMb;
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

    public long getSizeLimitMb() {
        return sizeLimitMb;
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