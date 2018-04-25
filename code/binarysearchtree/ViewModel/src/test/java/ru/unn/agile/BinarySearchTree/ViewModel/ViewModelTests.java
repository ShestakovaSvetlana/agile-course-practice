package ru.unn.agile.BinarySearchTree.ViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ViewModelTests {
    private ViewModel viewModel;

    @Before
    public void setUp() {
        if (viewModel == null) {
            viewModel = new ViewModel();
        }
    }

    @After
    public void tearDown() {
        viewModel = null;
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
}
