package pl.kowalewski.library.user;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.annotation.Nonnull;
import pl.kowalewski.library.user.role.Role;

public class UserDTO {
    @Nonnull
    private String firstName;
    
    @Nonnull
    private String lastName;
    
    @Nonnull
    private String username;

    @Nonnull
    private String password;
    
    @Nonnull
    private String[] roles;

    public UserDTO(){}

    public UserDTO(User user){
        this.firstName=user.getFirstName();
        this.lastName=user.getLastName();
        this.username=user.getUsername();
        List<String> a = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        this.roles="".split("");
        this.roles=a.toArray(this.roles);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String[] getRole() {
        return roles;
    }

    public void setRole(String[] roles) {
        this.roles = roles;
    }
}
