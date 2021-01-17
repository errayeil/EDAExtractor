package com.github.errayeil.EDADB.DB.Browse;

import com.github.errayeil.EDADB.DB.Database;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to search for system names within the database.
 *
 * @author Steven Frizell
 * @version HIP 2
 * @since HIP 2
 */
public class DBBrowser {

    /**
     *
     */
    private final Database systemsDatabase;

    /**
     *
     */
    public DBBrowser(String databasePath) {
        systemsDatabase = new Database();
    }

    /**
     *
     * @param name
     * @param maxResults
     * @return
     */
    public List<String> searchForSystems(String name, int maxResults) {
        List<String> results = new ArrayList<>( maxResults );


        return results;
    }
}
