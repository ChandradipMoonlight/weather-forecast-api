# Weather Forecast API

***

## Overview
This API provides weather forecast information for different locations using integrated APIs.

### Integrated APIs
1. **RapidApiGetForecastSummaryByLocationName**
    - Summary Forecast Endpoint: http://localhost:9091/api/v1/forecast/{location}/summary
    - Description: Provides a summary of the weather forecast for the specified location.
    - Note: Replace `{location}` in the URL with the desired city name to get the summary for different locations.

2. **RapidApiGetHourlyForecastByLocationName**
    - Hourly Forecast Endpoint: http://localhost:9091/api/v1/forecast/{location}/hourly
    - Description: Provides hourly weather forecast details for the specified location.
    - Note: This API might return a 401 Unauthorized error because access permission for this API is disabled. To enable access, a subscription is required.

### Swagger API Documentation
Access the Swagger API documentation at [Swagger UI](http://localhost:9091/webjars/swagger-ui/index.html).

Please note the correct endpoint URLs and the note regarding access permission for the hourly forecast API.

### Start application using below command
`mvn spring-boot:run`
