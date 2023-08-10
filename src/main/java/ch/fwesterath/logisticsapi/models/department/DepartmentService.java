package ch.fwesterath.logisticsapi.models.department;

import ch.fwesterath.logisticsapi.error.ApiExceptionResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public Iterable<Department> getAllDepartments() {
        try {
            return departmentRepository.findAll();
        } catch (Exception e) {
            throw new ApiExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public Department getDepartmentById(Long id) {
        try {
            Optional<Department> optionalDepartment = departmentRepository.findById(id);
            if (optionalDepartment.isPresent()) {
                return optionalDepartment.get();
            } else {
                throw new ApiExceptionResponse(HttpStatus.NOT_FOUND, "Department not found with id " + id.toString());
            }
        } catch (Exception e) {
            throw new ApiExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public Department createDepartment(Department department) {
//        try {
            return departmentRepository.save(department);
//        } catch (Exception e) {
//            throw new ApiExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
//        }
    }

    public Department updateDepartment(Department department) {
        try {
            if (departmentRepository.findById(department.getId()).isPresent()) {
                return departmentRepository.save(department);
            } else {
                throw new ApiExceptionResponse(HttpStatus.NOT_FOUND, "Department not found with id " + department.getId().toString());
            }
        } catch (Exception e) {
            throw new ApiExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public void deleteDepartment(Long id) {
        try {
            Optional<Department> optionalDepartment = departmentRepository.findById(id);
            if (optionalDepartment.isPresent()) {
                departmentRepository.deleteById(id);
            } else {
                throw new ApiExceptionResponse(HttpStatus.NOT_FOUND, "Department not found with id " + id.toString());
            }
        } catch (Exception e) {
            throw new ApiExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
