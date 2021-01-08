package com.github.errayeil.EDAExtractor.Extractor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Steven Frizell
 * @version HIP 2
 * @since HIP 2
 */
public class ExtractorWriter {

    /**
     *
     */
    private BufferedWriter aWriter;
    private BufferedWriter bWriter;
    private BufferedWriter cWriter;
    private BufferedWriter dWriter;
    private BufferedWriter eWriter;
    private BufferedWriter fWriter;
    private BufferedWriter gWriter;
    private BufferedWriter hWriter;
    private BufferedWriter iWriter;
    private BufferedWriter jWriter;
    private BufferedWriter kWriter;
    private BufferedWriter lWriter;
    private BufferedWriter mWriter;
    private BufferedWriter nWriter;
    private BufferedWriter oWriter;
    private BufferedWriter pWriter;
    private BufferedWriter qWriter;
    private BufferedWriter rWriter;
    private BufferedWriter sWriter;
    private BufferedWriter tWriter;
    private BufferedWriter uWriter;
    private BufferedWriter vWriter;
    private BufferedWriter wWriter;
    private BufferedWriter xWriter;
    private BufferedWriter yWriter;
    private BufferedWriter zWriter;
    private BufferedWriter numericWriter;

    /**
     * Boolean determining if the the BufferedWriters are
     * loaded.
     */
    private boolean loaded = false;

    /**
     * A queue of system names needing to be written.
     */
    private ConcurrentLinkedQueue< String > queue;

    /**
     * Constructor.
     */
    public ExtractorWriter( ) {
        queue = new ConcurrentLinkedQueue<>(  );
    }

    /**
     * Adds the specified system name to the queue.
     *
     * @param name The name to add.
     */
    public void queueForWrite( String name ) {
        queue.add( name );

    }

    /**
     * Creates and executes a thread to write write queue system names
     * to their respective file.
     */
    public void startWriting( ) {
        Runnable run = ( ) -> {
            while (loaded) {
                String name = queue.poll();

                if (name != null) {
                    name = name.toUpperCase();
                    String letter = name.substring( 0, 1 );
                    boolean isNumeric = letter.chars( ).allMatch( Character::isDigit );

                    if(isNumeric) {
                        try {
                            System.out.println("Writing numeric: " + name);
                            numericWriter.write( name );
                        } catch ( IOException e ) {
                            e.printStackTrace( );
                        }
                    } else {
                        try {
                            System.out.println( "Writing name: " + name );
                            switch ( letter ) {
                                case "A":
                                    aWriter.write( name );
                                    aWriter.newLine();
                                    break;
                                case "B":
                                    bWriter.write( name );
                                    bWriter.newLine();
                                    break;
                                case "C":
                                    cWriter.write( name );
                                    cWriter.newLine();
                                    break;
                                case "D":
                                    dWriter.write( name );
                                    dWriter.newLine();
                                    break;
                                case "E":
                                    eWriter.write( name );
                                    eWriter.newLine();
                                    break;
                                case "F":
                                    fWriter.write( name );
                                    fWriter.newLine();
                                    break;
                                case "G":
                                    gWriter.write( name );
                                    gWriter.newLine();
                                    break;
                                case "H":
                                    hWriter.write( name );
                                    hWriter.newLine();
                                    break;
                                case "I":
                                    iWriter.write( name );
                                    iWriter.newLine();
                                    break;
                                case "J":
                                    jWriter.write( name );
                                    jWriter.newLine();
                                    break;
                                case "K":
                                    kWriter.write( name );
                                    kWriter.newLine();
                                    break;
                                case "L":
                                    lWriter.write( name );
                                    lWriter.newLine();
                                    break;
                                case "M":
                                    mWriter.write( name );
                                    mWriter.newLine();
                                    break;
                                case "N":
                                    nWriter.write( name );
                                    nWriter.newLine();
                                    break;
                                case "O":
                                    oWriter.write( name );
                                    oWriter.newLine();
                                    break;
                                case "P":
                                    pWriter.write( name );
                                    pWriter.newLine();
                                    break;
                                case "Q":
                                    qWriter.write( name );
                                    qWriter.newLine();
                                    break;
                                case "R":
                                    rWriter.write( name );
                                    rWriter.newLine();
                                    break;
                                case "S":
                                    sWriter.write( name );
                                    sWriter.newLine();
                                    break;
                                case "T":
                                    tWriter.write( name );
                                    tWriter.newLine();
                                    break;
                                case "U":
                                    uWriter.write( name );
                                    uWriter.newLine();
                                    break;
                                case "V":
                                    vWriter.write( name );
                                    vWriter.newLine();
                                    break;
                                case "W":
                                    wWriter.write( name );
                                    wWriter.newLine();
                                    break;
                                case "X":
                                    xWriter.write( name );
                                    xWriter.newLine();
                                    break;
                                case "Y":
                                    yWriter.write( name );
                                    yWriter.newLine();
                                    break;
                                case "Z":
                                    zWriter.write( name );
                                    zWriter.newLine();
                                    break;
                                default: System.out.println("Does not match: " + name);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        Thread queueThread = new Thread(run);

        queueThread.setName( "csvWritingThread" );
        queueThread.start();
    }

    /**
     * Loads all BufferedWriters to write system name to their files.
     * @param path The parent directory path the name files are located.
     */
    public void loadWriters( String path ) throws IOException {
        aWriter = new BufferedWriter( new FileWriter( path + File.separator + "namesA.txt", true ) );
        bWriter = new BufferedWriter( new FileWriter( path + File.separator + "namesB.txt", true ) );
        cWriter = new BufferedWriter( new FileWriter( path + File.separator + "namesC.txt", true ) );
        dWriter = new BufferedWriter( new FileWriter( path + File.separator + "namesD.txt", true ) );
        eWriter = new BufferedWriter( new FileWriter( path + File.separator + "namesE.txt", true ) );
        fWriter = new BufferedWriter( new FileWriter( path + File.separator + "namesF.txt", true ) );
        gWriter = new BufferedWriter( new FileWriter( path + File.separator + "namesG.txt", true ) );
        hWriter = new BufferedWriter( new FileWriter( path + File.separator + "namesH.txt", true ) );
        iWriter = new BufferedWriter( new FileWriter( path + File.separator + "namesI.txt", true ) );
        jWriter = new BufferedWriter( new FileWriter( path + File.separator + "namesJ.txt", true ) );
        kWriter = new BufferedWriter( new FileWriter( path + File.separator + "namesK.txt", true ) );
        lWriter = new BufferedWriter( new FileWriter( path + File.separator + "namesL.txt", true ) );
        mWriter = new BufferedWriter( new FileWriter( path + File.separator + "namesM.txt", true ) );
        nWriter = new BufferedWriter( new FileWriter( path + File.separator + "namesN.txt", true ) );
        oWriter = new BufferedWriter( new FileWriter( path + File.separator + "namesO.txt", true ) );
        pWriter = new BufferedWriter( new FileWriter( path + File.separator + "namesP.txt", true ) );
        qWriter = new BufferedWriter( new FileWriter( path + File.separator + "namesQ.txt", true ) );
        rWriter = new BufferedWriter( new FileWriter( path + File.separator + "namesR.txt", true ) );
        sWriter = new BufferedWriter( new FileWriter( path + File.separator + "namesS.txt", true ) );
        tWriter = new BufferedWriter( new FileWriter( path + File.separator + "namesT.txt", true ) );
        uWriter = new BufferedWriter( new FileWriter( path + File.separator + "namesU.txt", true ) );
        vWriter = new BufferedWriter( new FileWriter( path + File.separator + "namesV.txt", true ) );
        wWriter = new BufferedWriter( new FileWriter( path + File.separator + "namesW.txt", true ) );
        xWriter = new BufferedWriter( new FileWriter( path + File.separator + "namesX.txt", true ) );
        yWriter = new BufferedWriter( new FileWriter( path + File.separator + "namesY.txt", true ) );
        zWriter = new BufferedWriter( new FileWriter( path + File.separator + "namesZ.txt", true ) );
        numericWriter = new BufferedWriter( new FileWriter( path + File.separator + "names0_9.txt", true ) );

        loaded = true;
        System.out.println("Writers loaded.");
    }

    /**
     * @throws IOException
     */
    public void closeWriters( ) throws IOException {
        aWriter.close( );
        bWriter.close( );
        cWriter.close( );
        dWriter.close( );
        eWriter.close( );
        fWriter.close( );
        gWriter.close( );
        hWriter.close( );
        iWriter.close( );
        jWriter.close( );
        kWriter.close( );
        lWriter.close( );
        mWriter.close( );
        nWriter.close( );
        oWriter.close( );
        pWriter.close( );
        qWriter.close( );
        rWriter.close( );
        sWriter.close( );
        tWriter.close( );
        uWriter.close( );
        vWriter.close( );
        wWriter.close( );
        xWriter.close( );
        yWriter.close( );
        zWriter.close( );
        numericWriter.close( );

        loaded = false;
        System.out.println("Writers closed.");
    }
}
