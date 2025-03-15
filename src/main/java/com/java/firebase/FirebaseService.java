package com.java.firebase;

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

    public User getUserDetails(String uid) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("users").document(uid);
        ApiFuture<DocumentSnapshot> future = documentReference.get();

        DocumentSnapshot document = future.get();

        User user = null;

        if (document.exists()) {
            user = document.toObject(User.class);
            return user;
        } else {
            return null;
        }

    }

}

// trying to commit these changes
