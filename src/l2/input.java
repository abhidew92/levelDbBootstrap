package l2;
import org.iq80.leveldb.*;
import static org.iq80.leveldb.impl.Iq80DBFactory.*;
import java.io.*;
public class input {
	public static void main(String[] args) throws IOException {
		// config for database
		Options options = new Options();
		options.createIfMissing(true);
		// open/create database
		DB indexDb = factory.open(new File("indexDb"), options);
		DB coreDb = factory.open(new File("coreDb"), options);
		try {
			// populate index database
			// TODO: create modular function to populate db
			indexDb.put(bytes("1"), bytes("k1"));
			indexDb.put(bytes("3"), bytes("k3"));
			indexDb.put(bytes("5"),bytes("k5"));
			// populate core database
			coreDb.put(bytes("k1"), bytes("v1"));
			coreDb.put(bytes("k3"), bytes("v3"));
			coreDb.put(bytes("k5"), bytes("v5"));
			// get key for the coreDb
			String key = getKey(indexDb, "10");
			String value = getValue(coreDb, key);
			// display retrieved value
			System.out.println(value);
			
		} finally {
			
			//clean-up
			indexDb.close();
			coreDb.close();
		}
	}
	// returns key for the coreDb
	private static String getKey(DB indexDb, String intermediateKey) {
		String key = asString(indexDb.get(bytes(intermediateKey)));
		while (key == null) {
			intermediateKey = String.valueOf(Integer.parseInt(intermediateKey) - 1);
			key = asString(indexDb.get(bytes(intermediateKey)));
		}
		return key;
	}
	// returns desired value
	private static String getValue(DB coreDb, String key) {
		return asString(coreDb.get(bytes(key)));
	}
}