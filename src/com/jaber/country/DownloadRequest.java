package com.jaber.country;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class DownloadRequest {

    private int downloadedSize;
    private int totalSize;
    private int percent;


    public int getDownloadedSize() {
        return downloadedSize;
    }


    public int getTotalSize() {
        return totalSize;
    }


    public int getPercent() {
        return percent;
    }


    public DownloadRequest download() {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    URL url = new URL(downloadPath);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setDoOutput(true);
                    connection.connect();

                    totalSize = connection.getContentLength();

                    File file = new File(filepath);
                    if (file.exists()) {
                        file.delete();
                    }

                    FileOutputStream outputStream = new FileOutputStream(filepath);

                    InputStream inputStream = connection.getInputStream();
                    byte[] buffer = new byte[G.DOWNLOAD_BUFFER_SIZE];
                    int len = 0;
                    while ((len = inputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, len);
                        downloadedSize += len;

                        percent = (int) (100.0f * (float) downloadedSize / totalSize);
                        if (listener != null) {

                            final int finalDownloadedSize = downloadedSize;
                            G.HANDLER.post(new Runnable() {

                                @Override
                                public void run() {
                                    listener.onProgressDownload(percent, finalDownloadedSize, totalSize);
                                }
                            });
                        }

                        if (simulate) {
                            try {
                                Thread.sleep(30);
                            }
                            catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    outputStream.close();
                }
                catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

        return this;
    }

    private String                     downloadPath;
    private String                     filepath;
    private OnProgressDownloadListener listener;
    private boolean                    simulate;


    public DownloadRequest downloadPath(String value) {
        downloadPath = value;
        return this;
    }


    public DownloadRequest filepath(String value) {
        filepath = value;
        return this;
    }


    public DownloadRequest listener(OnProgressDownloadListener value) {
        listener = value;
        return this;
    }


    public DownloadRequest simulate(boolean value) {
        simulate = value;
        return this;
    }
}
