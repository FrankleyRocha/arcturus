package io.github.frankleyrocha.scrumize.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import io.github.frankleyrocha.arcturus.common.mapper.EntityMapper;
import io.github.frankleyrocha.scrumize.infrastructure.persistence.domain.Workspace;
import io.github.frankleyrocha.scrumize.infrastructure.persistence.dto.WorkspaceDto;

@Mapper
public interface WorkspaceMapper extends EntityMapper<Workspace, WorkspaceDto> {

}
