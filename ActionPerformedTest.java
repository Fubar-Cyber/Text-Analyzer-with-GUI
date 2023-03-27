package TextAnalyzer;

import static org.junit.Assert.*;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

public class ActionPerformedTest {

	@Test
	//Running test with the assigned URL
	public void testTheRavenUrlRead() throws IOException {
        String url = "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm";
        Document page = Jsoup.connect(url).get();
        assertNotNull(page);
	}
	
	@Test
	// Running test with other possible URL
	public void testGrimmsFairyTaleUrlRead() throws IOException {
        String url = "https://www.gutenberg.org/cache/epub/2591/pg2591-images.html#link2H_4_0020";
        Document page = Jsoup.connect(url).get();
        assertNotNull(page);
	}

}
