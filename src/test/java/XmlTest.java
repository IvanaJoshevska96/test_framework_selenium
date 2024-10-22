import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.TestBase;
import utils.TestWatcherExtension;

import static utils.xml_manipulation.XmlDuplicateChecker.checkForDuplicateField;
import static utils.xml_manipulation.XmlGuidUpdater.updateFieldInXml;

@ExtendWith({TestWatcherExtension.class})
public class XmlTest extends TestBase {
    @Test
    public void checkIfXmlContainsDuplicateGUIDs() throws Exception {
        updateFieldInXml("src/test/resources/input.xml", "src/test/resources/output.xml", "guid");
        Assertions.assertTrue(checkForDuplicateField("src/test/resources/output.xml", "guid"));//check if duplicate GUID is found
    }

    @Test
    public void checkIfXmlContainsDuplicateIDs() throws Exception {
        updateFieldInXml("src/test/resources/input.xml", "src/test/resources/output.xml", "id");
        Assertions.assertTrue(checkForDuplicateField("src/test/resources/output.xml", "id"));//check if duplicate GUID is found
    }
}
