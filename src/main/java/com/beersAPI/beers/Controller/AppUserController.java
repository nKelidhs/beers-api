package com.beersAPI.beers.Controller;

import com.beersAPI.beers.Model.Response.Response;
import com.beersAPI.beers.Model.AppUser;
import com.beersAPI.beers.Service.AppUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class AppUserController {

    private final AppUserService appUserService;

    @Operation(summary = "Create new User")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode =  "400")
    })
    @PostMapping("/user/create")
    public ResponseEntity<Response> createUser(@RequestBody AppUser appUser) {
        try {
            return ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("appUser", appUserService.create(appUser)))
                    .message("User Successfully Created")
                    .status(CREATED)
                    .statusCode(CREATED.value())
                    .build()
            );
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(BAD_REQUEST, ex.getMessage());
        }
    }

    @Operation(summary = "Retrieve all the users. The Password is encoded using BCryptPasswordEncoder")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode =  "400")
    })
    @GetMapping("/users")
    public ResponseEntity<Response> getUsers() {
        try {
            return ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("appUsers", appUserService.getUsers()))
                    .message("Users Successfully Created")
                    .status(OK)
                    .statusCode(OK.value())
                    .build()
            );
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(BAD_REQUEST, ex.getMessage());
        }
    }
}
