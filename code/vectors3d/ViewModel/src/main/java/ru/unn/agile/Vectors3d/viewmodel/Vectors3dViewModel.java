package ru.unn.agile.Vectors3d.viewmodel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import ru.unn.agile.Vectors3d.Model.Vector3d;

public class Vectors3dViewModel {

    public Vectors3dViewModel() {
        BooleanBinding isCalculationAvailable = new BooleanBinding() {
            {
                super.bind(firstVectorViewModel.normalizeDisabledProperty(),
                        secondVectorViewModel.normalizeDisabledProperty());
            }

            @Override
            protected boolean computeValue() {
                return !firstVectorViewModel.normalizeDisabledProperty().get()
                        && !secondVectorViewModel.normalizeDisabledProperty().get();
            }
        };
        calculationIsNotAvailable.bind(isCalculationAvailable.not());
    }

    public VectorViewModel getFirstVectorViewModel() {
        return firstVectorViewModel;
    }

    public VectorViewModel getSecondVectorViewModel() {
        return secondVectorViewModel;
    }

    public ResultNumberViewModel getResultNumberViewModel() {
        return resultNumberViewModel;
    }

    public VectorViewModel getResultVectorViewModel() {
        return resultVectorViewModel;
    }

    public BooleanProperty calculationIsNotAvailableProperty() {
        return calculationIsNotAvailable;
    }

    public final boolean isCalculationDisabled() {
        return calculationIsNotAvailable.get();
    }

    public void calculateDotProduct() {
        Vector3d firstVector = firstVectorViewModel.getVector3d();
        Vector3d secondVector = secondVectorViewModel.getVector3d();
        Double result = firstVector.dotProduct(secondVector);
        resultNumberViewModel.setResult(result);
    }

    public void calculateCrossProduct() {
        Vector3d firstVector = firstVectorViewModel.getVector3d();
        Vector3d secondVector = secondVectorViewModel.getVector3d();
        Vector3d result = firstVector.crossProduct(secondVector);
        resultVectorViewModel.setVector3d(result);
    }

    private final VectorViewModel firstVectorViewModel = new VectorViewModel();
    private final VectorViewModel secondVectorViewModel = new VectorViewModel();
    private final ResultNumberViewModel resultNumberViewModel = new ResultNumberViewModel();
    private final VectorViewModel resultVectorViewModel = new VectorViewModel();

    private final BooleanProperty calculationIsNotAvailable = new SimpleBooleanProperty();
}
