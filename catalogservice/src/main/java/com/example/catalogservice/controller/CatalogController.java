package com.example.catalogservice.controller;

import com.example.catalogservice.domain.Catalog;
import com.example.catalogservice.dto.CatalogDto;
import com.example.catalogservice.service.CatalogService;
import com.example.catalogservice.vo.ResponseCatalog;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/catalog-service")
@RestController
public class CatalogController {

    private final Environment env;
    private final CatalogService catalogService;

    @GetMapping("/health-check")
    public String status() {
        return String.format("It's working in catalog service on port %s",
                env.getProperty("local.server.port"));
    }

    @GetMapping("/catalogs")
    public ResponseEntity<List<ResponseCatalog>> getCatalogAll() {
        List<Catalog> catalogs = catalogService.getCatalogAll();

        List<ResponseCatalog> responseCatalogs = catalogs.stream()
                .map(ResponseCatalog::of)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(responseCatalogs);
    }
}
