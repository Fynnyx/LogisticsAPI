package ch.fwesterath.logisticsapi.models.project;

import ch.fwesterath.logisticsapi.helper.Role;
import ch.fwesterath.logisticsapi.models.user.User;

public class ProjectAccessControlHelper {

    public Boolean canUserAccessProject(Project project, User user) {
        if (user.getRole() == Role.ADMIN) {
            return true;
        }
        for (User u : project.getUsers()) {
            if (u.getId().equals(user.getId())) {
                return true;
            }
        }
        return false;
    }
}
