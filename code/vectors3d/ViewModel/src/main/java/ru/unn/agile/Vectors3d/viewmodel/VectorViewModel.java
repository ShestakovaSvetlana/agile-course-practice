package ru.unn.agile.Vectors3d.viewmodel;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import ru.unn.agile.Vectors3d.Model.Vector3d;

public class VectorViewModel {

    public VectorViewModel() {
        x.set("0.0");
        y.set("0.0");
        z.set("0.0");

        final List<StringProperty> fields = new ArrayList<StringProperty>() { {
            add(x);
            add(y);
            add(z);
        } };

        for (StringProperty field : fields) {
            final ValueChangeListener listener = new ValueChangeListener();
            field.addListener(listener);
            valueChangedListeners.add(listener);
        }
    }

    public StringProperty getXProperty() {
        return x;
    }

    public StringProperty getYProperty() {
        return y;
    }

    public StringProperty getZProperty() {
        return z;
    }

    public BooleanProperty normalizeDisabledProperty() {
        return isNormalizeDisabled;
    }

    public final boolean isNormalizeDisabled() {
        return isNormalizeDisabled.get();
    }

    public void setX(final String x) {
        this.x.set(x);
    }

    public void setY(final String y) {
        this.y.set(y);
    }

    public void setZ(final String z) {
        this.z.set(z);
    }

    public void setCoordinates(final String x, final String y, final String z) {
        setX(x);
        setY(y);
        setZ(z);
    }

    public Vector3d getVector3d() {
        return new Vector3d(Double.parseDouble(x.get()),
                Double.parseDouble(y.get()),
                Double.parseDouble(z.get()));
    }

    public void setVector3d(final Vector3d vector) {
        String xFormatted = formatCoordinate(vector.getX());
        String yFormatted = formatCoordinate(vector.getY());
        String zFormatted = formatCoordinate(vector.getZ());

        x.set(xFormatted);
        y.set(yFormatted);
        z.set(zFormatted);
    }

    public void normalize() {
        Vector3d normalizedVector = getVector3d().normalize();
        setVector3d(normalizedVector);
    }

    private class ValueChangeListener implements ChangeListener<String> {
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            try {
                Double.parseDouble(x.get());
                Double.parseDouble(y.get());
                Double.parseDouble(z.get());
                isNormalizeDisabled.set(false);
            } catch (Exception e) {
                isNormalizeDisabled.set(true);
            }
        }
    }

    private String formatCoordinate(final double coordinate) {
        BigDecimal decimalValue = BigDecimal.valueOf(coordinate)
                .setScale(NUMBER_OF_DIGITS_AFTER_POINT, BigDecimal.ROUND_HALF_UP);

        String roundedValue = decimalValue.toString();

        if (Math.abs(Double.parseDouble(roundedValue)) < ZERO_PRECISION) {
            return "0";
        }

        String regexForDoubleWithPoint = "[-+]?[0-9]+.[0-9]+";
        if (roundedValue.matches(regexForDoubleWithPoint)) {
            while (roundedValue.endsWith("0")) {
                roundedValue = roundedValue.substring(0, roundedValue.length() - 1);
            }

            if (roundedValue.endsWith(".")) {
                roundedValue = roundedValue.substring(0, roundedValue.length() - 1);
            }
        }

        return roundedValue;
    }

    private static final int NUMBER_OF_DIGITS_AFTER_POINT = 8;
    private static final double ZERO_PRECISION = 1e-7;

    private final StringProperty x = new SimpleStringProperty();
    private final StringProperty y = new SimpleStringProperty();
    private final StringProperty z = new SimpleStringProperty();

    private final List<ValueChangeListener> valueChangedListeners = new ArrayList<>();

    private final BooleanProperty isNormalizeDisabled = new SimpleBooleanProperty();
}
