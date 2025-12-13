package io.github.frankleyrocha.scrumize.infrastructure.api.rest;

import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.frankleyrocha.arcturus.common.repository.CrudRepository;
import io.github.frankleyrocha.arcturus.common.spring.api.rest.CrudRestController;
import io.github.frankleyrocha.scrumize.infrastructure.persistence.dto.ProjectDto;

@RestController
@RequestMapping("projects")
public class ProjectRestController extends CrudRestController<ProjectDto, UUID> {

    public ProjectRestController(
            CrudRepository<ProjectDto, UUID> service) {

        super(service);
    }

}
