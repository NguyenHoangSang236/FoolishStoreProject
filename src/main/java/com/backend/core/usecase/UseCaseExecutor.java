package com.backend.core.usecase;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;


public interface UseCaseExecutor {
    <Any, I extends UseCase.InputValues, O extends UseCase.OutputValues> CompletableFuture<Any> execute(
            UseCase<I, O> useCase,
            I input,
            Function<O, Any> outputMapper
    );
}
