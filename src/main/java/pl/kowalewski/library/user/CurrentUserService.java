package pl.kowalewski.library.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequestMapping("/user")
public class CurrentUserService {
    
    Logger logger = LoggerFactory.getLogger(CurrentUserService.class);

    @GetMapping
    public ResponseEntity<Object> getCurrentUser(){
        return ResponseEntity.ok(((MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).clone());
    }

    public String getCurrentUsername(){
        return ((MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }
}
