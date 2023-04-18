package com.prime.rushhour.domain.provider.service;

import com.prime.rushhour.domain.provider.dto.ProviderRequestDto;
import com.prime.rushhour.domain.provider.dto.ProviderResponseDto;
import com.prime.rushhour.domain.provider.entity.Provider;
import com.prime.rushhour.domain.provider.mapper.ProviderMapper;
import com.prime.rushhour.domain.provider.repository.ProviderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProviderServiceImplTest {

    @InjectMocks
    private ProviderServiceImpl providerService;

    @Mock
    private ProviderRepository providerRepository;

    @Mock
    private ProviderMapper providerMapper;


    @Test
    void save() {

        ProviderRequestDto providerRequestDto = new ProviderRequestDto( "Filip Massage", "https://filip.com", "ft12", "+3816555333",
                LocalTime.of(8,0,0), LocalTime.of(17,0,0),
                Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY));

        Provider provider = new Provider
                ( "Filip Massage", "https://filip.com", "ft12", "+3816555333",
                        LocalTime.of(8,0,0), LocalTime.of(17,0,0),
                        Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY));

        ProviderResponseDto providerResponseDto = new ProviderResponseDto( "Filip Massage", "https://filip.com", "ft12", "+3816555333",
                LocalTime.of(8,0,0), LocalTime.of(17,0,0),
                Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY));


        when(providerMapper.toEntity(providerRequestDto)).thenReturn(provider);
        when(providerRepository.save(provider)).thenReturn(provider);
        when(providerMapper.toDto(provider)).thenReturn(providerResponseDto);

        ProviderResponseDto result = providerService.save(providerRequestDto);

        assertEquals(providerResponseDto, result);

        verify(providerMapper).toEntity(providerRequestDto);
        verify(providerRepository).save(provider);
        verify(providerMapper).toDto(provider);
    }

    @Test
    void getById() {
        Provider provider = new Provider
                ( "Filip Massage", "https://filip.com", "ft12", "+3816555333",
                        LocalTime.of(8,0,0), LocalTime.of(17,0,0),
                        Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY));

        when(providerRepository.findById(1L)).thenReturn(Optional.of(provider));

        ProviderResponseDto providerResponseDto = new ProviderResponseDto( "Filip Massage", "https://filip.com", "ft12", "+3816555333",
                LocalTime.of(8,0,0), LocalTime.of(17,0,0),
                Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY));

        when(providerMapper.toDto(provider)).thenReturn(providerResponseDto);

        providerResponseDto = providerService.getById(1L);

        assertEquals(provider.getName(), providerResponseDto.name());
    }

    @Test
    void getAll() {

        List<Provider> providers = Arrays.asList(
                new Provider("Filip Massage", "https://filip.com", "ft12", "+3816555333",
                        LocalTime.of(8,0,0), LocalTime.of(17,0,0),
                        Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY)),
                new Provider("John Spa", "https:/johnspa.com", "js22", "+3816555444",
                        LocalTime.of(9,0,0), LocalTime.of(18,0,0),
                        Set.of(DayOfWeek.TUESDAY, DayOfWeek.FRIDAY))
        );
        Page<Provider> page = new PageImpl<>(providers);

        when(providerRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(providerMapper.toDto(any(Provider.class))).thenReturn(new ProviderResponseDto(
                "Filip Massage", "https://filip.com", "ft12", "+3816555333",
                LocalTime.of(8,0,0), LocalTime.of(17,0,0),
                Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY)
        ));
        Page<ProviderResponseDto> result = providerService.getAll(PageRequest.of(0, 10));

        assertEquals(2, result.getContent().size());
        assertEquals("Filip Massage", result.getContent().get(0).name());
        assertEquals("https://filip.com", result.getContent().get(0).website());
        assertEquals("ft12", result.getContent().get(0).businessDomain());
        assertEquals("+3816555333", result.getContent().get(0).phone());
        assertEquals(LocalTime.of(8,0,0), result.getContent().get(0).startTimeOfWorkingDay());
        assertEquals(LocalTime.of(17,0,0), result.getContent().get(0).endTimeOfWorkingDay());
        assertEquals(Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY), result.getContent().get(0).workingDays());
    }

    @Test
    void delete() {
        Long providerId = 1L;

        when(providerRepository.existsById(providerId)).thenReturn(true);

        providerService.delete(providerId);

        verify(providerRepository, times(1)).deleteById(providerId);
    }

    @Test
    void update() {

        Long id = 1L;
        ProviderRequestDto requestDto = new ProviderRequestDto( "Filip Massage", "https://filip.com", "ft12", "+3816555333",
                LocalTime.of(8,0,0), LocalTime.of(17,0,0),
                Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY));

        Provider provider = new Provider("Filip Massage", "https://filip.com", "ft12", "+3816555333",
                LocalTime.of(8,0,0), LocalTime.of(17,0,0),
                Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY));

        ProviderResponseDto responseDto = new ProviderResponseDto( "Filip Massage", "https://filip.com", "ft12", "+3816555333",
                LocalTime.of(8,0,0), LocalTime.of(17,0,0),
                Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY));

        when(providerRepository.findById(id)).thenReturn(Optional.of(provider));
        when(providerMapper.toDto(provider)).thenReturn(responseDto);

        ProviderResponseDto result = providerService.update(id, requestDto);

        verify(providerRepository).findById(id);
        verify(providerMapper).update(provider, requestDto);
        verify(providerRepository).save(provider);
        verify(providerMapper).toDto(provider);
        assertEquals(responseDto, result);
    }
}