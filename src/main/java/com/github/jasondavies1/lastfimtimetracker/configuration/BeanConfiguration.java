package com.github.jasondavies1.lastfimtimetracker.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jasondavies1.lastfimtimetracker.converter.MapToTrackDtoConverter;
import com.github.jasondavies1.lastfimtimetracker.converter.TrackDtoToAlbumDtoConverter;
import com.github.jasondavies1.lastfimtimetracker.converter.TrackDtoToTrackConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final MapToTrackDtoConverter mapToTrackDtoConverter;
    private final TrackDtoToAlbumDtoConverter trackDtoToAlbumDtoConverter;
    private final TrackDtoToTrackConverter trackDtoToTrackConverter;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public ConversionService conversionService() {
        final DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(mapToTrackDtoConverter);
        conversionService.addConverter(trackDtoToAlbumDtoConverter);
        conversionService.addConverter(trackDtoToTrackConverter);
        return conversionService;
    }


}
