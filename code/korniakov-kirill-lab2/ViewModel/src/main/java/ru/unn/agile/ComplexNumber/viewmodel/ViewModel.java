package ru.unn.agile.ComplexNumber.viewmodel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.unn.agile.ComplexNumber.model.ComplexNumber;
import ru.unn.agile.ComplexNumber.model.ComplexNumber.Operation;

import java.util.ArrayList;
import java.util.List;

public class ViewModel {
    private final StringProperty re1 = new SimpleStringProperty();
    private final StringProperty im1 = new SimpleStringProperty();
    private final StringProperty re2 = new SimpleStringProperty();
    private final StringProperty im2 = new SimpleStringProperty();

    private final ObjectProperty<ObservableList<Operation>> operations =
            new SimpleObjectProperty<>(FXCollections.observableArrayList(Operation.values()));
    private final ObjectProperty<Operation> operation = new SimpleObjectProperty<>();
    private final BooleanProperty calculationDisabled = new SimpleBooleanProperty();

    private final StringProperty result = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();

    private final List<ValueChangeListener> valueChangedListeners = new ArrayList<>();

    // FXML needs default c-tor for binding
    public ViewModel() {
        re1.set("");
        im1.set("");
        re2.set("");
        im2.set("");
        operation.set(Operation.ADD);
        result.set("");
        status.set(Status.WAITING.toString());

        BooleanBinding couldCalculate = new BooleanBinding() {
            {
                super.bind(re1, im1, re2, im2);
            }
            @Override
            protected boolean computeValue() {
                return getInputStatus() == Status.READY;
            }
        };
        calculationDisabled.bind(couldCalculate.not());

        // Add listeners to the input text fields
        final List<StringProperty> fields = new ArrayList<StringProperty>() { {
            add(re1);
            add(im1);
            add(re2);
            add(im2);
        } };

        for (StringProperty field : fields) {
            final ValueChangeListener listener = new ValueChangeListener();
            field.addListener(listener);
            valueChangedListeners.add(listener);
        }
    }

    public void calculate() {
        if (calculationDisabled.get()) {
            return;
        }

        ComplexNumber z1 = new ComplexNumber(re1.get(), im1.get());
        ComplexNumber z2 = new ComplexNumber(re2.get(), im2.get());

        result.set(operation.get().apply(z1, z2).toString());
        status.set(Status.SUCCESS.toString());
    }

    public StringProperty re1Property() {
        return re1;
    }
    public StringProperty im1Property() {
        return im1;
    }
    public StringProperty re2Property() {
        return re2;
    }
    public StringProperty im2Property() {
        return im2;
    }
    public final ObservableList<Operation> getOperations() {
        return operations.get();
    }
    public ObjectProperty<Operation> operationProperty() {
        return operation;
    }
    public BooleanProperty calculationDisabledProperty() {
        return calculationDisabled;
    }
    public final boolean isCalculationDisabled() {
        return calculationDisabled.get();
    }

    public StringProperty resultProperty() {
        return result;
    }
    public final String getResult() {
        return result.get();
    }
    public StringProperty statusProperty() {
        return status;
    }
    public final String getStatus() {
        return status.get();
    }

    private Status getInputStatus() {
        Status inputStatus = Status.READY;
        if (re1.get().isEmpty() || im1.get().isEmpty()
         || re2.get().isEmpty() || im2.get().isEmpty()) {
            inputStatus = Status.WAITING;
        }
        try {
            if (!re1.get().isEmpty()) {
                Double.parseDouble(re1.get());
            }
            if (!im1.get().isEmpty()) {
                Double.parseDouble(im1.get());
            }
            if (!re2.get().isEmpty()) {
                Double.parseDouble(re2.get());
            }
            if (!im2.get().isEmpty()) {
                Double.parseDouble(im2.get());
            }
        } catch (NumberFormatException nfe) {
            inputStatus = Status.BAD_FORMAT;
        }

        return inputStatus;
    }

    private class ValueChangeListener implements ChangeListener<String> {
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            status.set(getInputStatus().toString());
        }
    }
}

enum Status {
    WAITING("Please provide input data"),
    READY("Press 'Calculate' or Enter"),
    BAD_FORMAT("Bad format"),
    SUCCESS("Success");

    private final String name;
    Status(final String name) {
        this.name = name;
    }
    public String toString() {
        return name;
    }
}
