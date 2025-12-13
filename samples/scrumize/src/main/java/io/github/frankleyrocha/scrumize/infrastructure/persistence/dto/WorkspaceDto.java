package io.github.frankleyrocha.scrumize.infrastructure.persistence.dto;

import java.util.UUID;

public record WorkspaceDto(
        UUID id,
        String title,
        String description) {

}
