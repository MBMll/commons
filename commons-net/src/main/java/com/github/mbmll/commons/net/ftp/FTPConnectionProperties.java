package com.github.mbmll.commons.net.ftp;


import com.github.mbmll.commons.net.ConnectionProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.nio.charset.StandardCharsets;

/**
 * FTP connection properties.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FTPConnectionProperties extends ConnectionProperties {
    private Long dataTimeout;

    @Override
    public Long getConnectTimeout() {
        Long connectTimeout = super.getConnectTimeout();
        return (connectTimeout == null) ? 30_000L : connectTimeout;
    }

    public Long getDataTimeout() {
        return (dataTimeout == null) ? 30_000L : dataTimeout;
    }

    @Override
    public String getEncoding() {
        String encoding = super.getEncoding();
        return (encoding == null) ? StandardCharsets.UTF_8.name() : encoding;
    }
}
