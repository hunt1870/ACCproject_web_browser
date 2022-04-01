package webCrawler;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HTMLToText {
	public static void saveDocument(Document document, String link) {
		try {
			if(document.body() == null) {return; }
			PrintWriter htmlWriter=new PrintWriter(new FileWriter(FilePath.HtmlFile + document.title().replace('/', '_') + ".html"));
			htmlWriter.write(document.toString());
			htmlWriter.close();
			convertHtmlToText(FilePath.HtmlFile+ document.title().replace('/', '_') + ".html", link,
					document.title().replace('/', '_').replace('!', ' ') + ".txt");
		}catch(Exception e) {
			
		}
	}
	private static void convertHtmlToText(String htmlFile, String link, String txtFile) throws Exception {
		File htmlFileData=new File(htmlFile);
		Document document=Jsoup.parse(htmlFileData,"UTF-8");
		String data=document.text().toLowerCase();
		data=link+"::"+data;
		PrintWriter writer=new PrintWriter(FilePath.textFile+txtFile);
		writer.println(data);
		writer.close();
	}
}
