package cm.socialfitnesshub.Database;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Database {

    private final static Database instance = new Database();

    private final FirebaseDatabase db = FirebaseDatabase.getInstance();
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    private Database() {

    }

    public static Database getInstance() {
        return instance;
    }

    /**
     * Returns the current user id
     *
     * @return a string with the current user id
     */
    public String getCurrentUserId() {
        return auth.getCurrentUser().getUid();
    }

    /**
     * Returns the DatabaseReference with the provided path.
     *
     * @param path database path to the reference
     * @return DatabaseReference with the desired path
     */
    public DatabaseReference getDatabaseReference(String path) {
        return db.getReference(path);
    }


}
