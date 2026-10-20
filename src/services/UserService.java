package services;

import models.AdminUser;
import models.RegularUser;
import models.User;
import utils.ValidationUtils;
import utils.exceptions.InvalidInputException;

import java.util.Arrays;

// keeps the users, in lab 1 this was done inside ConsoleMenu
public class UserService {

    private User[] users = new User[10];
    private int count;
    private int nextNumber = 1;

    /** Creates a user with the next id (U001...). Role 1 = Regular, 2 = Admin. Throws InvalidInputException if a value is not valid. */
    public User createUser(String name, String email, int roleChoice) {
        String validName = ValidationUtils.requireText(name, "Name");
        String validEmail = ValidationUtils.requireValidEmail(email);
        if (emailExists(validEmail)) {
            throw new InvalidInputException("A user with this email already exists.");
        }
        ValidationUtils.requireValidRole(roleChoice);

        String id = String.format("U%03d", nextNumber);
        User user = roleChoice == 2 ? new AdminUser(id, validName, validEmail) : new RegularUser(id, validName, validEmail);

        if (count == users.length) {
            users = Arrays.copyOf(users, users.length * 2);
        }
        users[count++] = user;
        nextNumber++;
        return user;
    }

    /** Returns true if a user already has this email (ignoring case). */
    public boolean emailExists(String email) {
        for (int i = 0; i < count; i++) {
            if (users[i].getEmail().equalsIgnoreCase(email.trim())) {
                return true;
            }
        }
        return false;
    }

    /** Finds a user from what was typed ("U001" or "1"), returns null if there is none. */
    public User findUser(String input) {
        String id = ValidationUtils.normalizeId(input, "U");
        for (int i = 0; i < count; i++) {
            if (users[i].getId().equals(id)) {
                return users[i];
            }
        }
        return null;
    }

    /** Returns all the users. */
    public User[] getUsers() {
        return Arrays.copyOf(users, count);
    }
}
