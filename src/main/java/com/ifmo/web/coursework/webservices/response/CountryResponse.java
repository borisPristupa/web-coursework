package com.ifmo.web.coursework.webservices.response;

import com.ifmo.web.coursework.data.entity.Country;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CountryResponse {
    private int id;
    private String name;

    public static CountryResponse fromCountry(Country country) {
        return CountryResponse.builder()
                .id(country.getCountryId())
                .name(country.getName())
                .build();
    }
}
