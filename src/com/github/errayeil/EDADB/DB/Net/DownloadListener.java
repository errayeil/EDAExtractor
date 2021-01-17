package com.github.errayeil.EDADB.DB.Net;

/**
 * A listener for determining what is happening with a download.
 *
 * @author Steven Frizell
 * @version HIP 2
 * @since HIP 2
 */
public interface DownloadListener {

    /**
     *
     */
    void downloadStarted(DownloadEvent event);

    /**
     *
     * @param event
     */
    void downloadInterrupted(DownloadEvent event);

    /**
     *
     */
    void downloadFinished(DownloadEvent event);

    /**
     *
     */
    void downloadProgress(DownloadEvent event);

    /**
     *
     */
    void downloadError(DownloadEvent event);
}
