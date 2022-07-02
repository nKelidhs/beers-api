package com.beersAPI.beers.Controller;

import com.beersAPI.beers.Enumerator.BeerType;
import com.beersAPI.beers.Model.Request.UpdateRatingRequest;
import com.beersAPI.beers.Model.Response.Response;
import com.beersAPI.beers.Model.Beer;
import com.beersAPI.beers.Service.BeerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@SecurityRequirement(name = "bearerAuth")
public class BeerController {

    private final BeerService beerService;

    @Operation(summary = "Get beers with filters", description = "The results are inside the data property. Returns a list of beers based on the parameters. " +
            "If no parameters are provided, it will return all the beers")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode =  "400", description = "Validations failed")
    })
    @GetMapping("/beers")
    public ResponseEntity<Response> getBeers(@RequestParam(required = false, name = "name") String name,
                                             @RequestParam(required = false, name = "type") BeerType type,
                                             @RequestParam(required = false, name = "rate") Integer rate) {
        try {
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(Map.of("beers", beerService.getBeersWithFilters(name, type, rate)))
                .message("Beers Successfully Retrieved")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(BAD_REQUEST, ex.getMessage());
        }
    }

    @Operation(summary = "Create new beer", description = "Name and Type are required and rate is optional. If no rate is provided then the beer will have 0 rate." +
            " Also, the beer name is unique. Returns the new beer inside the data property if successful")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "Successfully created"),
            @ApiResponse(responseCode =  "400", description = "Validations failed")
    })
    @PostMapping("/beer/create")
    public ResponseEntity<Response> createBeer(@RequestBody Beer beer) {
        try {
            return ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("beer", beerService.create(beer)))
                    .message("Beer Successfully Created")
                    .status(CREATED)
                    .statusCode(CREATED.value())
                    .build()
            );
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(BAD_REQUEST, ex.getMessage());
        }
    }

    @Operation(summary = "Delete an existing beer")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "Successfully deleted"),
            @ApiResponse(responseCode =  "404", description = "Beer not found")
    })
    @DeleteMapping("/beer/{id}/delete")
    public ResponseEntity<Response> deleteBeer(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("success", beerService.delete(id)))
                    .message("Beer Successfully Deleted")
                    .status(OK)
                    .statusCode(OK.value())
                    .build()
            );
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(NOT_FOUND, ex.getMessage());
        }
    }

    @Operation(summary = "Rate an existing beer", description = "Rate must be between 0 and 5.")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "Successfully Rated"),
            @ApiResponse(responseCode =  "400", description = "Validations failed"),
            @ApiResponse(responseCode =  "404", description = "Beer not found")
    })
    @PutMapping("/beer/{id}/update-rating")
    public ResponseEntity<Response> updateBeerRate(@PathVariable("id") Long id, @RequestBody UpdateRatingRequest updateRatingRequest) {
        try {
            return ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("success", beerService.updateRate(id, updateRatingRequest)))
                    .message("Beer Successfully Rated")
                    .status(OK)
                    .statusCode(OK.value())
                    .build()
            );
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(BAD_REQUEST, ex.getMessage());
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(NOT_FOUND, ex.getMessage());
        }
    }

    @Operation(summary = "Retrieves an existing beer based on the id", description = "The results are inside the data property. Returns a single beer with the provided id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "Successfully Retrieved"),
            @ApiResponse(responseCode =  "400", description = "Validations failed"),
            @ApiResponse(responseCode =  "404", description = "Beer not found")
    })
    @GetMapping("/beer/{id}")
    public ResponseEntity<Response> getBeer(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("beer", beerService.getById(id)))
                    .message("Beer Successfully Retrieved")
                    .status(OK)
                    .statusCode(OK.value())
                    .build()
            );
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(BAD_REQUEST, ex.getMessage());
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(NOT_FOUND, ex.getMessage());
        }
    }

    @Operation(summary = "Retrieves an existing beer based on it's name", description = "The results are inside the data property. Returns a single beer with the provided name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "Successfully Retrieved"),
            @ApiResponse(responseCode =  "400", description = "Validations failed"),
            @ApiResponse(responseCode =  "404", description = "Beer not found")
    })
    @GetMapping("/beer")
    public ResponseEntity<Response> getBeerByName(@RequestParam(name = "name") String name) {

        try {
            return ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("beer", beerService.getByName(name)))
                    .message("Beer Successfully Retrieved")
                    .status(OK)
                    .statusCode(OK.value())
                    .build()
            );
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(BAD_REQUEST, ex.getMessage());
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(NOT_FOUND, ex.getMessage());
        }
    }

}
