package week3;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ronny on 27/3/2015.
 */
public class HomeWork3_1
{
	private MongoCollection<Document> doc;

	public HomeWork3_1(String db, String collection)
	{
		this.doc = new MongoClient().getDatabase(db).getCollection(collection);
	}

	public static void main(String[] args)
	{
		HomeWork3_1 hw = new HomeWork3_1("school", "students");
		hw.removeElements();
	}

	private Document dropScores(Document document)
	{
		List<Document> homeworks = ((ArrayList<Document>) document.get("scores")).stream().filter(value -> value.get
				                                                                                                         ("type").equals("homework")).collect(Collectors.toList());
		List<Document> others = ((ArrayList<Document>) document.get("scores")).stream().filter(value -> value.get
				                                                                                                      ("type").equals("exam") || value.get("type").equals("quiz")).collect(Collectors.toList());

		others.add((homeworks.get(0).getDouble("score") > homeworks.get(1).getDouble("score")) ? homeworks.get(0) :
				           homeworks.get(1));

		return new Document("_id", document.get("_id")).append("name", document.get("name")).append("scores", others);
	}

	public void removeElements()
	{
		List<Document> data = this.doc.find().into(new ArrayList<>());
		List<Document> newData = new ArrayList<>();

		for (Document doc : data)
			newData.add(dropScores(doc));

		this.doc.drop();
		this.doc.insertMany(newData);
	}
}
