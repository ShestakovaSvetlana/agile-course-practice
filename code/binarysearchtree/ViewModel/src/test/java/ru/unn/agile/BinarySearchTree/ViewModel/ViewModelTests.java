package ru.unn.agile.BinarySearchTree.ViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ViewModelTests {
    public void setViewModel(final ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    private ViewModel viewModel;

    @After
    public void tearDown() {
        viewModel = null;
    }
    @Before
    public void setUp() {
        if (viewModel == null) {
            viewModel = new ViewModel(new FakeLogger());
        }
    }

    @Test
    public void canSetDefaultValues() {
        assertEquals("", viewModel.keyProperty().get());
        assertEquals("", viewModel.valueProperty().get());
        assertEquals(Operation.INSERT, viewModel.operationProperty().get());
        assertEquals("—", viewModel.resultKeyProperty().get());
        assertEquals("—", viewModel.resultValueProperty().get());
        assertEquals(State.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canSetSuccessMessage() {
        setInputData();

        viewModel.execute();

        assertEquals(State.SUCCESS.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void cantInputGlyph() {
        viewModel.keyProperty().set("a");

        assertEquals(State.BAD_FORMAT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void cantInputIrrationalNumber() {
        viewModel.keyProperty().set("0.1");

        assertEquals(State.BAD_FORMAT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsWaitingIfKeyFieldEmptyAndInsertMode() {
        viewModel.operationProperty().set(Operation.INSERT);
        viewModel.valueProperty().set("1");

        assertEquals(State.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsWaitingIfValueFieldEmptyAndInsertMode() {
        viewModel.operationProperty().set(Operation.INSERT);
        viewModel.keyProperty().set("1");

        assertEquals(State.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsWaitingIfKeyFieldEmptyAndDeleteMode() {
        viewModel.operationProperty().set(Operation.DELETE);
        viewModel.valueProperty().set("1");

        assertEquals(State.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsWaitingIfValueFieldEmptyAndFindMode() {
        viewModel.operationProperty().set(Operation.FIND);
        viewModel.keyProperty().set("1");

        assertEquals(State.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void executeButtonIsDisabledInitially() {
        assertTrue(viewModel.executeDisabledProperty().get());
    }

    @Test
    public void executeButtonIsDisabledWhenFormatIsBad() {
        setInputData();
        viewModel.keyProperty().set("0.1");

        assertTrue(viewModel.executeDisabledProperty().get());
    }

    @Test
    public void executeButtonIsDisabledWithIncompleteInputWhenInsertMode() {
        viewModel.operationProperty().set(Operation.INSERT);
        viewModel.keyProperty().set("1");

        assertTrue(viewModel.executeDisabledProperty().get());
    }

    @Test
    public void calculateButtonIsEnabledWithCorrectInputWhenInsertMode() {
        viewModel.operationProperty().set(Operation.INSERT);
        setInputData();

        assertFalse(viewModel.executeDisabledProperty().get());
    }

    @Test
    public void executeButtonIsDisabledWithIncompleteInputWhenDeleteMode() {
        viewModel.operationProperty().set(Operation.DELETE);
        viewModel.valueProperty().set("1");

        assertTrue(viewModel.executeDisabledProperty().get());
    }

    @Test
    public void calculateButtonIsEnabledWithCorrectInputWhenDeleteMode() {
        viewModel.operationProperty().set(Operation.DELETE);
        setInputData();

        assertFalse(viewModel.executeDisabledProperty().get());
    }

    @Test
    public void executeButtonIsDisabledWithIncompleteInputWhenFindMode() {
        viewModel.operationProperty().set(Operation.FIND);
        viewModel.keyProperty().set("1");

        assertTrue(viewModel.executeDisabledProperty().get());
    }

    @Test
    public void calculateButtonIsEnabledWithCorrectInputWhenFindMode() {
        viewModel.operationProperty().set(Operation.FIND);
        setInputData();

        assertFalse(viewModel.executeDisabledProperty().get());
    }

    @Test
    public void executeButtonIsEnableAlwaysWhenGetRootMode() {
        viewModel.operationProperty().set(Operation.GET_ROOT);

        assertFalse(viewModel.executeDisabledProperty().get());
    }

    @Test
    public void addIsDefaultOperation() {
        assertEquals(Operation.INSERT, viewModel.operationProperty().get());
    }

    @Test
    public void canSetInsertOperation() {
        viewModel.operationProperty().set(Operation.INSERT);
        assertEquals(Operation.INSERT, viewModel.operationProperty().get());
    }

    @Test
    public void canSetDeleteOperation() {
        viewModel.operationProperty().set(Operation.DELETE);
        assertEquals(Operation.DELETE, viewModel.operationProperty().get());
    }

    @Test
    public void canSetFindOperation() {
        viewModel.operationProperty().set(Operation.FIND);
        assertEquals(Operation.FIND, viewModel.operationProperty().get());
    }

    @Test
    public void canSetGetRootOperation() {
        viewModel.operationProperty().set(Operation.GET_ROOT);
        assertEquals(Operation.GET_ROOT, viewModel.operationProperty().get());
    }

    @Test
    public void operationInsertHasCorrectResult() {
        viewModel.operationProperty().set(Operation.INSERT);
        viewModel.keyProperty().set("2");
        viewModel.valueProperty().set("Two");

        viewModel.execute();
        viewModel.operationProperty().set(Operation.GET_ROOT);
        viewModel.execute();

        assertEquals("2", viewModel.resultKeyProperty().get());
        assertEquals("Two", viewModel.resultValueProperty().get());
    }

    @Test
    public void operationFindHasCorrectResult() {
        insertNodes();
        viewModel.operationProperty().set(Operation.FIND);
        viewModel.valueProperty().set("Two");

        viewModel.execute();

        assertEquals("2", viewModel.resultKeyProperty().get());
    }

    @Test
    public void operationDeleteHasCorrectResult() {
        insertNodes();
        viewModel.operationProperty().set(Operation.DELETE);
        viewModel.keyProperty().set("2");

        viewModel.execute();
        viewModel.operationProperty().set(Operation.FIND);
        viewModel.valueProperty().set("Two");
        viewModel.execute();

        assertEquals(State.NODE_NOT_FOUND.toString(), viewModel.statusProperty().get());
    }

    @Test(expected = IllegalArgumentException.class)
    public void viewModelConstructorGiveExceptionIfLoggerNull() {
        new ViewModel(null);
    }

    @Test
    public void logIsEmptyWhenStarted() {
        List<String> log = viewModel.getLog();

        assertTrue(log.isEmpty());
    }

    @Test
    public void logContainsMessageAfterInsert() {
        insertNode();

        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*" + LogMessages.INSERT_WAS_EXECUTED + ".*"));
    }

    @Test
    public void logContainsMessageAfterFind() {
        insertNodes();
        findNode();

        String message = viewModel.getLog().get(3);

        assertTrue(message.matches(".*" + LogMessages.FIND_WAS_EXECUTED + ".*"));
    }

    @Test
    public void logContainsMessageAfterDelete() {
        insertNodes();
        deleteNode();

        String message = viewModel.getLog().get(3);

        assertTrue(message.matches(".*" + LogMessages.DELETE_WAS_EXECUTED + ".*"));
    }

    @Test
    public void logContainsMessageAfterGetRoot() {
        insertNodes();
        viewModel.operationProperty().set(Operation.GET_ROOT);
        viewModel.execute();

        String message = viewModel.getLog().get(3);

        assertTrue(message.matches(".*" + LogMessages.GET_ROOT_WAS_EXECUTED + ".*"));
    }

    @Test
    public void logContainsArgumentsAfterInsert() {
        insertNode();

        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*" + viewModel.keyProperty().get()
                + ".*" + viewModel.valueProperty().get() + ".*"));
    }

    @Test
    public void logContainsArgumentsAfterFind() {
        insertNodes();
        findNode();

        String message = viewModel.getLog().get(3);

        assertTrue(message.matches(".*" + viewModel.valueProperty().get() + ".*"));
    }

    @Test
    public void logContainsArgumentsAfterDelete() {
        insertNodes();
        deleteNode();

        String message = viewModel.getLog().get(3);

        assertTrue(message.matches(".*" + viewModel.keyProperty().get() + ".*"));
    }

    @Test
    public void operationChangeInLog() {
        setInputData();

        viewModel.onOperationChanged(Operation.INSERT, Operation.FIND);

        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*" + LogMessages.OPERATION_WAS_CHANGED + "Find.*"));
    }

    @Test
    public void writeSeveralLogMessages() {
        setInputData();

        viewModel.execute();
        viewModel.execute();
        viewModel.execute();

        assertEquals(3, viewModel.getLog().size());
    }

    @Test
    public void operationIsNotWrittenIfNotChanged() {
        viewModel.onOperationChanged(Operation.INSERT, Operation.DELETE);

        viewModel.onOperationChanged(Operation.DELETE, Operation.DELETE);

        assertEquals(1, viewModel.getLog().size());
    }

    @Test
    public void argumentsAreCorrectlyWritten() {
        setInputData();

        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);

        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*" + LogMessages.EDITING_FINISHED
                + "Key: "
                + viewModel.keyProperty().get() + "; Value: "
                + viewModel.valueProperty().get() + ";"));
    }

    @Test
    public void executeIsNotCalledWhenButtonIsDisabled() {
        viewModel.execute();

        assertTrue(viewModel.getLog().isEmpty());
    }

    @Test
    public void sameParametersDoNotWrittenTwice() {
        viewModel.keyProperty().set("1");
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);
        viewModel.keyProperty().set("1");
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);

        assertEquals(1, viewModel.getLog().size());
    }

    private void setInputData() {
        viewModel.keyProperty().set("1");
        viewModel.valueProperty().set("2");
    }

    private void insertNodes() {
        viewModel.operationProperty().set(Operation.INSERT);
        viewModel.keyProperty().set("5");
        viewModel.valueProperty().set("Five");
        viewModel.execute();
        viewModel.keyProperty().set("2");
        viewModel.valueProperty().set("Two");
        viewModel.execute();
        viewModel.keyProperty().set("9");
        viewModel.valueProperty().set("Nine");
        viewModel.execute();
    }

    private void insertNode() {
        viewModel.operationProperty().set(Operation.INSERT);
        viewModel.keyProperty().set("5");
        viewModel.valueProperty().set("Five");
        viewModel.execute();
    }

    private void findNode() {
        viewModel.operationProperty().set(Operation.FIND);
        viewModel.valueProperty().set("Two");
        viewModel.execute();
    }

    private void deleteNode() {
        viewModel.operationProperty().set(Operation.DELETE);
        viewModel.keyProperty().set("2");
        viewModel.execute();
    }
}
