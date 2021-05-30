package com.example.catalogservice.service;

import com.example.catalogservice.domain.CatalogEntity;
import com.example.catalogservice.dto.CatalogDto;
import com.example.catalogservice.repository.CatalogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CatalogService {

    private final CatalogRepository catalogRepository;

    public List<CatalogDto> getAllCatalogs() {
        List<CatalogDto> result = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Iterable<CatalogEntity> catalogs = catalogRepository.findAll();
        catalogs.forEach(c -> {
            result.add(mapper.map(c, CatalogDto.class));
        });
        return result;
    }

}
