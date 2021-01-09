package com.github.errayeil.EDAExtractor.Main;

import com.github.errayeil.EDAExtractor.Extractor.NameExtractor;
import com.opencsv.exceptions.CsvValidationException;

import javax.swing.*;
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

        NameExtractor extractor = new NameExtractor( null);

        try {
            extractor.updateNames();
        } catch ( IOException | CsvValidationException e ) {
            e.printStackTrace( );
        }
    }
}
