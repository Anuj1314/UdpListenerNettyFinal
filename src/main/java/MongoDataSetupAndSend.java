
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.pojo.Messages;
import org.bson.Document;

public class MongoDataSetupAndSend {
    public String run(Messages msg){
        try{
            MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb+srv://admin:admin@cluster0-lzywi.mongodb.net/Message?retryWrites=true&w=majority"));
            MongoDatabase db = mongoClient.getDatabase("KeyManagement");
            MongoCollection collection = db.getCollection("messages");
            collection.insertOne(new Document().append("sender",msg.getSender()).append("receiver",msg.getReceiver()).append("data",msg.getData()));
        }
        catch (Exception e){
            System.out.println(e);
        }
        finally {
            return "message saved to database successfully";
        }
        }
}
