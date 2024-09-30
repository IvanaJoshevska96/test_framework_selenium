package utils.xml_manipulation;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class XmlGuidUpdater {
    // Generate a valid UUID (GUID)
    public static String generateValidGuid() {
        return UUID.randomUUID().toString().toLowerCase();
    }

    // Update all "guid" fields in XML elements and attributes, making sure no duplicates are produced
    public static void updateGuidsInXml(String inputFilePath, String outputFilePath) throws Exception {
        // Set to keep track of used GUIDs to avoid duplicates
        Set<String> usedGuids = new HashSet<>();

        // Load and parse the XML file
        File inputFile = new File(inputFilePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();

        // Get all elements
        NodeList elements = doc.getElementsByTagName("*");

        // Loop through all elements
        for (int i = 0; i < elements.getLength(); i++) {
            Node element = elements.item(i);

            // Check if the element name is "guid" and update its content
            if (element.getNodeName().equalsIgnoreCase("guid")) {
                String newGuid = generateUniqueGuid(usedGuids);
                element.setTextContent(newGuid);
            }

            // Check attributes of each element for "guid"
            if (element.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) element;
                NamedNodeMap attributes = eElement.getAttributes();

                // Loop through attributes
                for (int j = 0; j < attributes.getLength(); j++) {
                    Attr attr = (Attr) attributes.item(j);

                    // Check if the attribute name is "guid"
                    if (attr.getName().equalsIgnoreCase("guid")) {
                        String newGuid = generateUniqueGuid(usedGuids);
                        attr.setValue(newGuid);
                    }
                }
            }
        }

        // Save the updated XML to a new file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(outputFilePath));
        transformer.transform(source, result);
    }

    // Helper method to generate a unique GUID that hasn't been used before
    private static String generateUniqueGuid(Set<String> usedGuids) {
        String newGuid;
        do {
            newGuid = generateValidGuid();
        } while (usedGuids.contains(newGuid));
        usedGuids.add(newGuid);  // Add new GUID to the set to track it
        return newGuid;
    }

    public static void main(String[] args) throws Exception {
        String inputFilePath = "input.xml";
        String outputFilePath = "output.xml";

        // Call the method to update all GUID fields in the XML
        updateGuidsInXml(inputFilePath, outputFilePath);
        System.out.println("GUIDs updated successfully.");
    }
}
