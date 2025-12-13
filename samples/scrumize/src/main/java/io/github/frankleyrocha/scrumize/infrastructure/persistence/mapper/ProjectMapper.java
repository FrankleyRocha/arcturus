package io.github.frankleyrocha.scrumize.infrastructure.persistence.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import io.github.frankleyrocha.arcturus.common.mapper.EntityMapper;
import io.github.frankleyrocha.scrumize.infrastructure.persistence.domain.Project;
import io.github.frankleyrocha.scrumize.infrastructure.persistence.domain.Workspace;
import io.github.frankleyrocha.scrumize.infrastructure.persistence.dto.ProjectDto;
import io.github.frankleyrocha.scrumize.infrastructure.persistence.dto.WorkspaceDto;

@Mapper
public interface ProjectMapper extends EntityMapper<Project,ProjectDto>{

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    WorkspaceDto fromWorkspaceEntity(Workspace workspace);

    // @BeanMapping(ignoreByDefault = true)
    // @Mapping(target = "id", source = "id")
    Workspace toWorkspaceEntity(WorkspaceDto workspace);

}
