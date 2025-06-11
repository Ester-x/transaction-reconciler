package com.esb.reconciler;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SftpService {

    private final String host, user, password, remotePath, localPath;
    private final int port;

    public SftpService(Properties config) {
        this.host = config.getProperty("sftp.host");
        this.port = Integer.parseInt(config.getProperty("sftp.port"));
        this.user = config.getProperty("sftp.user");
        this.password = config.getProperty("sftp.password");
        this.remotePath = config.getProperty("sftp.remote.path");
        this.localPath = config.getProperty("sftp.local.path");
    }

    public void downloadCsv() throws Exception {
        JSch jsch = new JSch();
        Session session = jsch.getSession(user, host, port);
        session.setPassword(password);

        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();

        Channel channel = session.openChannel("sftp");
        channel.connect();
        ChannelSftp sftpChannel = (ChannelSftp) channel;

        try (OutputStream output = new FileOutputStream(localPath)) {
            sftpChannel.get(remotePath, output);
        } finally {
            sftpChannel.exit();
            session.disconnect();
        }
    }

    // upload csv file to sftp server,, creating remote directory if needed
    public void uploadCsv(String localFilePath, String remoteFilePath) throws Exception {
        JSch jsch = new JSch();
        Session session = jsch.getSession(user, host, port);
        session.setPassword(password);

        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();

        Channel channel = session.openChannel("sftp");
        channel.connect();
        ChannelSftp sftpChannel = (ChannelSftp) channel;

        try (FileInputStream fis = new FileInputStream(localFilePath)) {
            String remoteDir = remoteFilePath.substring(0, remoteFilePath.lastIndexOf('/'));
            String remoteFileName = remoteFilePath.substring(remoteFilePath.lastIndexOf('/') + 1);

            try {
                sftpChannel.cd(remoteDir);
            } catch (Exception e) {

                // directory does not exist, create it then cd in it
                sftpChannel.mkdir(remoteDir);
                sftpChannel.cd(remoteDir);
            }

            sftpChannel.put(fis, remoteFileName);
        } finally {
            sftpChannel.exit();
            session.disconnect();
        }
    }
}
