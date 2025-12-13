package io.github.frankleyrocha.scrumize.infrastructure.persistence.dto;

import java.util.UUID;

public record ProjectDto(
        UUID id,
        String title,
        String description,
        WorkspaceDto workspace) {

}
