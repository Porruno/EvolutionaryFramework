
package CSP.CSP;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

/**
 *
 * @author José Carlos
 */
public abstract class XmlTest {
    
    public static void readFile(String fileName) {
        int i;
        String tagName, tagValue, text;
        try {
            File fXmlFile = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            NodeList domains = doc.getElementsByTagName("domain");
            for (i = 0; i < domains.getLength(); i++) {
                Node node = domains.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {             
                    Element element = (Element) node;                    
                    tagName = element.getAttribute("name");
                    tagValue = element.getAttribute("nbValues");
                    text = element.getTextContent();
                    System.out.println("=>" + tagName + ", " + tagValue + ", " + text);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        }

    }
    
    private static String getTagValue(String tag, Element element) {
	NodeList nlList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
	return nValue.getNodeValue();
  }
    
}
