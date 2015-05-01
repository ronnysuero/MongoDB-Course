package final_practice;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronny on 30/4/2015.
 */
public class Practice
{
	private MongoCollection<Document> collectionImage;
	private MongoCollection<Document> collectionAlbum;
	private List<Double> imagesList;

	public Practice(String database, String collectionFrom, String collectionTo)
	{
		this.collectionImage = new MongoClient().getDatabase(database).getCollection(collectionFrom);
		this.collectionAlbum = new MongoClient().getDatabase(database).getCollection(collectionTo);
	}

	public static void main(String[] args)
	{
		Practice hw = new Practice("practice", "images", "albums");
		hw.removeElements();
	}

	public void removeElements()
	{
		List<Document> dataAlbum = this.collectionAlbum.find().into(new ArrayList<>());
		List<Document> dataImage = this.collectionImage.find().into(new ArrayList<>());
		this.imagesList = new ArrayList<>();

		dataAlbum.stream().forEach(document -> this.imagesList.addAll((ArrayList<Double>) document.get("images")));

		dataImage.stream().forEach(document -> {
			if (!this.checkIfExists(document))
			{
				this.collectionImage.deleteOne(document);
				System.out.println("Me lleve a " + document);
			}
		});
	}

	private boolean checkIfExists(Document document)
	{
		return this.imagesList.stream().filter(image -> image.intValue() == document.getDouble("_id").intValue())
				       .count() > 0;
	}
}
