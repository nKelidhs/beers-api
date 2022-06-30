package com.beersAPI.beers.Controller;

import com.beersAPI.beers.Enumerator.BeerType;
import com.beersAPI.beers.Helper.Request.UpdateRatingRequest;
import com.beersAPI.beers.Model.Beer;
import com.beersAPI.beers.Helper.Response;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.beersAPI.beers.Service.BeerService;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BeerController {

    private final BeerService beerService;

    @ApiOperation(value = "Get beers with filters", notes = "The results are inside the data property. Returns a list of beers based on the parameters. " +
            "If no parameters are provided, it will return all the beers")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 404, message = "Not found - The product was not found")
    })
    @GetMapping("/beers")
    public ResponseEntity<Response> getBeers(@RequestParam(required = false, name = "name") String name,
                                             @RequestParam(required = false, name = "type") BeerType type,
                                             @RequestParam(required = false, name = "rate") Integer rate) {
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(Map.of("beers", beerService.getBeersWithFilters(name, type, rate)))
                .message("Beers Successfully retrieved")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }

    @ApiOperation(value = "Create new beer", notes = "Name and Type are required. Also, name must be unique. Returns the new beer inside the data property if successful")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created"),
            @ApiResponse(code = 400, message = "Validations failed")
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

    @ApiOperation(value = "Delete an existing beer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted"),
            @ApiResponse(code = 404, message = "Beer not found")
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

    @ApiOperation(value = "Rate an existing beer", notes = "Rate must be between 1 and 5.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Rated"),
            @ApiResponse(code = 400, message = "Validations failed"),
            @ApiResponse(code = 404, message = "Beer not found")
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

    @ApiOperation(value = "Retrieves a beer based on the id", notes = "The results are inside the data property. Returns a single beer with the provided id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Retrieved"),
            @ApiResponse(code = 400, message = "Validations failed"),
            @ApiResponse(code = 404, message = "Beer not found")
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

    @ApiOperation(value = "Retrieves a beer based on the name", notes = "The results are inside the data property. Returns a single beer with the provided id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Retrieved"),
            @ApiResponse(code = 400, message = "Validations failed"),
            @ApiResponse(code = 404, message = "Beer not found")
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
