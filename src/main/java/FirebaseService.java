import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class FirebaseService {

    public boolean validateUser(User user) throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        // Fetch the document for the user from the "users" collection using the username
        ApiFuture<DocumentSnapshot> documentSnapshotApiFuture = dbFirestore
                .collection("users")
                .document(user.getUsername())
                .get();

        // Wait for the result and get the DocumentSnapshot
        DocumentSnapshot documentSnapshot = documentSnapshotApiFuture.get();

        // Check if the document exists in Firestore
        return documentSnapshot.exists();
    }

}
