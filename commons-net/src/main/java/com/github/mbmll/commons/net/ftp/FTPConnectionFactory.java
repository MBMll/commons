package com.github.mbmll.commons.net.ftp;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Semaphore;

public class FTPConnectionFactory {
    private final ConcurrentMap<String, FTPSource> pool = new ConcurrentHashMap<>();

    /**
     * 获取FTP连接
     *
     * @param props 连接属性
     * @param size  连接池大小
     *
     * @return FTP连接
     *
     * @throws IOException          获取连接失败
     * @throws InterruptedException 获取连接失败
     */
    public FTPConnection getConnection(FTPConnectionProperties props, int size)
            throws IOException, InterruptedException {
        String server = props.getHost() + ":" + props.getPort();
        FTPSource ftpSource = pool.computeIfAbsent(server, k -> new FTPSource(new Semaphore(size)));
        return ftpSource.getConnection(props);
    }
}
