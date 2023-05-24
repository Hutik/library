package pl.kowalewski.library.user;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCrypt;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.Table;
import pl.kowalewski.library.user.role.Role;

@Entity
@Table(name = "users")
@NamedEntityGraph(
  name = "User",
  attributeNodes = {
    @NamedAttributeNode("roles")
  })
public class User {
    @Id
    Long id;
    String firstName;
    String lastName;
    @Column(unique=true)
    String username;
    String password;
    @ManyToMany
    @JoinTable(name="users_roles")
    Set<Role> roles;
    Integer graffitiId;

    public User(){}
    
    public User(String firstName, String lastName, String userName, String password, Set<Role> roles){
        this.firstName=firstName;
        this.lastName=lastName;
        this.username=userName;
        this.password=password;
        this.roles=roles;
    }

    public User(UserDTO user){
        this.firstName=user.getFirstName();
        this.lastName=user.getLastName();
        this.username=user.getUsername();
        this.password=BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

        ArrayList<Role> rolesArray = new ArrayList<Role>();

        for(String role:user.getRole()){
            Role a = new Role();
            try{
                a.setId(Integer.decode(role));
            }catch(NumberFormatException e){
                a.setId(getRoleId(role));
            }
            rolesArray.add(a);
        }

        this.roles=Set.copyOf(rolesArray);
    }

    public Long getId() {
        return id;
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

    public void setId(Long id){
        this.id=id;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    private Integer getRoleId(String role){
        return (role.equals("ADMIN"))? 1:0;
    }

}
