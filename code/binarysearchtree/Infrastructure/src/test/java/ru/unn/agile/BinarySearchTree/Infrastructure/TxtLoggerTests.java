package ru.unn.agile.BinarySearchTree.Infrastructure;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.unn.agile.BinarySearchTree.Infrastructure.RegexMatcher.matchesPattern;

public class TxtLoggerTests {
    private static final String FILENAME = "./Logger.log";
    private TxtLogger txtLogger;

    @Before
    public void setUp() {
        txtLogger = new TxtLogger(FILENAME);
    }

    @Test
    public void canCreateLogFile() {
        try {
            new BufferedReader(new FileReader(FILENAME));
        } catch (FileNotFoundException e) {
            fail("File " + FILENAME + " wasn't found!");
        }
    }

    @Test
    public void canWriteMessageInLog() {
        String testMessage = "Testing message";

        txtLogger.log(testMessage);

        String message = txtLogger.getLog().get(0);
        assertThat(message, matchesPattern(".*" + testMessage + "$"));
    }

    @Test
    public void canCreateLoggerWithFileName() {
        assertNotNull(txtLogger);
    }

    @Test
    public void canWriteMoreLogMessage() {
        String[] messages = {"Test message One", "Test message Two", "Testing Message Three"};

        txtLogger.log(messages[0]);
        txtLogger.log(messages[1]);
        txtLogger.log(messages[2]);

        List<String> actualMessages = txtLogger.getLog();
        assertThat(actualMessages.get(0), matchesPattern(".*" + messages[0] + "$"));
        assertThat(actualMessages.get(1), matchesPattern(".*" + messages[1] + "$"));
        assertThat(actualMessages.get(2), matchesPattern(".*" + messages[2] + "$"));
    }

    @Test
    public void doesLogContainDateAndTime() {
        String testMessage = "Test message";

        txtLogger.log(testMessage);

        String message = txtLogger.getLog().get(0);
        assertThat(message, matchesPattern("^\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2} : .*"));
    }
}
