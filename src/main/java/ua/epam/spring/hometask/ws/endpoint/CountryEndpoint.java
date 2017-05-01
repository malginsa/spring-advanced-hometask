package ua.epam.spring.hometask.ws.endpoint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ua.epam.spring.hometask.domain.sample.Country;
import ua.epam.spring.hometask.domain.sample.GetCountryRequest;
import ua.epam.spring.hometask.domain.sample.GetCountryResponse;
import ua.epam.spring.hometask.ws.repository.CountryRepository;

@Endpoint
public class CountryEndpoint {

    private static final Logger LOG = LogManager.getLogger();

    private static final String NAMESPACE_URI = "http://localhost:8080/ws/countries";

    private CountryRepository countryRepository;

    @Autowired
    public CountryEndpoint(CountryRepository countryRepository) {
        LOG.info("CountryEndpoint bean is instantiated");
        this.countryRepository = countryRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
    @ResponsePayload
    public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) {
        LOG.info("CountryEndpoint.getCountry() is invoked");
        GetCountryResponse response = new GetCountryResponse();
        Country country = countryRepository.findCountry(request.getName());
        response.setCountry(country);
        return response;
    }
}
