package com.jaber.country;

public interface OnProgressDownloadListener {

    public void onProgressDownload(int percent, int downloadedSize, int fileSize);
}