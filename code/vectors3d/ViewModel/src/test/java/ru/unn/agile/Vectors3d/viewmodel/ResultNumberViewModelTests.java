package ru.unn.agile.Vectors3d.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ResultNumberViewModelTests {

    @Before
    public void setUp() {
        viewModel = new ResultNumberViewModel();
    }

    @After
    public void clean() {
        viewModel = null;
    }

    @Test
    public void canGetDefaultValue() {
        assertTrue(viewModel.resultProperty().get().isEmpty());
    }

    @Test
    public void canSetResult() {
        viewModel.setResult(123.0);
        assertEquals("123.0", viewModel.resultProperty().get());
    }

    private ResultNumberViewModel viewModel;
}
