package com.github.errayeil.EDADB.Main;

import com.github.errayeil.EDADB.DB.Extractor.Extractor;
import com.github.errayeil.EDADB.DB.Net.DownloadEvent;
import com.github.errayeil.EDADB.DB.Net.DownloadListener;
import com.github.errayeil.EDADB.DB.Net.Downloader;
import com.opencsv.exceptions.CsvValidationException;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Run {

    /**
     *
     * @param args
     */
    public static void main (String[] args) {
        try {
            UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
        } catch ( ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e ) {
            e.printStackTrace( );
        }

        String systemsCSVURL = "https://eddb.io/archive/v6/systems.csv";
        String updatedSystemsURL = "https://eddb.io/archive/v6/systems_recently.csv";

        String extractedFolderPath = System.getProperty( "user.home" ) + File.separator + "Documents" + File.separator +
                "Extracted";
        String systemsCSVPath = extractedFolderPath + File.separator + "systems.csv";
        String dbPath = extractedFolderPath + File.separator + "database.db";

        File extractedFolder = new File(extractedFolderPath);
        File systemsCSVFile = new File(systemsCSVPath);

        if (!extractedFolder.exists()) {
           boolean dirsMade = extractedFolder.mkdirs();

           if (dirsMade) {
               System.out.println("Directories created.");
           }
        }

        if (!systemsCSVFile.exists()) {
            Downloader downloader = new Downloader(systemsCSVURL, systemsCSVPath);

            downloader.setByteBufferSize( 4096 );

            downloader.addDownloadListener( new DownloadListener( ) {
                float urlContentLength;
                float currentRead;

                @Override
                public void downloadStarted( DownloadEvent event ) {
                    urlContentLength = Long.valueOf( event.getContentValue( ) ).floatValue();
                    System.out.println("Download started.");
                    System.out.println("Content length: " + event.getContentValue());
                }

                @Override
                public void downloadInterrupted( DownloadEvent event ) {

                }

                @Override
                public void downloadFinished( DownloadEvent event ) {
                    System.out.println( "Download finished." );
                }

                @Override
                public void downloadProgress( DownloadEvent event ) {
                    currentRead += Long.valueOf( event.getContentValue() ).floatValue();
                    System.out.println( "Download progress: " + event.getContentValue() );
                }

                @Override
                public void downloadError( DownloadEvent event ) {

                }
            } );

            try {
                downloader.openConnection();
            } catch ( IOException e ) {
                e.printStackTrace( );
            }

            try {
                downloader.startDownload( true );
            } catch ( IOException e ) {
                e.printStackTrace( );
            }
        }

        Extractor extractor = null;
        try {
            extractor = new Extractor( dbPath );
        } catch ( IOException e ) {
            e.printStackTrace( );
        }

        try {
            extractor.extractNames( systemsCSVPath, dbPath, false );
        } catch ( IOException | CsvValidationException e ) {
            e.printStackTrace( );
        }


    }
}
