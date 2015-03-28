package week2;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronny on 27/3/2015.
 */
public class HomeWork2_3
{
	private MongoCollection<Document> doc;

	public HomeWork2_3(String db, String collection)
	{
		this.doc = new MongoClient().getDatabase(db).getCollection(collection);
	}

	public static void main(String[] args)
	{
		HomeWork2_3 hw = new HomeWork2_3("student", "grades");
		hw.removeElements();
	}

	public void removeElements()
	{
		List<Document> data = this.doc.find().filter(new Document("type", "homework")).sort(new Document("student_id",
		                                                                                                 1)).into(new ArrayList<>());

		for (int i = 0; i < data.size(); i += 2)
			this.doc.deleteOne((data.get(i).getDouble("score") < data.get(i + 1).getDouble("score")) ? data.get(i) :
					                   data.get(i + 1));
	}
}
