package ru.unn.agile.Vectors3d.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.Vectors3d.Model.Vector3d;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Vectors3dViewModelTests {

    @Before
    public void setUp() {
        viewModel = new Vectors3dViewModel();
    }

    @After
    public void clean() {
        viewModel = null;
    }

    @Test
    public void canGetDefaultValues() {
        Vector3d zeroVector = new Vector3d(0, 0, 0);
        assertTrue(viewModel.getFirstVectorViewModel().getVector3d().equalCompletely(zeroVector));
        assertTrue(viewModel.getSecondVectorViewModel().getVector3d().equalCompletely(zeroVector));
        assertTrue(viewModel.getResultVectorViewModel().getVector3d().equalCompletely(zeroVector));
        assertEquals("", viewModel.getResultNumberViewModel().resultProperty().get());
        assertFalse(viewModel.isCalculationDisabled());
    }

    @Test
    public void canGetCalculationAvailableWhenOneOfVectorsIsNotFilled() {
        viewModel.getFirstVectorViewModel().setX("");
        assertTrue(viewModel.isCalculationDisabled());
    }

    @Test
    public void canGetCalculationAvailableWhenBothVectorsAreNotFilled() {
        viewModel.getFirstVectorViewModel().setX("");
        viewModel.getSecondVectorViewModel().setZ("");
        assertTrue(viewModel.isCalculationDisabled());
    }

    @Test
    public void canGetCalculationAvailableWhenBothVectorsAreFilled() {
        VectorViewModel firstVector = viewModel.getFirstVectorViewModel();
        firstVector.setCoordinates("1123.2", "-23.1", "34");

        VectorViewModel secondVector = viewModel.getSecondVectorViewModel();
        secondVector.setCoordinates("666", "-0.23", "-0");

        assertFalse(viewModel.isCalculationDisabled());
    }

    @Test
    public void canGetResultOfDotProductOfTwoZeroVectors() {
        VectorViewModel firstVector = viewModel.getFirstVectorViewModel();
        firstVector.setCoordinates("0.0", "0.0", "0.0");

        VectorViewModel secondVector = viewModel.getSecondVectorViewModel();
        secondVector.setCoordinates("0.0", "0.0", "0.0");

        viewModel.calculateDotProduct();
        assertEquals("0.0", viewModel.getResultNumberViewModel().resultProperty().get());
    }

    @Test
    public void canGetResultOfDotProductWhenOneVectorIsZero() {
        VectorViewModel firstVector = viewModel.getFirstVectorViewModel();
        firstVector.setCoordinates("0.0", "0.0", "0.0");

        VectorViewModel secondVector = viewModel.getSecondVectorViewModel();
        secondVector.setCoordinates("1.0", "2.0", "3.0");

        viewModel.calculateDotProduct();
        assertEquals("0.0", viewModel.getResultNumberViewModel().resultProperty().get());
    }

    @Test
    public void canGetResultOfDotProduct() {
        VectorViewModel firstVector = viewModel.getFirstVectorViewModel();
        firstVector.setCoordinates("1.0", "2.0", "3.0");

        VectorViewModel secondVector = viewModel.getSecondVectorViewModel();
        secondVector.setCoordinates("4.0", "5.0", "6.0");

        viewModel.calculateDotProduct();
        assertEquals("32.0", viewModel.getResultNumberViewModel().resultProperty().get());
    }

    @Test
    public void canGetResultOfCrossProductOfTwoZeroVectors() {
        VectorViewModel firstVector = viewModel.getFirstVectorViewModel();
        firstVector.setCoordinates("0.0", "0.0", "0.0");

        VectorViewModel secondVector = viewModel.getSecondVectorViewModel();
        secondVector.setCoordinates("0.0", "0.0", "0.0");

        viewModel.calculateCrossProduct();
        assertTrue(viewModel.getResultVectorViewModel().getVector3d().equalCompletely(
                new Vector3d(0, 0, 0)
        ));
    }

    @Test
    public void canGetResultOfCrossProductWhenOneVectorIsZero() {
        VectorViewModel firstVector = viewModel.getFirstVectorViewModel();
        firstVector.setCoordinates("0.0", "0.0", "0.0");

        VectorViewModel secondVector = viewModel.getSecondVectorViewModel();
        secondVector.setCoordinates("1.0", "2.0", "3.0");

        viewModel.calculateCrossProduct();
        assertTrue(viewModel.getResultVectorViewModel().getVector3d().equalCompletely(
                new Vector3d(0, 0, 0)
        ));
    }

    @Test
    public void canGetResultOfCrossProduct() {
        VectorViewModel firstVector = viewModel.getFirstVectorViewModel();
        firstVector.setCoordinates("1.0", "2.0", "3.0");

        VectorViewModel secondVector = viewModel.getSecondVectorViewModel();
        secondVector.setCoordinates("4.0", "5.0", "6.0");

        viewModel.calculateCrossProduct();
        assertTrue(viewModel.getResultVectorViewModel().getVector3d().equalCompletely(
                new Vector3d(-3, 6, -3)
        ));
    }

    private Vectors3dViewModel viewModel;
}
