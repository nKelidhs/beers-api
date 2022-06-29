package com.beersAPI.beers.Controller;

import com.beersAPI.beers.Enumerator.BeerType;
import com.beersAPI.beers.Model.Beer;
import com.beersAPI.beers.Helper.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.beersAPI.beers.Service.BeerService;

import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

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
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(Map.of("beer", beerService.create(beer)))
                .message("Beer Successfully Created")
                .status(CREATED)
                .statusCode(CREATED.value())
                .build()
        );
    }

    @DeleteMapping("/beer/delete/{id}")
    public ResponseEntity<Response> deleteBeer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .message("Beer Successfully Deleted")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }

    @PutMapping("/beer/update")
    public ResponseEntity<Response> updateBeerRate(@RequestBody Beer beer) {
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .message("Beer Successfully Rated")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }

    @GetMapping("/beer/{id}")
    public ResponseEntity<Response> getBeer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(Map.of("beer", beerService.getById(id)))
                .message("Beer Successfully Fetched")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }

    @GetMapping("/beer")
    public ResponseEntity<Response> getBeerByName(@RequestParam(name = "name") String name) {
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(Map.of("beer", beerService.getByName(name)))
                .message("Beer Successfully Fetched")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }

}
