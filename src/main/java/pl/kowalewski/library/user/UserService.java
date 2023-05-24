package pl.kowalewski.library.user;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Service
@RestController
@RequestMapping("/users")
public class UserService implements UserDetailsService {

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository repo;

    // @Autowired 
    // private TransactionTemplate transaction;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = repo.findByUsernameIgnoreCase(username.toUpperCase());
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new MyUserPrincipal(user);
    }

    // @PutMapping
    // public String saveUser(UserDTO newUser){
    //     newUser.setPassword(" ");
    //     try{
    //         registerNewUserAccount(newUser);
    //         return "user/settings";
    //     }catch(AuthenticationException ex){
    //         return "user/settings?userAuthError";
    //     }
    // }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers(){
        return ResponseEntity.ok(repo.findAll().stream().map(UserDTO::new).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Object>> getUser(@PathVariable("id") Long id){
        return ResponseEntity.ok(repo.findById(id).map(UserDTO::new));
    }

    public User registerNewUserAccount(UserDTO userDto) throws AuthenticationException{

        logger.info("password: "+userDto.getPassword()+"\nlogin: "+userDto.getUsername());

        if (usernameExist(userDto.getUsername())) {
            logger.warn("Username already exist: "+userDto.getUsername());
        }else{
            logger.info(userDto.getUsername());
            User user = new User(userDto);
            user.setPassword("Haha");
            user.setId(repo.getMaxId()+1);

            return repo.save(user);
        }

        return null;
    }

    private boolean usernameExist(String username){
        return repo.findByUsernameIgnoreCase(username) !=null;
    }

    // @GetMapping("/test")
    // ResponseEntity<HttpStatus> test(){
    //     Long id = transaction.execute(status ->  {
    //         UserDTO user = (UserDTO) getUser(1L).getBody().get();
    //         user.setUsername("Ababba");
    //         user.setPassword("Haha");
    //         Long id1 = registerNewUserAccount(user).getId();

    //         throw new NullPointerException("Hahahaha");

    //         // return id1;
    //     });

    //     return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    // }
}
