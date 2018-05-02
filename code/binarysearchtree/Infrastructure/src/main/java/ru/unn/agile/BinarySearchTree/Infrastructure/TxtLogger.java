package ru.unn.agile.BinarySearchTree.Infrastructure;

import ru.unn.agile.BinarySearchTree.ViewModel.ILogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TxtLogger implements ILogger {
    private static final String DATE_FORMAT_NOW = "dd-MM-yyyy HH:mm:ss";
    private final BufferedWriter bufferedWriter;
    private final String filename;

    private static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW, Locale.ENGLISH);
        return sdf.format(cal.getTime());
    }

    public TxtLogger(final String filename) {
        this.filename = filename;

        BufferedWriter logsWriter = null;
        try {
            logsWriter = new BufferedWriter(new FileWriter(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
        bufferedWriter = logsWriter;
    }

    @Override
    public void log(final String s) {
        try {
            bufferedWriter.write(now() + " : " + s);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        BufferedReader bufReader;
        ArrayList<String> logs = new ArrayList<String>();
        try {
            bufReader = new BufferedReader(new FileReader(filename));
            String line = bufReader.readLine();

            while (line != null) {
                logs.add(line);
                line = bufReader.readLine();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return logs;
    }

}
