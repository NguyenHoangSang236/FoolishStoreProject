package com.backend.core.usecase;

import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@Service
public class UseCaseExecutorImpl implements UseCaseExecutor{
    @Override
    public <Any, I extends UseCase.InputValues, O extends UseCase.OutputValues> CompletableFuture<Any> execute(UseCase<I, O> useCase, I input, Function<O, Any> outputMapper) {
        return CompletableFuture
                .supplyAsync(() -> input)
                .thenApplyAsync(useCase::execute)
                .thenApplyAsync(outputMapper);
    }
}
