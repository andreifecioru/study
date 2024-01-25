package org.afecioru.study.mmc.astro;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.afecioru.study.mmc.astro.models.Assignment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.afecioru.study.mmc.astro.models.AstroResponse;
import org.afecioru.study.mmc.astro.services.AstroService;
import org.afecioru.study.mmc.astro.gateways.AstroGateway;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class AstroServiceTest {
    @Mock
    private AstroGateway gateway;

    @InjectMocks
    private AstroService service;

    @Test
    @DisplayName("Astro remote REST service responds with failure")
    void testGatewayFailureResponse() {
        // Arrange
        when(gateway.getResponse()).thenReturn(new AstroResponse(
            1, AstroResponse.FAILURE, Collections.emptyList()
        ));

        // Act
        var action = assertThatThrownBy(() -> service.getAstroData());

        // Assert
        verify(gateway).getResponse();

        action
            .isInstanceOf(RuntimeException.class)
            .hasMessage("Failed to contact astro service");
    }

    @Test
    @DisplayName("Astro remote REST service responds with success")
    void testGatewaySuccessResponse() {
        // Arrange
        when(gateway.getResponse()).thenReturn(new AstroResponse(
            1,
            AstroResponse.SUCCESS,
            List.of(
                new Assignment("Person One", "Station One"),
                new Assignment("Person Two", "Station One"),
                new Assignment("Person Three", "Station One"),
                new Assignment("Person Four", "Station Two"),
                new Assignment("Person Five", "Station Two")
            )
        ));

        var expected = Map.of("Station One", 3L, "Station Two", 2L);

        // Act
        var actual = service.getAstroData();

        // Assert
        verify(gateway).getResponse();

        assertThat(actual).containsAllEntriesOf(expected);

        actual.forEach((craft, number) ->
            assertAll(
                () -> assertThat(craft).isNotBlank(),
                () -> assertThat(number).isPositive()
            )
        );
    }
}
