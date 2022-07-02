package com.beersAPI.beers.Config;

import com.beersAPI.beers.Enumerator.BeerType;
import com.beersAPI.beers.Model.AppUser;
import com.beersAPI.beers.Model.Beer;
import com.beersAPI.beers.Repository.BeerRepository;
import com.beersAPI.beers.Service.AppUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
        //Populate Beers Table
    CommandLineRunner beerCommandLineRunner(BeerRepository repository) {
        return args -> {
            Beer Bergina = new Beer(
                    1L,
                    "Bergina",
                    BeerType.LAGER,
                    5);

            Beer BerginaPilsner = new Beer(
                    2L,
                    "Bergina Pilsner",
                    BeerType.PILSNER,
                    3);

            Beer Mythos = new Beer(
                    3L,
                    "Mythos",
                    BeerType.LAGER,
                    4);

            Beer Pils = new Beer(
                    4L,
                    "Pils",
                    BeerType.PILSNER,
                    2);

            Beer Amstel = new Beer(
                    5L,
                    "Amstel",
                    BeerType.LAGER,
                    1);

            Beer Alfa = new Beer(
                    6L,
                    "Alfa",
                    BeerType.LAGER,
                    4);

            Beer Corona = new Beer(
                    7L,
                    "Corona",
                    BeerType.LAGER,
                    2);

            Beer Septem = new Beer(
                    8L,
                    "Septem",
                    BeerType.PILSNER,
                    5);

            Beer Fischer = new Beer(
                    9L,
                    "Fischer",
                    BeerType.LAGER,
                    3);

            Beer SknipaSalonikia = new Beer(
                    10L,
                    "Sknipa Salonikia",
                    BeerType.PILSNER,
                    5);

            repository.saveAll(List.of(Bergina, BerginaPilsner, Mythos, Pils, Amstel, Alfa, Corona, Septem, Fischer, SknipaSalonikia));
        };
    }


    @Bean
        //Populate Users Table
    CommandLineRunner appUserCommandLineRunner(AppUserService appUserService) {
        return args -> appUserService.create(new AppUser(null, "Nikos Kelidis", "admin", "admin"));
    }
}
