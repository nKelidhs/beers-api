package com.beersAPI.beers.Service;

import com.beersAPI.beers.Enumerator.BeerType;
import com.beersAPI.beers.Model.Beer;
import com.beersAPI.beers.Repository.BeerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BeerService {

    private final BeerRepository beerRepository;

    public List<Beer> getAllBeers(){
        log.info("Fetching all beers");

        return beerRepository.findAll();
    }

    public Beer create(Beer beer){
        log.info("Saving new beer {}", beer.getName());
        //validation here

        return beerRepository.save(beer);
    }

    public Beer getById(Long id){
        log.info("Fetching beer with id {}", id);
        //validation here

        return beerRepository.findById(id).get();

    }

    public Beer getByName(String name){
        log.info("Fetching beer {}", name);
        //validation here

        return beerRepository.findByName(name);
    }

    public List<Beer> getBeersWithFilters(String name, BeerType type, Integer rate ){
        log.info("Fetching beers with filters");
        //validation here

        if (name == null && type == null && rate == null){
            var tt = beerRepository.findAll();

            return tt;
        }

        Beer beer = new Beer();
        beer.setName(name);
        beer.setType(type);
        if (rate != null){
            beer.setRate(rate);
        }

        return beerRepository.findAll(Example.of(beer));
    }


    public Boolean updateRate(Beer beer){
        log.info("Updating the rate of beer with id {}", beer.getId());
        //validation here

        beerRepository.setBeerRateById(beer.getRate(), beer.getId());
        return true;
    }


    public Boolean delete(Long id){
        log.info("Deleting beer with id {}", id);
        //validation here

        beerRepository.deleteById(id);
        return true;
    }
}
