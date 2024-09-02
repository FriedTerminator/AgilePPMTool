package io.nikitacherepanov.ppmtool.services;

import io.nikitacherepanov.ppmtool.domain.Project;
import io.nikitacherepanov.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project) {

        //Logic

        return projectRepository.save(project);
    }
}
