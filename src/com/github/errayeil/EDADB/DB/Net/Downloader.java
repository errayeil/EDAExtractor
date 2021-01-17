package com.github.errayeil.EDADB.DB.Net;

import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarBuilder;
import me.tongfei.progressbar.ProgressBarStyle;

import javax.print.attribute.standard.DocumentName;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Steven Frizell
 * @version HIP 2
 * @since HIP
 */
public class Downloader {

    /**
     *
     */
    private String contentUrl;

    /**
     *
     */
    private String destinationPath;

    /**
     *
     */
    private URLConnection connection;

    /**
     *
     */
    private int bufferSize = 4096;

    /**
     *
     */
    private long contentLength;

    /**
     *
     */
    private List<DownloadListener> listeners = new ArrayList<>();

    /**
     *
     */
    private boolean stopDownload;

    /**
     * @param url The url we will be obtaining the download content from.
     * @param destinationPath The destination of the downloaded content.
     */
    public Downloader(String url, String destinationPath) {
        this.contentUrl = url;
        this.destinationPath = destinationPath;
    }

    /**
     *
     * @param listener
     */
    public void addDownloadListener(DownloadListener listener) {
        if (!listeners.contains( listener ))
            listeners.add( listener );
    }

    /**
     *
     * @param listener
     */
    public void removeDownloadListener(DownloadListener listener) {
        listeners.remove( listener );
    }

    /**
     *
     * @param bufferSize
     */
    public void setByteBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    /**
     * Opens the connection of the url.
     * @throws IOException
     */
    public void openConnection() throws IOException {
        URL link = new URL( contentUrl );

        connection = link.openConnection();
        contentLength = connection.getContentLengthLong();
    }

    /**
     * Returns the length of the content.
     * @return
     */
    public long getContentLength() {
        return contentLength;
    }

    /**
     *
     * @param createFile If true, if the file doesn't exist it will be created.
     */
    public void startDownload(boolean createFile) throws IOException {
       File file = new File(destinationPath);

        if (!file.exists()) {
            if (createFile) {
                boolean created = file.createNewFile();

                if (!created) {
                    throw new IOException( "File: " + file.getAbsolutePath() + " could not be created." );
                }
            } else {
                throw new IOException( "File: " + file.getAbsolutePath() + " does not exist." );
            }
        }

        final InputStream in = connection.getInputStream();

        ProgressBarBuilder builder = new ProgressBarBuilder()
                .setInitialMax( contentLength )
                .setStyle( ProgressBarStyle.COLORFUL_UNICODE_BLOCK )
                .setUpdateIntervalMillis( 100 )
                .setTaskName( "Downloading systems.csv" );

        ProgressBar bar = builder.build();

        try (BufferedInputStream bufferedIn = new BufferedInputStream(in);
             FileOutputStream fileOut = new FileOutputStream(file)) {

            final byte[] buffer = new byte[ bufferSize ];
            int count;
            long read = 0;
            while ( ( count = bufferedIn.read( buffer, 0, bufferSize ) ) != -1 ) {
                fileOut.write( buffer, 0, count );
                read += count;
                bar.stepTo( read );

                if (stopDownload) {
                    System.out.println("Stopping download.");
                    break;
                }
            }

            if (stopDownload) {
                boolean deleted = file.delete();

                if (deleted) {
                    //TODO
                    System.out.println( "File deleted." );
                }
            } else {

            }
        }
    }

    /**
     *
     */
    public void stopDownload() {
        stopDownload = true;
    }
}
