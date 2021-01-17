package com.github.errayeil.EDADB.DB.Extractor;


public class ExtractorEvent {

    /**
     *
     */
    public enum ExtractorState { STARTED, ENDED, IN_PROGRESS};

    /**
     *
     */
    private ExtractorState currentState;

    /**
     *
     */
    private String processName;

    /**
     * The process value, dependent on the state. If the process is started,
     * this could be the length of a file while downloading, for example.
     */
    private long processValue;

    /**
     *
     */
    protected ExtractorEvent( String processName, ExtractorState state, long value) {
        this.processName = processName;
        this.currentState = state;
        this.processValue = value;
    }

    /**
     * Returns the value of the process.
     * @return
     */
    public long getProcessValue() {
        return processValue;
    }

    /**
     * Returns the name of the process.
     * @return
     */
    public String getProcessName() {
        return processName;
    }

    /**
     * Returns the current state of the process.
     *
     * @return
     */
    public ExtractorState getProcessState() {
        return currentState;
    }
}
