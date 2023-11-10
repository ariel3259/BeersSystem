package com.ariel.BeersApi.Controllers;

import com.ariel.BeersApi.Dto.DrinkTypesRequest;
import com.ariel.BeersApi.Dto.DrinkTypesResponse;
import com.ariel.BeersApi.Dto.PageDto;
import com.ariel.BeersApi.Dto.ServiceResponse;
import com.ariel.BeersApi.Exceptions.HttpException;
import com.ariel.BeersApi.Services.DrinkTypesService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController()
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/drinkTypes")
public class DrinkTypesController {
    private final DrinkTypesService service;

    @Autowired
    public DrinkTypesController(DrinkTypesService s) {
        super();
        service = s;
    }

    @GetMapping
    public ResponseEntity<List<DrinkTypesResponse>> getAll(@RequestParam(name = "page") Optional<Integer> page, @RequestParam(name = "limit") Optional<Integer> limit, HttpServletResponse servletResponse) {
        PageDto<DrinkTypesResponse> dto = service.getAll(page, limit);
        servletResponse.addHeader("x-total-count", String.format("%d", dto.getTotalItems()));
        return ResponseEntity.ok(dto.getElements());
    }

    @PostMapping
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = DrinkTypesResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = HashMap.class), mediaType = "application/json"))
    })
    public ResponseEntity<?> create(@RequestBody()DrinkTypesRequest dto) throws HttpException {
        ServiceResponse<DrinkTypesResponse> serviceResponse = service.create(dto);
        if (serviceResponse.isError()) throw new HttpException(400, serviceResponse.getMessage());
        return ResponseEntity.status(201).body(serviceResponse.getResponse());
    }

    @ApiResponse(responseCode = "204")
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@RequestAttribute(name = "id") int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}
