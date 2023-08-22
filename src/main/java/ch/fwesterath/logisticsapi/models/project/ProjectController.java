package ch.fwesterath.logisticsapi.models.project;

import ch.fwesterath.logisticsapi.error.ApiExceptionResponse;
import ch.fwesterath.logisticsapi.helper.Role;
import ch.fwesterath.logisticsapi.models.department.Department;
import ch.fwesterath.logisticsapi.models.department.DepartmentService;
import ch.fwesterath.logisticsapi.models.transport.Transport;
import ch.fwesterath.logisticsapi.models.transport.TransportService;
import ch.fwesterath.logisticsapi.models.user.User;
import ch.fwesterath.logisticsapi.models.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private TransportService transportService;

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    @CrossOrigin(origins = "*")
    public Iterable<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/byId/{id}")
    public Project getProjectById(@PathVariable("id") Long id) {

        Project project = projectService.getProjectById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication.getName());
        ProjectAccessControlHelper projectAccessControlHelper = new ProjectAccessControlHelper();
        if (projectAccessControlHelper.canUserAccessProject(project, user)) {
            return project;
        } else {
            throw new ApiExceptionResponse(HttpStatus.UNAUTHORIZED, "User is not authorized to access this project");
        }
    }

    @GetMapping("/byKeyName/{keyName}")
    public Project getProjectByKeyname(@PathVariable("keyName") String keyName) {
        return projectService.getProjectByKeyname(keyName);
    }

    @PostMapping
    public Project createProject(@RequestBody Project project) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (project.getOwner() == null || !(userService.getUserByUsername(authentication.getName()).getRole() == Role.ADMIN)) {

            User user = userService.getUserByUsername(authentication.getName());
            project.setOwner(user);
        }
        return projectService.createProject(project);
    }

    @PutMapping("/{id}")
    public Project updateProject(@RequestBody Project project, @PathVariable("id") Long id) {
        project.setId(id);
        return projectService.updateProject(project);
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable("id") Long id) {
        projectService.deleteProject(id);
    }


//  Transport Endpoints ----------------------------------------------
    @GetMapping("{id}/transports")
    public Iterable<Transport> getAllTransports(@PathVariable("id") Long id) {
        return projectService.getAllTransports(id);
    }

    @GetMapping("{id}/transports/{transportId}")
    public Transport getTransportById(@PathVariable("id") Long id, @PathVariable("transportId") Long transportId) {
        return transportService.getTransportById(transportId);
    }

    @PostMapping("{id}/transports")
    public Transport createTransport(@PathVariable("id") Long id, @RequestBody Transport transport) {
        transport.setProject(projectService.getProjectById(id));
        return transportService.createTransport(transport);
    }

    @PutMapping("{id}/transports/{transportId}")
    public Transport updateTransport(@PathVariable("id") Long id, @PathVariable("transportId") Long transportId, @RequestBody Transport transport) {
        transport.setId(transportId);
        transport.setProject(projectService.getProjectById(id));
        return transportService.updateTransport(transport);
    }

    @DeleteMapping("{id}/transports/{transportId}")
    public void deleteTransport(@PathVariable("id") Long id, @PathVariable("transportId") Long transportId) {
        transportService.deleteTransport(transportId);
    }

//  Department Endpoints ----------------------------------------------
    @GetMapping("{id}/departments")
    public Iterable<Department> getAllDepartments(@PathVariable("id") Long id) {
        return projectService.getAllDepartments(id);
    }

    @GetMapping("{id}/departments/{departmentId}")
    public Department getDepartmentById(@PathVariable("id") Long id, @PathVariable("departmentId") Long departmentId) {
        return departmentService.getDepartmentById(departmentId);
    }

    @PostMapping("{id}/departments")
    public Department createDepartment(@PathVariable("id") Long id, @RequestBody Department department) {
        department.setProject(projectService.getProjectById(id));
        return departmentService.createDepartment(department);
    }

    @PutMapping("{id}/departments/{departmentId}")
    public Department updateDepartment(@PathVariable("id") Long id, @PathVariable("departmentId") Long departmentId, @RequestBody Department department) {
        department.setId(departmentId);
        department.setProject(projectService.getProjectById(id));
        return departmentService.updateDepartment(department);
    }

    @DeleteMapping("{id}/departments/{departmentId}")
    public void deleteDepartment(@PathVariable("id") Long id, @PathVariable("departmentId") Long departmentId) {
        departmentService.deleteDepartment(departmentId);
    }

}
