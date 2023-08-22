package ch.fwesterath.logisticsapi.models.project;

import ch.fwesterath.logisticsapi.error.ApiExceptionResponse;
import ch.fwesterath.logisticsapi.models.department.Department;
import ch.fwesterath.logisticsapi.models.transport.Transport;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Iterable<Project> getAllProjects() {
        try {
            return projectRepository.findAll();
        } catch (Exception e) {
            throw new ApiExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public Project getProjectById(Long id) {
        try {
            if (projectRepository.findById(id).isPresent()) {
                return projectRepository.findById(id).get();
            } else {
                throw new ApiExceptionResponse(HttpStatus.NOT_FOUND, "Project not found with id " + id.toString());
            }
        } catch (Exception e) {
            throw new ApiExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public Project getProjectByKeyname(String keyName) {
        try {
            if (projectRepository.findByKeyName(keyName).isPresent()) {
                return projectRepository.findByKeyName(keyName).get();
            } else {
                throw new ApiExceptionResponse(HttpStatus.NOT_FOUND, "Project not found with keyName " + keyName);
            }
        } catch (Exception e) {
            throw new ApiExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public Project createProject(Project project) {
        try {
            project.setCreatedAt(new Date());
            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ApiExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public Project updateProject(Project project) {
        try {
            if (projectRepository.findById(project.getId()).isPresent()) {
                return projectRepository.save(project);
            } else {
                throw new ApiExceptionResponse(HttpStatus.NOT_FOUND, "Project not found with id " + project.getId().toString());
            }
        } catch (Exception e) {
            throw new ApiExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public void deleteProject(Long id) {
        try {
            if (projectRepository.findById(id).isPresent()) {
                projectRepository.deleteById(id);
            } else {
                throw new ApiExceptionResponse(HttpStatus.NOT_FOUND, "Project not found with id " + id.toString());
            }
        } catch (Exception e) {
            throw new ApiExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

//  Transport Methods
    public Set<Transport> getAllTransports(Long projectId) {
        try {
            Optional<Project> optionalProject = projectRepository.findById(projectId);
            if (optionalProject.isPresent()) {
                return optionalProject.get().getTransports();
            } else {
                throw new ApiExceptionResponse(HttpStatus.NOT_FOUND, "Project not found with id " + projectId.toString());
            }
        } catch (Exception e) {
            throw new ApiExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

//  Department Methods
    public Set<Department> getAllDepartments(Long projectId) {
        try {
            Optional<Project> optionalProject = projectRepository.findById(projectId);
            if (optionalProject.isPresent()) {
                return optionalProject.get().getDepartments();
            } else {
                throw new ApiExceptionResponse(HttpStatus.NOT_FOUND, "Project not found with id " + projectId.toString());
            }
        } catch (Exception e) {
            throw new ApiExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
