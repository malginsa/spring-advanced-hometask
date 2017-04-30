package ua.epam.spring.hometask.ws.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ua.epam.spring.hometask.domain.sample.localhost._8080.ws.countries.GetCountryRequest;
import ua.epam.spring.hometask.domain.sample.localhost._8080.ws.countries.GetCountryResponse;
import ua.epam.spring.hometask.ws.repository.CountryRepository;

@Endpoint
public class CountryEndpoint {
    private static final String NAMESPACE_URI =
        "http://localhost:8080/ws/countries";

    private CountryRepository countryRepository;

    @Autowired
    public CountryEndpoint(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
    @ResponsePayload
    public GetCountryResponse getCountry(@RequestPayload
                                             GetCountryRequest request) {
        GetCountryResponse response = new GetCountryResponse();
        response.setCountry(countryRepository.findCountry(request.getName()));
        return response;
    }
}
