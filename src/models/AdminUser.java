package models;

public class AdminUser extends User {

    /** Creates an admin user. */
    public AdminUser(String id, String name, String email) {
        super(id, name, email, "Admin");
    }

    /** Admins can do everything. */
    @Override
    public boolean canModify() {
        return true;
    }
}
