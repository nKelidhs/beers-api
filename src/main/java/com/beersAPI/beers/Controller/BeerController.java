package com.beersAPI.beers.Controller;

import com.beersAPI.beers.Enumerator.BeerType;
import com.beersAPI.beers.Helper.Request.UpdateRatingRequest;
import com.beersAPI.beers.Model.Beer;
import com.beersAPI.beers.Helper.Response;
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

    @GetMapping("/beers")
    public ResponseEntity<Response> getBeers(@RequestParam(required = false, name = "name") String name,
                                             @RequestParam(required = false, name = "type") BeerType type,
                                             @RequestParam(required = false, name = "rate") Integer rate) {
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(Map.of("beers", beerService.getBeersWithFilters(name, type, rate)))
                .message("Beers Successfully Fetched")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }

    @PostMapping("/beer/save")
    public ResponseEntity<Response> saveBeer(@RequestBody Beer beer) {
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

    @GetMapping("/beer/{id}")
    public ResponseEntity<Response> getBeer(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("beer", beerService.getById(id)))
                    .message("Beer Successfully Fetched")
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

    @GetMapping("/beer")
    public ResponseEntity<Response> getBeerByName(@RequestParam(name = "name") String name) {

        try {
            return ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("beer", beerService.getByName(name)))
                    .message("Beer Successfully Fetched")
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
