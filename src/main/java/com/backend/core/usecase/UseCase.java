package com.backend.core.usecase;

public abstract class UseCase<In extends UseCase.InputValues, Out extends UseCase.OutputValues> {
    public abstract Out execute(In input);

    public interface InputValues {
    }

    public interface OutputValues {
    }
}
