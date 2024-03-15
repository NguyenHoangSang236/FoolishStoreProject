package com.backend.core.usecase;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.usecase.TestUseCaseOutputMapper;
import com.backend.core.usecase.UseCaseExecutor;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class TestController {
    private UseCaseExecutorImpl useCaseExecutor;
    private TestUseCaseOutputMapper outputMapper;
    private TestUseCase testUseCase;


    @GetMapping("/unauthen/test")
    public CompletableFuture<ResponseEntity<ApiResponse>> testRequest(@RequestParam("type") String type) throws IOException {
        return useCaseExecutor.execute(
                testUseCase,
                new TestUseCase.InputValues(type),
                (TestUseCaseOutputMapper::map)
        );
    }
}
