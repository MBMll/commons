package com.github.mbmll.commons.net.ftp;


import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Semaphore;

public class FTPSource {
    private Semaphore semaphore;

    public FTPSource(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    public FTPConnection createAndConnectFTPClient(FTPConnectionProperties props)
            throws IOException {
        FTPClientWrapper connection = new FTPClientWrapper();
        connection.setControlEncoding(StandardCharsets.UTF_8.name());
        connection.setConnectTimeout(Math.toIntExact(props.getConnectTimeout()));
        connection.setDataTimeout(Math.toIntExact(props.getDataTimeout()));

        connection.connect(props.getHost(), props.getPort());

        int replyCode = connection.getReplyCode();
        if (!FTPReply.isPositiveCompletion(replyCode)) {
            connection.disconnect();
            throw new IOException("Failed to connect to FTP server. Reply code: " + replyCode);
        }

        if (!connection.login(props.getUser(), props.getPassword())) {
            throw new IOException("Failed to login to FTP server");
        }
        return connection;
    }

    /**
     * @param props FTP connection properties
     *
     * @return FTP connection
     *
     * @throws InterruptedException
     * @throws IOException
     */
    public FTPConnection getConnection(FTPConnectionProperties props) throws InterruptedException, IOException {
        semaphore.acquire();
        try {
            return createAndConnectFTPClient(props);
        } finally {
            semaphore.release();
        }
    }

    public class FTPClientWrapper extends FTPConnection {
        @Override
        public void close() throws Exception {
            try {
                super.close();
            } finally {
                semaphore.release();
            }
        }
    }
}
