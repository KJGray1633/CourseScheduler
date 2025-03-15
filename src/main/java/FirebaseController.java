import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class FirebaseController {

    @Autowired
    private FirebaseService firebaseService;

    @GetMapping("/getUserDetails")
    public User getUsername(@RequestHeader String username) throws ExecutionException, InterruptedException {
        return firebaseService.getUserDetails(username);
    }
}