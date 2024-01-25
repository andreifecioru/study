package org.afecioru.study.mmc.astro.services;

import java.util.Map;

import lombok.*;

import org.afecioru.study.mmc.astro.gateways.AstroGateway;
import org.afecioru.study.mmc.astro.models.Assignment;

import static java.util.stream.Collectors.*;
import static org.afecioru.study.mmc.astro.models.AstroResponse.*;


@Data
public class AstroService {
    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private final AstroGateway gateway;

    public Map<String, Long> getAstroData() {
        var response = gateway.getResponse();

        if (response.message().equals(SUCCESS)) {
            return response.people().stream().collect(
                groupingBy(Assignment::craft, counting())
            );

        } else {
            throw new RuntimeException("Failed to contact astro service");
        }
    }
}
