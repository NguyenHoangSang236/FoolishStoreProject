package com.backend.core.infrastructure.business.category.controller;

import com.backend.core.entity.CrudController;
import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.category.model.Catalog;
import com.backend.core.infrastructure.config.api.ResponseMapper;
import com.backend.core.usecase.UseCaseExecutorImpl;
import com.backend.core.usecase.service.CrudService;
import com.backend.core.usecase.statics.RenderTypeEnum;
import com.backend.core.usecase.usecases.category.AddCategoryUseCase;
import com.backend.core.usecase.usecases.category.DeleteCategoryUseCase;
import com.backend.core.usecase.usecases.category.FilterCategoryUseCase;
import com.backend.core.usecase.usecases.category.UpdateCategoryUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.hibernate.sql.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class CategoryController {
    final UseCaseExecutorImpl useCaseExecutor;
    final AddCategoryUseCase addCategoryUseCase;
    final DeleteCategoryUseCase deleteCategoryUseCase;
    final FilterCategoryUseCase filterCategoryUseCase;
    final UpdateCategoryUseCase updateCategoryUseCase;


    @PostMapping("/authen/category/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CompletableFuture<ResponseEntity<ApiResponse>> addNewCategory(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Catalog catalog = objectMapper.readValue(json, Catalog.class);
        return useCaseExecutor.execute(
                addCategoryUseCase,
                new AddCategoryUseCase.InputValue(catalog),
                ResponseMapper::map
        );
    }

    @PostMapping("/authen/category/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CompletableFuture<ResponseEntity<ApiResponse>> updateCategory(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Catalog catalog = objectMapper.readValue(json, Catalog.class);
        return useCaseExecutor.execute(
                updateCategoryUseCase,
                new UpdateCategoryUseCase.InputValue(catalog),
                ResponseMapper::map
        );
    }


    @GetMapping("authen/category/delete_category_id={id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CompletableFuture<ResponseEntity<ApiResponse>> deleteCategoryById(@PathVariable int id, HttpServletRequest httpRequest) throws IOException {
        return useCaseExecutor.execute(
                deleteCategoryUseCase,
                new DeleteCategoryUseCase.InputValue(id),
                ResponseMapper::map
        );
    }

    @GetMapping("/unauthen/category/allCategories")
    public CompletableFuture<ResponseEntity<ApiResponse>> getListOfItems() throws IOException {
        return useCaseExecutor.execute(
                filterCategoryUseCase,
                new FilterCategoryUseCase.InputValue(),
                ResponseMapper::map
        );
    }
}
