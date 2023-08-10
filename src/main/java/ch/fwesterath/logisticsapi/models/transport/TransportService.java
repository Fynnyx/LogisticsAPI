package ch.fwesterath.logisticsapi.models.transport;

import ch.fwesterath.logisticsapi.error.ApiExceptionResponse;
import ch.fwesterath.logisticsapi.models.project.Project;
import ch.fwesterath.logisticsapi.models.project.ProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class TransportService {

    @Autowired
    private TransportRepository transportRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public Iterable<Transport> getAllTransports() {
        try {
            return transportRepository.findAll();
        } catch (Exception e) {
            throw new ApiExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public Transport getTransportById(Long id) {
        try {
            Optional<Transport> optionalTransport = transportRepository.findById(id);
            if (optionalTransport.isPresent()) {
                return optionalTransport.get();
            } else {
                throw new ApiExceptionResponse(HttpStatus.NOT_FOUND, "Transport not found with id " + id);
            }
        } catch (Exception e) {
            throw new ApiExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public Transport createTransport(Transport transport) {
        try {
            // Check if the associated project exists
            Long projectId = transport.getProject().getId();
            Optional<Project> optionalProject = projectRepository.findById(projectId);
            if (!optionalProject.isPresent()) {
                throw new ApiExceptionResponse(HttpStatus.NOT_FOUND, "Associated project not found with id " + projectId);
            }

            // Save the transport
            return transportRepository.save(transport);
        } catch (Exception e) {
            throw new ApiExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public Transport updateTransport(Transport transport) {
        try {
            // Check if the transport exists
            Long transportId = transport.getId();
            Optional<Transport> optionalTransport = transportRepository.findById(transportId);
            if (!optionalTransport.isPresent()) {
                throw new ApiExceptionResponse(HttpStatus.NOT_FOUND, "Transport not found with id " + transportId);
            }

            // Check if the associated project exists
            Long projectId = transport.getProject().getId();
            Optional<Project> optionalProject = projectRepository.findById(projectId);
            if (!optionalProject.isPresent()) {
                throw new ApiExceptionResponse(HttpStatus.NOT_FOUND, "Associated project not found with id " + projectId);
            }

            // Save the updated transport
            return transportRepository.save(transport);
        } catch (Exception e) {
            throw new ApiExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public void deleteTransport(Long id) {
        try {
            Optional<Transport> optionalTransport = transportRepository.findById(id);
            if (optionalTransport.isPresent()) {
                transportRepository.deleteById(id);
            } else {
                throw new ApiExceptionResponse(HttpStatus.NOT_FOUND, "Transport not found with id " + id);
            }
        } catch (Exception e) {
            throw new ApiExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
