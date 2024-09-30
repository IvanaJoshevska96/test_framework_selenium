package utils.xml_manipulation;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlDuplicateChecker {
    public static Map<String, List<String>> checkForDuplicateIds(String filename) throws Exception {
        Map<String, List<String>> duplicateElements = new HashMap<>();
        File inputFile = new File(filename);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();
        Map<String, List<Element>> chunks = new HashMap<>();
        NodeList nodeList = doc.getElementsByTagName("*");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                if (element.getTagName().endsWith("guid")) { //duplicate values should be detected at guid fields
                    String textContent = element.getTextContent();
                    chunks.computeIfAbsent(textContent, k -> new ArrayList<>()).add(element);
                }
            }
        }

        // Check for duplicate elements based on text content and add them to the result map
        for (Map.Entry<String, List<Element>> entry : chunks.entrySet()) {
            if (entry.getValue().size() > 1) {
                List<String> elementTags = printElem(entry.getValue());
                duplicateElements.put(entry.getKey(), elementTags);
            }
        }

        return duplicateElements;  // Return the map of duplicates
    }

    // Helper method to print element tags
    private static List<String> printElem(List<Element> elements) {
        List<String> elementTags = new ArrayList<>();
        for (Element element : elements) {
            elementTags.add("<" + element.getTagName() + ">");
        }
        return elementTags;
    }
}
