import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutionException;

@SpringBootTest
public class FirebaseControllerTest {

    @Mock
    private FirebaseService firebaseService;

    @InjectMocks
    private FirebaseController firebaseController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUsername() throws InterruptedException, ExecutionException {
        String username = "bob";
        User user = new User(0);
        user.setUsername(username);

        when(firebaseService.getUserDetails(username)).thenReturn(user);

        User result = firebaseController.getUsername(username);

        assertNotNull(result);
        assertEquals(username, result.getUsername());
        verify(firebaseService, times(1)).getUserDetails(username);
    }
}