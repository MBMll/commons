package com.github.mbmll.commons.net;

import lombok.Data;

import java.io.Serializable;

/**
 * Common connection properties for FTP and other network connections.
 */
@Data
public class ConnectionProperties implements Serializable {

    private static final long serialVersionUID = 1L;

    private String host;
    private Integer port;
    private String user;
    private String password;

    private String encoding;
    private Long connectTimeout;
}
