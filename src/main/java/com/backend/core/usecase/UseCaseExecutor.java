package com.backend.core.usecase;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;


public interface UseCaseExecutor {
    <Any, In extends UseCase.InputValues, Out extends UseCase.OutputValues> CompletableFuture<Any> execute(
            UseCase<In, Out> useCase,
            In input,
            Function<Out, Any> outputMapper
    );
}
