package com.ariel.BeersApi.Controllers;

import com.ariel.BeersApi.Abstractions.ValidationController;
import com.ariel.BeersApi.Dto.DrinkTypesRequest;
import com.ariel.BeersApi.Dto.DrinkTypesResponse;
import com.ariel.BeersApi.Dto.PageDto;
import com.ariel.BeersApi.Dto.ServiceResponse;
import com.ariel.BeersApi.Services.DrinkTypesService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.*;

@RestController()
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/drinkTypes")
public class DrinkTypesController extends ValidationController {
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
    public ResponseEntity<?> create(@RequestBody()DrinkTypesRequest dto) {
        ServiceResponse<DrinkTypesResponse> serverResponse = service.create(dto);
        if (serverResponse.isError()) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", serverResponse.getMessage());
            return ResponseEntity.badRequest().body(errors);
        }
        return ResponseEntity.status(201).body(serverResponse.getResponse());
    }

    @ApiResponse(responseCode = "204")
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@RequestAttribute(name = "id") int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}
