package com.prime.rushhour.domain.provider.service;

import com.prime.rushhour.domain.provider.dto.ProviderRequest;
import com.prime.rushhour.domain.provider.dto.ProviderResponse;
import com.prime.rushhour.domain.provider.entity.Provider;
import com.prime.rushhour.domain.provider.mapper.ProviderMapper;
import com.prime.rushhour.domain.provider.repository.ProviderRepository;
import com.prime.rushhour.infrastructure.exceptions.EntityNotFoundException;
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

        ProviderRequest providerRequest = new ProviderRequest("Filip Massage", "https://filip.com", "ft12", "+3816555333",
                LocalTime.of(8, 0, 0), LocalTime.of(17, 0, 0),
                Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY));

        Provider provider = new Provider
                ("Filip Massage", "https://filip.com", "ft12", "+3816555333",
                        LocalTime.of(8, 0, 0), LocalTime.of(17, 0, 0),
                        Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY));

        ProviderResponse providerResponse = new ProviderResponse("Filip Massage", "https://filip.com", "ft12", "+3816555333",
                LocalTime.of(8, 0, 0), LocalTime.of(17, 0, 0),
                Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY));


        when(providerMapper.toEntity(providerRequest)).thenReturn(provider);
        when(providerRepository.save(provider)).thenReturn(provider);
        when(providerMapper.toDto(provider)).thenReturn(providerResponse);

        ProviderResponse result = providerService.save(providerRequest);

        assertEquals(providerResponse, result);

        verify(providerMapper).toEntity(providerRequest);
        verify(providerRepository).save(provider);
        verify(providerMapper).toDto(provider);
    }

    @Test
    void getById() {
        Provider provider = new Provider
                ("Filip Massage", "https://filip.com", "ft12", "+3816555333",
                        LocalTime.of(8, 0, 0), LocalTime.of(17, 0, 0),
                        Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY));

        when(providerRepository.findById(1L)).thenReturn(Optional.of(provider));

        ProviderResponse providerResponse = new ProviderResponse("Filip Massage", "https://filip.com", "ft12", "+3816555333",
                LocalTime.of(8, 0, 0), LocalTime.of(17, 0, 0),
                Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY));

        when(providerMapper.toDto(provider)).thenReturn(providerResponse);

        providerResponse = providerService.getById(1L);

        assertEquals(provider.getName(), providerResponse.name());
    }

    @Test
    void getAll() {

        List<Provider> providers = Arrays.asList(
                new Provider("Filip Massage", "https://filip.com", "ft12", "+3816555333",
                        LocalTime.of(8, 0, 0), LocalTime.of(17, 0, 0),
                        Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY)),
                new Provider("John Spa", "https:/johnspa.com", "js22", "+3816555444",
                        LocalTime.of(9, 0, 0), LocalTime.of(18, 0, 0),
                        Set.of(DayOfWeek.TUESDAY, DayOfWeek.FRIDAY))
        );
        Page<Provider> page = new PageImpl<>(providers);

        when(providerRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(providerMapper.toDto(any(Provider.class))).thenReturn(new ProviderResponse(
                "Filip Massage", "https://filip.com", "ft12", "+3816555333",
                LocalTime.of(8, 0, 0), LocalTime.of(17, 0, 0),
                Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY)
        ));
        Page<ProviderResponse> result = providerService.getAll(PageRequest.of(0, 10));

        assertEquals(2, result.getContent().size());
        assertEquals("Filip Massage", result.getContent().get(0).name());
        assertEquals("https://filip.com", result.getContent().get(0).website());
        assertEquals("ft12", result.getContent().get(0).businessDomain());
        assertEquals("+3816555333", result.getContent().get(0).phone());
        assertEquals(LocalTime.of(8, 0, 0), result.getContent().get(0).startTimeOfWorkingDay());
        assertEquals(LocalTime.of(17, 0, 0), result.getContent().get(0).endTimeOfWorkingDay());
        assertEquals(Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY), result.getContent().get(0).workingDays());
    }

    @Test
    void delete() {

        Provider provider = new Provider();
        provider.setId(1L);
        providerRepository.save(provider);

        providerRepository.deleteById(provider.getId());

        verify(providerRepository, times(1)).deleteById(provider.getId());
        assertFalse(providerRepository.existsById(provider.getId()));
    }

    @Test
    void update() {

        Long providerId = 1L;
        ProviderRequest requestDto = new ProviderRequest("Filip Massage", "https://filip.com", "ft12", "+3816555333",
                LocalTime.of(8, 0), LocalTime.of(17, 0),
                Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY));

        Provider provider = new Provider("Filip Massage", "https://filip.com", "ft12", "+3816555333",
                LocalTime.of(8, 0), LocalTime.of(17, 0),
                Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY));

        ProviderResponse responseDto = new ProviderResponse("Filip Massage", "https://filip.com", "ft12", "+3816555333",
                LocalTime.of(8, 0), LocalTime.of(17, 0),
                Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY));

        when(providerRepository.findById(providerId)).thenReturn(Optional.of(provider));
        when(providerRepository.save(provider)).thenReturn(provider);
        when(providerMapper.toDto(provider)).thenReturn(responseDto);

        var result = providerService.update(providerId, requestDto);

        verify(providerRepository).findById(providerId);
        verify(providerMapper).update(provider, requestDto);
        verify(providerRepository).save(provider);
        verify(providerMapper).toDto(provider);
        assertEquals(responseDto, result);
    }

    @Test
    void getProviderById_WhenProviderDoesNotExist_ThrowsException() {
        Long providerId = 1L;

        when(providerRepository.findById(providerId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> providerService.getProviderById(providerId));
        verify(providerRepository).findById(providerId);
    }

    @Test
    void getProviderIdByAccount() {
        Long accountId = 1L;
        Long providerId = 1L;

        when(providerRepository.findProviderIdByAccountId(accountId)).thenReturn(providerId);
        Long result = providerService.getProviderIdByAccount(accountId);
        assertEquals(providerId, result);
        verify(providerRepository).findProviderIdByAccountId(accountId);
    }

    @Test
    void getProviderIdByAccount_WhenProviderDoesntExist() {
        Long accountId = 1L;

        when(providerRepository.findProviderIdByAccountId(accountId)).thenReturn(null);
        Long result = providerService.getProviderIdByAccount(accountId);
        assertNull(result);
        verify(providerRepository).findProviderIdByAccountId(accountId);
    }
}