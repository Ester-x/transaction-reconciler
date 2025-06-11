package com.esb.reconciler;

import java.io.InputStream;
import java.util.Properties;

public class CsvUploader {

    public static void main(String[] args) {
        try {
            Properties config = new Properties();
            try (InputStream input = CsvUploader.class.getClassLoader().getResourceAsStream("application.properties")) {
                if (input == null) {
                    System.out.println("Sorry, unable to find application.properties");
                    return;
                }
                config.load(input);
            }

            String localCsvPath = "report.csv";   // localpath to local CSV file
            String remoteCsvPath = "/upload/report.csv"; // remotepath on SFTP server

            new SftpService(config).uploadCsv(localCsvPath, remoteCsvPath);

            System.out.println("Upload complete.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
