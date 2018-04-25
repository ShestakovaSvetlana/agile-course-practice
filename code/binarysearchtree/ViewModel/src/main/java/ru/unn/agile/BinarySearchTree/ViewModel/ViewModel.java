package ru.unn.agile.BinarySearchTree.ViewModel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.unn.agile.BinarySearchTree.Model.BinarySearchTree;
import ru.unn.agile.BinarySearchTree.Model.Node;

import java.util.ArrayList;
import java.util.List;

public class ViewModel {
    private final StringProperty key = new SimpleStringProperty();
    private final StringProperty value = new SimpleStringProperty();

    private final ObjectProperty<ObservableList<Operation>> operations =
            new SimpleObjectProperty<>(FXCollections.observableArrayList(Operation.values()));
    private final ObjectProperty<Operation> operation = new SimpleObjectProperty<>();
    private final BooleanProperty executeDisabled = new SimpleBooleanProperty();
    private final BooleanProperty keyFieldDisabled = new SimpleBooleanProperty();
    private final BooleanProperty valueFieldDisabled = new SimpleBooleanProperty();

    private final StringProperty resultKey = new SimpleStringProperty();
    private final StringProperty resultValue = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();

    private final BinarySearchTree tree = new BinarySearchTree();

    private List<ValueChangeListener> valueChangedListeners;

    private class ValueChangeListener implements ChangeListener<String> {
        private String previousValue = new String();
        private String currentValue = new String();

        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            if (oldValue.equals(newValue)) {
                return;
            }
            status.set(getState().toString());
            currentValue = newValue;
        }

        public boolean isChanged() {
            return !previousValue.equals(currentValue);
        }

        public void cache() {
            previousValue = currentValue;
        }
    }

    public ViewModel() {
        init();
    }

    public void execute() {
        if (executeDisabled.get()) {
            return;
        }

        String tempKey = "—";
        String tempValue = "—";
        String statusResult = State.SUCCESS.toString();
        StringBuilder message = new StringBuilder();

        switch (operation.get()) {
            case INSERT:
                Node node = new Node(Integer.parseInt(key.get()), value.get());
                tree.insert(node);
                message.append("Key = ").append(key.get())
                        .append("; Value = ").append(value.get())
                        .append(";");
                break;
            case FIND:
                List<Node> nodes = tree.findByValue(value.get());
                if (nodes.isEmpty()) {
                    statusResult = State.NODE_NOT_FOUND.toString();
                } else {
                    tempKey = Integer.toString(getFirstNodeKey(nodes));
                    tempValue = getFirstNodeValue(nodes).toString();
                }
                message.append("Value = ").append(value.get())
                        .append(";");
                break;
            case DELETE:
                tree.delete(Integer.parseInt(key.get()));
                message.append("Key = ").append(key.get())
                        .append(";");
                break;
            case GET_ROOT:
                Node result = tree.getRoot();
                tempKey = Integer.toString(result.getKey());
                tempValue = result.getValue().toString();
                break;
            default:
                break;
        }

        resultKey.set(tempKey);
        resultValue.set(tempValue);
        status.set(statusResult);
    }

    public void onOperationChanged(final Operation oldValue, final Operation newValue) {
        if (oldValue.equals(newValue)) {
            return;
        }
        StringBuilder message = new StringBuilder();
        message.append(newValue.toString());
    }

    public void onFocusChanged(final Boolean oldValue, final Boolean newValue) {
        if (!oldValue && newValue) {
            return;
        }

        for (ValueChangeListener listener : valueChangedListeners) {
            if (listener.isChanged()) {
                StringBuilder message = new StringBuilder();
                message.append("Key: ")
                        .append(key.get()).append("; Value: ")
                        .append(value.get()).append(";");

                listener.cache();
                break;
            }
        }
    }


    public StringProperty keyProperty() {
        return key;
    }

    public StringProperty valueProperty() {
        return value;
    }

    public final ObservableList<Operation> getOperations() {
        return operations.get();
    }

    public ObjectProperty<Operation> operationProperty() {
        return operation;
    }

    public BooleanProperty executeDisabledProperty() {
        return executeDisabled;
    }

    public final boolean isExecuteDisabled() {
        return executeDisabled.get();
    }

    public final boolean isValueFieldDisabled() {
        return valueFieldDisabled.get();
    }

    public final boolean isKeyFieldDisabled() {
        return keyFieldDisabled.get();
    }

    public StringProperty resultKeyProperty() {
        return resultKey;
    }

    public final String getResultKey() {
        return resultKey.get();
    }

    public StringProperty resultValueProperty() {
        return resultValue;
    }

    public final String getResultValue() {
        return resultValue.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public final String getStatus() {
        return status.get();
    }

    private void init() {
        key.set("");
        value.set("");
        operation.set(Operation.INSERT);
        resultKey.set("—");
        resultValue.set("—");
        status.set(State.WAITING.toString());
        bindDeterminate();
        createFieldsValueChangingListeners();
    }

    private State getState() {

        State currentStatus = State.READY;

        if (operation.get() == Operation.INSERT && (key.get().isEmpty() || value.get().isEmpty())) {
            currentStatus = State.WAITING;
        }

        if ((operation.get() == Operation.DELETE && key.get().isEmpty())
                || (operation.get() == Operation.FIND && value.get().isEmpty())) {
            currentStatus = State.WAITING;
        }

        if (operationProperty().get() == Operation.GET_ROOT) {
            currentStatus = State.READY;
        }

        try {
            if (!key.get().isEmpty()) {
                Integer.parseInt(key.get());
            }
        } catch (NumberFormatException nfe) {
            currentStatus = State.BAD_FORMAT;
        }

        status.set(currentStatus.toString());
        return currentStatus;
    }

    private void bindDeterminate() {
        BooleanBinding couldExecute = new BooleanBinding() {
            {
                super.bind(key, value, operation);
            }

            @Override
            protected boolean computeValue() {
                State currentState = getState();
                status.set(currentState.toString());
                return currentState == State.READY;
            }
        };
        executeDisabled.bind(couldExecute.not());

        BooleanBinding couldChangeKeyField = new BooleanBinding() {
            {
                super.bind(operation);
            }

            @Override
            protected boolean computeValue() {
                return operation.get() != Operation.FIND && operation.get() != Operation.GET_ROOT;
            }
        };

        keyFieldDisabled.bind(couldChangeKeyField.not());

        BooleanBinding couldChangeValueField = new BooleanBinding() {
            {
                super.bind(operation);
            }

            @Override
            protected boolean computeValue() {
                return operation.get() == Operation.INSERT || operation.get() == Operation.FIND;
            }
        };

        valueFieldDisabled.bind(couldChangeValueField.not());
    }

    private void createFieldsValueChangingListeners() {
        final List<StringProperty> stringFields = new ArrayList<StringProperty>() {
            {
                add(key);
                add(value);
            }
        };
        valueChangedListeners = new ArrayList<>();
        for (StringProperty field : stringFields) {
            final ValueChangeListener listener = new ValueChangeListener();
            field.addListener(listener);
            valueChangedListeners.add(listener);
        }
    }

    private int getFirstNodeKey(final List<Node> nodes) {
        return getFirstNode(nodes).getKey();
    }

    private Object getFirstNodeValue(final List<Node> nodes) {
        return getFirstNode(nodes).getValue();
    }

    private Node getFirstNode(final List<Node> nodes) {
        return nodes.get(0);
    }

}

enum State {
    WAITING("Please input data"),
    READY("Press 'Execute'"),
    NODE_NOT_FOUND("Node not found"),
    BAD_FORMAT("Bad format"),
    SUCCESS("Success");

    private final String name;

    State(final String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
