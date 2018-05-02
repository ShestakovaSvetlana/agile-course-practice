package ru.unn.agile.Vectors3d.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.Vectors3d.Model.Vector3d;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class VectorViewModelTests {

    @Before
    public void setUp() {
        viewModel = new VectorViewModel();
    }

    @After
    public void clean() {
        viewModel = null;
    }

    @Test
    public void canGetDefaultValues() {
        assertEquals("0.0", viewModel.getXProperty().get());
        assertEquals("0.0", viewModel.getYProperty().get());
        assertEquals("0.0", viewModel.getZProperty().get());
        assertFalse(viewModel.isNormalizeDisabled());
        Vector3d vector = viewModel.getVector3d();
        assertEquals(0.0, vector.getX(), 0.0);
        assertEquals(0.0, vector.getY(), 0.0);
        assertEquals(0.0, vector.getZ(), 0.0);
    }

    @Test
    public void canGetIsFilled() {
        viewModel.setX("1.0");
        viewModel.setY("2.0");
        viewModel.setZ("3.0");
        assertFalse(viewModel.isNormalizeDisabled());
    }

    @Test
    public void canGetIsFilledWhenOneFieldIsEmpty() {
        viewModel.setX("");
        assertTrue(viewModel.isNormalizeDisabled());
    }

    @Test
    public void canGetIsFilledWhenAllFieldsAreEmpty() {
        viewModel.setX("");
        viewModel.setY("");
        viewModel.setZ("");
        assertTrue(viewModel.isNormalizeDisabled());
    }

    @Test
    public void canNormalizeZeroVector() {
        double zero = 0.0;
        viewModel.setCoordinates("0.0", "0.0", "0.0");
        viewModel.normalize();
        assertEquals(zero, Double.parseDouble(viewModel.getXProperty().get()), PRECISION);
        assertEquals(zero, Double.parseDouble(viewModel.getYProperty().get()), PRECISION);
        assertEquals(zero, Double.parseDouble(viewModel.getZProperty().get()), PRECISION);
    }

    @Test
    public void canNormalizeVectorWithOneNonZeroCoordinate() {
        viewModel.setCoordinates("2.0", "0.0", "0.0");
        viewModel.normalize();
        assertEquals(1.0, Double.parseDouble(viewModel.getXProperty().get()), PRECISION);
        assertEquals(0.0, Double.parseDouble(viewModel.getYProperty().get()), PRECISION);
        assertEquals(0.0, Double.parseDouble(viewModel.getZProperty().get()), PRECISION);
    }

    @Test
    public void canNormalizeNonZeroVector() {
        viewModel.setCoordinates("1", "1.", "1.0");
        viewModel.normalize();
        double normalizedValue = 1. / Math.sqrt(3.0);
        assertEquals(normalizedValue,
                Double.parseDouble(viewModel.getXProperty().get()), PRECISION);
        assertEquals(normalizedValue,
                Double.parseDouble(viewModel.getYProperty().get()), PRECISION);
        assertEquals(normalizedValue,
                Double.parseDouble(viewModel.getZProperty().get()), PRECISION);
    }

    @Test
    public void canNormalizeVectorWithBigDecimals() {
        Double x = 1231231.123;
        Double y = 1.12313e16;
        Double z = -23.4234e-5;

        viewModel.setX(x.toString());
        viewModel.setY(y.toString());
        viewModel.setZ(z.toString());
        viewModel.normalize();
        double normalizingValue = Math.sqrt(x * x + y * y + z * z);
        assertEquals(x / normalizingValue,
                Double.parseDouble(viewModel.getXProperty().get()), PRECISION);
        assertEquals(y / normalizingValue,
                Double.parseDouble(viewModel.getYProperty().get()), PRECISION);
        assertEquals(z / normalizingValue,
                Double.parseDouble(viewModel.getZProperty().get()), PRECISION);
    }

    @Test
    public void canSetVector3dWithBigDecimalCoordinates() {
        Vector3d vector = new Vector3d(10e-15, 0.000000001, 15.24234234234);
        viewModel.setVector3d(vector);
        assertEquals("0", viewModel.getXProperty().get());
        assertEquals("0", viewModel.getYProperty().get());
        assertEquals("15.24234234", viewModel.getZProperty().get());
    }

    @Test
    public void canNormalizeWithAlmostZeroCoordinates() {
        viewModel.setCoordinates("4324e-15", "0.00000000235", "14636.532");
        viewModel.normalize();
        assertEquals("0", viewModel.getXProperty().get());
        assertEquals("0", viewModel.getYProperty().get());
        assertEquals("1", viewModel.getZProperty().get());
    }

    private VectorViewModel viewModel;
    private static final double PRECISION = 1e-7;
}
