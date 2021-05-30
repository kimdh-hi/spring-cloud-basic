package com.example.catalogservice.controller;

import com.example.catalogservice.dto.CatalogDto;
import com.example.catalogservice.service.CatalogService;
import com.example.catalogservice.vo.ResponseCatalog;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/catalog-service")
public class CatalogController {

    private final CatalogService catalogService;
    private final Environment env;

    @GetMapping("/catalogs")
    public ResponseEntity<List<ResponseCatalog>> getAllCatalogs() {
        List<ResponseCatalog> result = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();

        List<CatalogDto> catalogs = catalogService.getAllCatalogs();
        catalogs.forEach(c -> {
            result.add(mapper.map(c, ResponseCatalog.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
