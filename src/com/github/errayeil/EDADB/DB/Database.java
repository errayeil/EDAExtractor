package com.github.errayeil.EDADB.DB;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Steven Frizell
 * @version HIP 2
 * @since HIP 2
 */
public class Database {

    /**
     *
     */
    private DB database;

    /**
     * The queue containing system names.
     */
    private ConcurrentLinkedQueue<String> queue;

    /**
     *
     */
    private boolean queueAllowed = true;

    /**
     *
     */
    private boolean maxSizeReached = false;

    /**
     * boolean determining the queue has content
     * and the push thread should be started.
     */
    private boolean queued = false;

    /**
     * boolean determining the thread should still run.
     */
    private boolean run = false;

    /**
     *
     */
    public Database() {
        queue = new ConcurrentLinkedQueue<String>(  );
    }

    /**
     * Returns if the database is closed. No changes or actions can be
     * made when the database is closed.
     *
     * @return boolean indicating if the database is closed
     */
    public boolean isClosed() {
        return database.isClosed();
    }

    /**
     *
     * @param dbPath
     */
    public void openDatabase(String dbPath) throws IOException {
        File file = new File(dbPath);

        database = DBMaker.fileDB( file )
                .fileMmapEnable()
                .fileMmapPreclearDisable()
                .transactionEnable()
                .make();
    }

    /**
     * Commits any changes or data added to the database.
     */
    public void commitChanges() {
        if (!database.isClosed())
            database.commit();
    }

    /**
     * Closes the database. This does not commit changes.
     */
    public void closeDatabase() {
        if (!database.isClosed( ))
            database.close();
    }

    /**
     *
     * @return
     */
    public boolean canQueue() {
        if (!maxSizeReached) {
            if (queue.size() >= 1000000) {
                maxSizeReached = true;
                queueAllowed = false;
            }
        } else {
            if (queue.size() == 0) {
                maxSizeReached = false;
                queueAllowed = true;
            }
        }

        return queueAllowed;
    }

    /**
     * Queues the system name into the queue for
     * when the push thread is ready to push the name
     * to the database.
     * @param name
     * @return Returns true or false if subsequent queues can occur.
     */
    public void queue( String name) {
        if (!queued) {
            queued = true;
            run = true;
            startPushThread();
        }

        queue.add( name );
    }


    /**
     * Starts the thread that polls the LinkedQueue and pushes the information
     * to the database.
     */
    private void startPushThread() {
        Thread thread = new Thread( () -> {
            while ( run ) {
                //push();
            }
        } );

        thread.start();
    }

    /**
     * This method is ran by the push thread, processing the system
     * name information correctly to store in the database.
     */
    public void push(String name) {

//        String[] commonNamesA = new String[] {"2MASS", "Aaeyoea", "Aemao", "Aemonz", "Aemorrs", "Aemorsch",
//        "Aemost", "Agnaims", "Agnainks", "Anairr", "Agnairs", "Agnairt", "Agnaitts", "Agnaiv", "Agnaiw", "Agnaix",
//        "Agnaiz", "Aiconk", "Aicons", "Aicosp", "Aidoct", "Aidocy", "Aidohs", "Aidoms", "Aidomt", "Aidorn", "Aidorr",
//                "Aidow", "Aidoy", "Ainairk", "Aiphairds"};


        if (name != null) {
            String letter = name.substring( 0, 1 );

            if (letter.equals( "*" )) {
                letter = name.substring( 0, 3 );
            }

            boolean isNumeric = Character.isDigit( letter.charAt( 0 ) );

            if (isNumeric) {
                letter = "#";
            }

            HTreeMap.KeySet<String> set = database.hashSet( letter, Serializer.STRING ).createOrOpen();

            if (!set.contains( name )) {
                set.add( name );
            }
        }
    }

    /**
     *
     * @param letter
     * @param name
     */
    private void addToSet(String letter, String name) {

    }
}
