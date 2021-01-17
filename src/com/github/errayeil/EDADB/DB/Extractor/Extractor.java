package com.github.errayeil.EDADB.DB.Extractor;

import com.github.errayeil.EDADB.DB.Database;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import me.tongfei.progressbar.DefaultProgressBarRenderer;
import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarBuilder;
import me.tongfei.progressbar.ProgressBarConsumer;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author Steven Frizell
 * @version HIP 2
 * @since HIP 2
 */
public class Extractor {


    /**
     * @param databasePath The path to the system names database.
     */
    public Extractor( String databasePath ) throws IOException {
        File file = new File( databasePath );

        if ( !file.exists( ) ) {
//            boolean created = file.createNewFile();
//
//            if (!created) {
//                throw new IOException( "The database file could not be created." );
//            }
        }

    }

    /**
     * Extracts the names from the source file in the database.
     *
     * @param sourcePath   The path of the source file to read from. Must be a csv file.
     * @param deleteSource If the source file should be deleted or not.
     */
    public void extractNames( String sourcePath, String dbPath, boolean deleteSource ) throws IOException, CsvValidationException {
        long startNanoTime = System.nanoTime( );
        Database systemNameDatabase = new Database( );
        systemNameDatabase.openDatabase( dbPath );

        if ( systemNameDatabase.isClosed( ) ) {
            throw new IOException( "The system name database is not open." );
        }

        File systemsCSVFile = new File( sourcePath );

        if ( !systemsCSVFile.exists( ) || !systemsCSVFile.canRead( ) ) {
            throw new IOException( "The file cannot be read or does not exist." );
        }

        long entriesRead = 0;
        ProgressBarBuilder builder = new ProgressBarBuilder( )
                .setTaskName( "Reading" )
                .setInitialMax( 1900000 ) //Based off current discovered systems
                .setUpdateIntervalMillis( 1 );


        ProgressBar bar = builder.build( );

        try ( CSVReader reader = new CSVReader( new FileReader( systemsCSVFile ) ) ) {
            String[] currentArray;

            while ( ( currentArray = reader.readNext( ) ) != null ) {
                systemNameDatabase.push( currentArray[2] );
                bar.stepTo( entriesRead++ );
            }

            systemNameDatabase.commitChanges( );
            systemNameDatabase.closeDatabase( );

            if ( deleteSource ) {
                boolean deleted = systemsCSVFile.delete( );

                if ( deleted ) {
                    System.out.println( "The source file has been deleted." );
                }
            }

            System.out.println( "Entries read" + entriesRead );

            long endNanoTime = System.nanoTime( );
            long milliSecond = ( endNanoTime - startNanoTime ) / 1000000;
            long minutes = TimeUnit.MINUTES.toMinutes( milliSecond );

            System.out.println( "This took: " + minutes + " minutes to complete." );
        }
    }
}
