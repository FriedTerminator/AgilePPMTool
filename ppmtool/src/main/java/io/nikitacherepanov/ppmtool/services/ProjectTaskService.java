package io.nikitacherepanov.ppmtool.services;

import io.nikitacherepanov.ppmtool.domain.Backlog;
import io.nikitacherepanov.ppmtool.domain.Project;
import io.nikitacherepanov.ppmtool.domain.ProjectTask;
import io.nikitacherepanov.ppmtool.exceptions.ProjectIdException;
import io.nikitacherepanov.ppmtool.exceptions.ProjectNotFoundException;
import io.nikitacherepanov.ppmtool.exceptions.ProjectNotFoundExceptionResponse;
import io.nikitacherepanov.ppmtool.repositories.BacklogRepository;
import io.nikitacherepanov.ppmtool.repositories.ProjectRepository;
import io.nikitacherepanov.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {

        try {
            //PTs to be added to a specific project, project != null, BL exists
            Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
            //set the backlog to pt
            projectTask.setBacklog(backlog);
            //Project sequence will be like this: IDPRO-1   IDPRO-2 ...100 101
            Integer BacklogSequence = backlog.getPTSequence();
            //Update the BL Sequence
            BacklogSequence++;

            backlog.setPTSequence(BacklogSequence);

            //Add sequence to project task
            projectTask.setProjectSequence(projectIdentifier + "-" + BacklogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);

            //Initial Priority when priority null
            if (projectTask.getPriority() == null) { // In the future will need projectTask.getPriority() == 0
                projectTask.setPriority(3);
            }

            //Initial Status when status is null
            if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
                projectTask.setStatus("TO_DO");
            }

            return projectTaskRepository.save(projectTask);
        } catch(Exception e) {
            throw new ProjectNotFoundException("Project not Found");
        }
    }

    public Iterable<ProjectTask> findBacklogById(String backlogId) {

        Project project = projectRepository.findByProjectIdentifier(backlogId);

        if(project == null) {
            throw new ProjectNotFoundException("Project with ID: '" + backlogId + "' does not exist");
        }

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlogId);
    }
}
