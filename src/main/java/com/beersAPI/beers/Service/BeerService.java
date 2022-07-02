package com.beersAPI.beers.Service;

import com.beersAPI.beers.Enumerator.BeerType;
import com.beersAPI.beers.Model.Request.UpdateRatingRequest;
import com.beersAPI.beers.Model.Beer;
import com.beersAPI.beers.Repository.BeerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BeerService {

    private final BeerRepository beerRepository;

    public List<Beer> getBeersWithFilters(String name, BeerType type, Integer rate) {
        if (rate != null && !isRateRangeValid(rate)) throw new IllegalArgumentException("Rate must be between 0 and 5");

        log.info("Fetching beers with filters");

        Beer beer = new Beer();
        beer.setName(name);
        beer.setType(type);
        beer.setRate(rate);

        return beerRepository.findAll(Example.of(beer), Sort.by(Sort.Direction.ASC, "id"));
    }

    public Beer create(Beer beer) throws IllegalArgumentException {
        if (!isNameValid(beer.getName())) throw new IllegalArgumentException("Name cannot be null or empty");
        if (!isTypeValid(beer.getType())) throw new IllegalArgumentException("Beer Type cannot be null");

        log.info("Saving new beer {}", beer.getName());
        if (beer.getRate() != null && !isRateRangeValid(beer.getRate())) throw new IllegalArgumentException("Rate must be between 0 and 5");
        if (beerRepository.findByName(beer.getName()).isPresent())
            throw new IllegalArgumentException("A beer with the same name already exist in the database");

        return beerRepository.save(beer);
    }

    public Boolean delete(Long id) throws EntityNotFoundException {
        if (!isIdValid(id)) throw new IllegalArgumentException("Id cannot be null or 0");

        log.info("Deleting beer with id {}", id);
        Optional<Beer> beer = beerRepository.findById(id);
        if (beer.isEmpty()) throw new EntityNotFoundException(String.format("Beer with id %s not found in the database", id));

        beerRepository.deleteById(id);
        return true;
    }

    public Boolean updateRate(Long id, UpdateRatingRequest request) throws IllegalArgumentException, EntityNotFoundException {
        if (!isIdValid(id)) throw new IllegalArgumentException("Id cannot be null or 0");
        if (!isRateValid(request.getRate())) throw new IllegalArgumentException("Rate must be between 0 and 5");

        log.info("Updating the rate of beer with id {}", id);
        Optional<Beer> beer = beerRepository.findById(id);
        if (beer.isEmpty()) throw new EntityNotFoundException(String.format("Beer with id %s not found in the database", id));

        beerRepository.setBeerRateById(id, request.getRate());
        return true;
    }

    public Beer getById(Long id) throws IllegalArgumentException, EntityNotFoundException {
        if (!isIdValid(id)) throw new IllegalArgumentException("Id parameter cannot be null or 0");

        log.info("Fetching beer with id {}", id);
        var beer = beerRepository.findById(id);
        if (beer.isEmpty()) throw new EntityNotFoundException(String.format("Beer with id %s not found in the database", id));

        return beer.get();

    }

    public Beer getByName(String name) throws IllegalArgumentException, EntityNotFoundException {
        if (!isNameValid(name)) throw new IllegalArgumentException("Name cannot be null or empty");

        log.info("Fetching beer {}", name);
        var beer = beerRepository.findByName(name);
        if (beer.isEmpty()) throw new EntityNotFoundException(String.format("Beer with name %s not found in the database", name));

        return beer.get();
    }

    //region Validations

    private Boolean isIdValid(Long id) {
        if (id == null) return false;
        if (id == 0) return false;
        return true;
    }

    private Boolean isRateValid(Integer rate) {
        if (rate == null) return false;
        if (!isRateRangeValid(rate)) return false;
        return true;
    }

    private Boolean isNameValid(String name) {
        if (name == null) return false;
        if (name.isEmpty()) return false;
        return true;
    }

    private Boolean isTypeValid(BeerType type) {
        return type != null;
    }

    private Boolean isRateRangeValid(Integer rate) {
        return rate >= 0 && rate <= 5;
    }

    //endregion
}
