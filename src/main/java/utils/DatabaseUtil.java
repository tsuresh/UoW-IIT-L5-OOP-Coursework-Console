package utils;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import controllers.Constants;
import models.Car;
import models.MotorBike;
import models.VehicleType;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DatabaseUtil {

    private static Firestore db;

    public DatabaseUtil() {
        connect();
    }

    public static boolean connect(){
        if (db == null) {
            try {
                FileInputStream serviceAccount = null;
                serviceAccount = new FileInputStream("oopcwk1-firebase-adminsdk-ueiyv-21f002366b.json");
                FirebaseOptions options = null;
                options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setDatabaseUrl("https://oopcwk1.firebaseio.com")
                        .build();
                FirebaseApp.initializeApp(options);
                db = FirestoreClient.getFirestore();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            return true;
        }
        return false;
    }

    public static boolean addData(Object data, String path){
        if (db != null) {
            ApiFuture<WriteResult> insertFuture = db.collection(path).document().set(data);
            return true;
        } else {
            return false;
        }
    }

    public static boolean addData(Object data, String path, String itemID){
        if (db != null) {
            ApiFuture<WriteResult> insertFuture = db.collection(path).document(itemID).set(data);
            return true;
        } else {
            return false;
        }
    }

    public static boolean deleteData(String path, String id){
        if (db != null) {
            ApiFuture<WriteResult> future = db.collection(path).document(id).delete();
            return true;
        } else {
            return false;
        }
    }

    public static List getCollection(String path){
        List objects = new ArrayList();
        db.collection(path).listDocuments().forEach(docRef -> {
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = null;
            try {
                document = future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            if (document.exists()) {
                if (path.equals(Constants.VEHICLES)) {
                    if (document.getData().get(Constants.TYPE).equals(VehicleType.CAR.toString())) {
                        objects.add(document.toObject(Car.class));
                    } else if (document.getData().get(Constants.TYPE).equals(VehicleType.MOTORBIKE.toString())) {
                        objects.add(document.toObject(MotorBike.class));
                    }
                } else {
                    objects.add(document.toObject(Object.class));
                }
            }
        });
        return objects;
    }

}
