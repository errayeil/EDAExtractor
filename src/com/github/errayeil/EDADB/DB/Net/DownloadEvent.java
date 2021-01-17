package com.github.errayeil.EDADB.DB.Net;

/**
 *
 * @author Steven Frizell
 * @version HIP 2
 * @since HIP 2
 */
public class DownloadEvent {

    /**
     * The current value of the processed content.
     * This could be the content length if the download has started,
     * the current downloaded value, or the value if interrupted,
     * or finished.
     */
    private long contentValue;

    /**
     * The time of this event.
     */
    private long nanoTime;

    /**
     *
     */
    public DownloadEvent(final long contentValue) {
        this.contentValue = contentValue;
        nanoTime = System.nanoTime();
    }

    /**
     *
     * @return
     */
    public final long getContentValue() {
        return contentValue;
    }

    /**
     *
     * @return
     */
    public final long getNanoTime() {
        return nanoTime;
    }
}
