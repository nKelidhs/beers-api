package com.beersAPI.beers.Helper;


import com.beersAPI.beers.Enumerator.BeerType;
import org.springframework.core.convert.converter.Converter;

public class StringToBeerTypeEnumConverter implements Converter<String, BeerType> {

    @Override
    public BeerType convert(String source) {
            return BeerType.valueOf(source.toUpperCase());

    }
}
