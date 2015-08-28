package l2;


import org.iq80.leveldb.*;
import static org.iq80.leveldb.impl.Iq80DBFactory.*;
import java.io.*;

public class test {
	public static void main(String[] args) throws IOException {
		Options options = new Options();
		options.createIfMissing(true);
		DB db = factory.open(new File("example"), options);
		try {
			db.put(bytes("sayWhat?!"), bytes("Gaurav is awesome!"));
			String value = asString(db.get(bytes("sayWhat?!")));
			System.out.println(value);
		} finally {
			db.close();
		}
	}
}