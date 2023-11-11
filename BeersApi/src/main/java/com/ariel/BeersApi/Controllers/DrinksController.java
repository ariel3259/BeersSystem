package com.ariel.BeersApi.Controllers;

import com.ariel.BeersApi.Dto.*;
import com.ariel.BeersApi.Exceptions.HttpException;
import com.ariel.BeersApi.Services.DrinksService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
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
    public ResponseEntity<DrinksResponse> save(@RequestBody DrinksRequest request) throws HttpException {
        ServiceResponse<DrinksResponse> serviceResponse = service.save(request);
        if (serviceResponse.isError()) throw new HttpException(400, serviceResponse.getMessage());
        URI uriCreated = URI.create("/api/drinks");
        return ResponseEntity.created(uriCreated).body(serviceResponse.getResponse());
    }

    @PutMapping("{drinksId}")
    public ResponseEntity<DrinksResponse> update(@RequestBody DrinksUpdateRequest request, @RequestAttribute("drinksId") int drinksId) throws HttpException {
        ServiceResponse<DrinksResponse> serviceResponse = service.update(request, drinksId);
        if (serviceResponse.isError()) throw new HttpException(400, serviceResponse.getMessage());
        return ResponseEntity.ok(serviceResponse.getResponse());
    }

    @DeleteMapping("{drinksId}")
    public ResponseEntity<Void> delete(@RequestAttribute("drinksId") int drinksId) {
        service.delete(drinksId);
        return ResponseEntity.noContent().build();
    }
}
