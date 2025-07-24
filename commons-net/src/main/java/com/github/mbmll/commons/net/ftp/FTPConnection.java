package com.github.mbmll.commons.net.ftp;


import org.apache.commons.net.ftp.FTPClient;

public class FTPConnection extends FTPClient implements AutoCloseable {

    @Override
    public void close() throws Exception {
        try {
            logout();
        } finally {
            if (isConnected()) {
                disconnect();
            }
        }
    }
}
