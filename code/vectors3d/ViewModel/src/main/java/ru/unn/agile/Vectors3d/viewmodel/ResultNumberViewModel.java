package ru.unn.agile.Vectors3d.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ResultNumberViewModel {

    public ResultNumberViewModel() {
        result.set("");
    }

    public StringProperty resultProperty() {
        return result;
    }

    public void setResult(final Double res) {
        result.set(res.toString());
    }

    private final StringProperty result = new SimpleStringProperty();
}
