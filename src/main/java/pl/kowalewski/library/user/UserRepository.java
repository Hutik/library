package pl.kowalewski.library.user;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

public interface UserRepository extends JpaRepository<User, Long>{
    @EntityGraph(
    type = EntityGraphType.FETCH,
    attributePaths = {
      "roles"
    })
    User findByUsernameIgnoreCase(String username);
    @Query(value="SELECT MAX(id) FROM wr.users", nativeQuery=true)
    Long getMaxId();

    @EntityGraph(
    type = EntityGraphType.FETCH,
    attributePaths = {
      "roles"
    })
    List<User> findAll();
  }