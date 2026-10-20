package models;

public class RegularUser extends User {

    /** Creates a regular user. */
    public RegularUser(String id, String name, String email) {
        super(id, name, email, "Regular");
    }

    /** Regular users can only view and add. */
    @Override
    public boolean canModify() {
        return false;
    }
}
