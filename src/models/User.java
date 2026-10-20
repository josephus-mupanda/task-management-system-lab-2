package models;

// RegularUser and AdminUser say what they are allowed to do with canModify()
public abstract class User {

    private final String id;
    private final String name;
    private final String email;
    private final String role;

    /** Creates a user. */
    public User(String id, String name, String email, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    /** Returns true if the user can update and remove tasks and create users. */
    public abstract boolean canModify();

    /** Returns the id, ex: U001. */
    public String getId() {
        return id;
    }

    /** Returns the name. */
    public String getName() {
        return name;
    }

    /** Returns the email. */
    public String getEmail() {
        return email;
    }

    /** Returns the role, "Regular" or "Admin". */
    public String getRole() {
        return role;
    }
}
