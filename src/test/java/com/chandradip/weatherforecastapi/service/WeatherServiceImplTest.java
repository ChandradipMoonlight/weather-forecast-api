package com.chandradip.weatherforecastapi.service;

import com.chandradip.weatherforecastapi.config.HttpHeaderConfig;
import com.chandradip.weatherforecastapi.mappers.AppConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.test.util.ReflectionTestUtils;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Map;

@PropertySource("classpath:application-test.properties")
public class WeatherServiceImplTest {
    @InjectMocks
    private WeatherServiceImpl weatherService;

    @Mock
    private ReactiveWebClientService reactiveWebClientService;

    @Mock
    private HttpHeaderConfig headerConfig;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(weatherService, "rapidBaseApiUrl", "your_rapid_api_base_url");
        ReflectionTestUtils.setField(weatherService, "rapidApiKey", "your_rapid_api_key");
        ReflectionTestUtils.setField(weatherService, "rapidApiHost", "your_rapid_api_host");
    }

    @Test
    void testGetForecastSummaryByLocationName_Success() throws NoSuchFieldException {
        //Mock request data
        String url = "testUrl";
        HttpHeaders headers = new HttpHeaders();
        headers.set(AppConstants.X_RAPID_API_KEY, "your_rapid_api_key");
        headers.set(AppConstants.X_RAPID_API_KEY, "your_rapid_api_host");
        String city = "testLocation";
        Map<String, String> map = Map.ofEntries(
                Map.entry(AppConstants.X_RAPID_API_KEY, "your_rapid_api_key"),
                Map.entry(AppConstants.X_RAPID_API_HOST , "your_rapid_api_host")
        );
        //Mock Headers
//        Mockito.when(headerConfig.setHeaders(Mockito.eq(Map.of("testKey", "testValue")))).thenReturn(headers);
//        Mockito.when(headerConfig.setHeaders(Mockito.eq(map))).thenReturn(headers);

        // mock the response
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("location", "testLocation");
        objectNode.put("temperature", 23);
        JsonNode successResponse = objectNode;

        Mockito.when(reactiveWebClientService.getForMono(Mockito.eq(url), Mockito.eq(JsonNode.class), Mockito.any(HttpHeaders.class))).thenReturn(Mono.just(successResponse));

        StepVerifier.create(weatherService.getForecastSummaryByLocationName(city)).expectNextCount(0)
//                .expectNextMatches(appResponse -> appResponse.getStatus().equals(AppConstants.SUCCESS))
                .verifyComplete();
    }
}
