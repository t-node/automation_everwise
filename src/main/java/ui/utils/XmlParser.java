package ui.utils;

//import com.sun.tools.corba.se.idl.StringGen;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ui.BrowserElementImpl;
import ui.support.Config;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Random;

/**
 * Created by vevinmoza on 3/18/16.
 */
public class XmlParser {
    private static final Logger logger = Logger.getLogger(XmlParser.class);
    static Document doc;

    public XmlParser() throws Exception{

        File fXmlFile = new File("src//test//resources//testData//"+ Config.getBanner()+"//data.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        doc = dBuilder.parse(fXmlFile);
    }

    public static  String getTagName(String tagName,int index){
        return doc.getElementsByTagName(tagName).item(index).getTextContent();
    }

    public static  String getAttributeForTag(String tagName,int index,String attribute){
        return doc.getElementsByTagName(tagName).item(index).getAttributes().getNamedItem(attribute).getTextContent();
    }

    public static String getValue(String tag){
        return doc.getElementsByTagName(tag).item(0).getTextContent();
    }

    public static String getItemIn(String tagName){
        int eCount = doc.getElementsByTagName(tagName).getLength();
        int r = new Random().nextInt(eCount);
        return doc.getElementsByTagName(tagName).item(r).getTextContent();
    }
}
