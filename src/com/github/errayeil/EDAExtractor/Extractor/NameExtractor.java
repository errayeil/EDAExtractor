package com.github.errayeil.EDAExtractor.Extractor;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * Extracts all system names from the eddb systems csv file. The system names are stored in files according to the first
 * letter of the system name.
 *
 * @author Steven Frizell
 * @version HIP 2
 * @since HIP 2
 */
public class NameExtractor {

    /**
     * The class that helps write system names to their respectful file.
     */
    private ExtractorWriter writer;

    /**
     * The URL we obtain the systems.csv file from. We download this information from eddb.io instead of edsm due to
     * download rate. In my tests, eddb tended to be much quicker over EDSM.
     */
    private String systemsURL = "https://eddb.io/archive/v6/systems.csv";

    /**
     * The URL we obtain a csv file with updated systems.
     */
    private String updateSystemsURL = "https://eddb.io/archive/v6/systems_recently.csv";

    /**
     * The letters used to name files.
     */
    private String[] letters = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q"
            , "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "0_9" };

    /**
     * The parent folder.
     */
    private File parentFolder;

    /**
     * Constructs the name extractor and creates the folder and files needed for storing system name information.
     *
     * @param parentDirectory The directory to store the downloaded systems or updatedSystems files and to store the
     *                        names text files. Pass null to have the default created.
     */
    public NameExtractor( String parentDirectory ) {
        if ( parentDirectory == null ) {
            parentFolder = new File( System.getProperty( "user.home" )
                    + File.separator + "Documents" + File.separator + "Extracted" );
        } else {
            parentFolder = new File( parentDirectory + File.separator + "Extracted" );
        }

        writer = new ExtractorWriter(  );


        if ( !parentFolder.exists( ) ) {
            boolean made = parentFolder.mkdir( );

            if ( made ) {
                System.out.println("Creating name files.");
                createNameFiles( );
            }
        }
    }

    /**
     * Starts the extraction process. This includes downloading the systems.csv files if it does not exist.
     */
    public void startExtraction( ) throws IOException, CsvValidationException {
        File systemsCSVFile = new File( parentFolder.getAbsolutePath( )
                + File.separator
                + "systems.csv");

        if ( !systemsCSVFile.exists( ) ) {
            boolean created = systemsCSVFile.createNewFile();

            if (created) {
                try {
                    downloadFile(new URL(systemsURL), systemsCSVFile );
                } catch ( IOException e ) {
                    e.printStackTrace( );
                }
            }
        }

        extractNames( );
    }

    /**
     *
     */
    public void updateNames( ) throws IOException, CsvValidationException {
        File updatedSystemsFile = new File( parentFolder.getAbsolutePath( )
                + File.separator
                + "updatedSystems.csv" );

        if ( !updatedSystemsFile.exists( ) ) {
            System.out.println( "Created updated systems file." );
            updatedSystemsFile.createNewFile( );
            downloadFile( new URL( updateSystemsURL ), updatedSystemsFile );
        }

        writeUpdates( updatedSystemsFile );
    }

    /**
     * Pulls names from the systems.csv file.
     */
    private void extractNames( ) throws IOException, CsvValidationException {
        long startTime = System.nanoTime( );

        String path = parentFolder.getAbsolutePath( ) + File.separator;
        File systems = new File( path + "systems.csv" );
        writer.loadWriters( parentFolder.getAbsolutePath() );


        try ( CSVReader reader = new CSVReader( new FileReader(systems)) ) {
            String[] array;
            writer.startWriting();

            while ( ( array = reader.readNext( ) ) != null ) {
                String name = array[2].toUpperCase();
                writer.queueForWrite( name );
            }
        }

        System.out.println("Closing Writers");
        writer.closeWriters();

        long stopTime = System.nanoTime( );
        long durationMiliseconds = ( stopTime - startTime ) / 1000000;
        long seconds = durationMiliseconds / 1000;
        long minutes = seconds / 60;

        System.out.println( "This took " + minutes + " minutes to complete." );
    }

    /**
     * Downloads the systems.csv file or updatedSystems file from eddb.io.
     */
    private void downloadFile( URL url, File file ) throws IOException {
        if ( !file.exists( ) )
            file.createNewFile( );

        URLConnection connection = url.openConnection( );
        double size = connection.getContentLengthLong();

        try ( BufferedInputStream in = new BufferedInputStream( connection.getInputStream());
              FileOutputStream fout = new FileOutputStream( file ) ) {

            final byte[] data = new byte[ 4096 ];
            int count;
            double read = 0.0;
            while ( ( count = in.read( data, 0, 4000 ) ) != -1 ) {
                fout.write( data, 0, count );
                read += count;
                System.out.println("Percentage complete: " + (read / size) * 100 + "%");
            }
        }

    }

    /**
     *
     * @param updatedSystemsFile
     */
    private void writeUpdates(File updatedSystemsFile) throws IOException, CsvValidationException {
        try ( CSVReader reader = new CSVReader( new FileReader( updatedSystemsFile ) ) ) {
            String[] array;

            while ( ( array = reader.readNext( ) ) != null ) {
                String name = array[ 2 ].toUpperCase( );
                System.out.println("Got name: " + name);
                File file = getFileForLetter( name.substring( 0, 1 ) );

                try ( BufferedReader buffered = new BufferedReader( new FileReader( file ) ) ) {
                    String line;
                    boolean broke = false;

                    while ( ( line = buffered.readLine( ) ) != null ) {
                        if ( line.equals( name ) ) {
                            System.out.println("Comparing.");
                            line = null;
                            broke = true;
                        }
                    }

                    buffered.close( );

                    if ( !broke ) {
                        try ( BufferedWriter write = new BufferedWriter( new FileWriter( file, true ) ) ) {
                            System.out.println( "Writing name: " + name + " to file: " + file.getName( ) );
                            write.write( name );
                        }
                    }
                }
            }
        }
    }

    /**
     * Creates the name files for alphabetical and numerical storage.
     */
    private void createNameFiles( ) {
        String path = parentFolder.getAbsolutePath( ) + File.separator;

        for ( String letter : letters ) {
            File f = new File( path + "names" + letter + ".txt" );

            if ( !f.exists( ) ) {
                try {
                    f.createNewFile( );
                } catch ( IOException e ) {
                    e.printStackTrace( );
                }
            }
        }
    }

    /**
     * @param letter
     *
     * @return
     */
    private File getFileForLetter( String letter ) {
        boolean isNumeric = letter.chars( ).allMatch( Character::isDigit );

        if ( isNumeric ) {
            return new File( parentFolder.getAbsolutePath( ) + File.separator + "names" + "0_9.txt" );
        } else {
            return new File( parentFolder.getAbsolutePath( ) + File.separator + "names" + letter + ".txt" );
        }
    }
}
