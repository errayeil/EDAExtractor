package com.github.errayeil.EDAExtractor.Extractor;

/**
 * @author Steven Frizell
 * @version HIP 2
 * @since HIP 2
 */
public interface ExtractorListener {

    /**
     *
     * @param event
     */
    void extractorStarted( ExtractorEvent event);

    /**
     *
     * @param event
     */
    void extractorFinished( ExtractorEvent event);

    /**
     *
     * @param event
     */
    void extractorProgressed( ExtractorEvent event);
}
