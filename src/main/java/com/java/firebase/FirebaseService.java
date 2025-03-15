//package com.java.firebase;
//
//import com.google.api.core.ApiFuture;
//import com.google.cloud.firestore.DocumentReference;
//import com.google.cloud.firestore.DocumentSnapshot;
//import com.google.cloud.firestore.Firestore;
//import com.google.firebase.cloud.FirestoreClient;
//import com.google.firebase.remoteconfig.User;
//import org.springframework.stereotype.Service;
//
//import java.util.concurrent.ExecutionException;
//
//@Service
//public class FirebaseService {
//
//    public boolean validateUser(User user) throws InterruptedException, ExecutionException {
//        Firestore dbFirestore = FirestoreClient.getFirestore();
//
//        // Fetch the document for the user from the "users" collection using the username
//        ApiFuture<DocumentSnapshot> documentSnapshotApiFuture = dbFirestore
//                .collection("users")
//                .document(user.getUsername())
//                .get();
//
//        // Wait for the result and get the DocumentSnapshot
//        DocumentSnapshot documentSnapshot = documentSnapshotApiFuture.get();
//
//        // Check if the document exists in Firestore
//        return documentSnapshot.exists();
//    }
//
//    public User getUserDetails(String uid) throws ExecutionException, InterruptedException {
//        Firestore dbFirestore = FirestoreClient.getFirestore();
//        DocumentReference documentReference = dbFirestore.collection("users").document(uid);
//        ApiFuture<DocumentSnapshot> future = documentReference.get();
//
//        DocumentSnapshot document = future.get();
//
//        User user = null;
//
//        if (document.exists()) {
//            user = document.toObject(User.class);
//            return user;
//        } else {
//            return null;
//        }
//
//    }
//
//}
//
//// trying to commit these changes
