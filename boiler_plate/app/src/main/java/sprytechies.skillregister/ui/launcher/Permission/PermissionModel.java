package sprytechies.skillregister.ui.launcher.Permission;

/**
 * Created by sprydev5 on 24/10/16.
 */

public class PermissionModel {
    public String name;

    public static final String[] data = {"Education", "Work Experience", "Projects",
            "Certificates", "Contacts", "Profile"};

    PermissionModel(String name) {
        this.name = name;
    }
}
