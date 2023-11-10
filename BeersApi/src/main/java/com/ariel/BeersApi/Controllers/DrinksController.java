package com.ariel.BeersApi.Controllers;

import com.ariel.BeersApi.Dto.DrinksRequest;
import com.ariel.BeersApi.Dto.DrinksResponse;
import com.ariel.BeersApi.Dto.PageDto;
import com.ariel.BeersApi.Dto.ServiceResponse;
import com.ariel.BeersApi.Exceptions.HttpException;
import com.ariel.BeersApi.Services.DrinksService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/drinks")
public class DrinksController {
    private final DrinksService service;

    @Autowired
    public DrinksController(DrinksService s) {
        service = s;
    }


    @GetMapping
    public ResponseEntity<List<DrinksResponse>> getAll(@RequestParam("page") Optional<Integer> page, @RequestParam("offset") Optional<Integer> limit, @RequestParam("drinkTypesId") Optional<Integer> drinkTypesId, HttpServletResponse res) {
        PageDto<DrinksResponse> pageResponse = service.getAll(page, limit, drinkTypesId);
        res.addHeader("x-total-count",  "" + pageResponse.getTotalItems());
        return ResponseEntity.ok(pageResponse.getElements());
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody DrinksRequest request) throws HttpException {
        ServiceResponse<DrinksResponse> serviceResponse = service.save(request);
        if (serviceResponse.isError()) throw new HttpException(400, serviceResponse.getMessage());
        URI uriCreated = URI.create("/api/drinks");
        return ResponseEntity.created(uriCreated).body(serviceResponse.getResponse());
    }
}
