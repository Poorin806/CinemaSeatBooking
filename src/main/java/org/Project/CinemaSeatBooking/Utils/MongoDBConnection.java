package org.Project.CinemaSeatBooking.Utils;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.github.cdimascio.dotenv.Dotenv;
import org.bson.Document;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MongoDBConnection {

    // Private Key (MongoDB URI)
    private static Dotenv dotEnv = Dotenv.load();
    private static String connectionString = dotEnv.get("MONGODB_URI");

    // MongoDB Settings
    private static final ServerApi serverApi = ServerApi.builder()
            .version(ServerApiVersion.V1)
            .build();

    private static final MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(new ConnectionString(connectionString))
            .serverApi(serverApi)
            .build();

    private static MongoClient mongoClient;
    private static MongoDatabase database;
    public MongoCollection<Document> collection;

    // Constructor
    public MongoDBConnection() {
        // Disable MongoDB log
        Logger logger = Logger.getLogger("org.mongodb.driver");
        logger.setLevel(Level.SEVERE);
    }

    public static void Connect() {
        try {

            mongoClient = MongoClients.create(settings);
            database = mongoClient.getDatabase("CinemaSeatBooking");
            database.runCommand(new Document("ping", 1));
            System.out.println("Pinged your deployment. You successfully connected to MongoDB!");

        } catch (MongoException e) {
            e.printStackTrace();
        }
    }

    public static void Disconnect() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("You successfully disconnected to MongoDB!");
        }
    }

    public void SetTable(String tableName) {
        collection = database.getCollection(tableName);
    }

}
