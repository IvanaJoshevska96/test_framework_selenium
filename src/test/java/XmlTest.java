import org.junit.jupiter.api.Test;
import org.testng.Assert;
import utils.TestBase;

import static utils.xml_manipulation.XmlDuplicateChecker.checkForDuplicateField;
import static utils.xml_manipulation.XmlGuidUpdater.updateFieldInXml;


public class XmlTest extends TestBase {
    @Test
    public void checkIfXmlContainsDuplicateGUIDs() throws Exception {
        updateFieldInXml("src/test/resources/input.xml", "src/test/resources/output.xml", "guid");
        Assert.assertTrue(checkForDuplicateField("src/test/resources/output.xml", "guid"));//check if duplicate GUID is found
    }

    @Test
    public void checkIfXmlContainsDuplicateIDs() throws Exception {
        updateFieldInXml("src/test/resources/input.xml", "src/test/resources/output.xml", "id");
        Assert.assertTrue(checkForDuplicateField("src/test/resources/output.xml", "id"));//check if duplicate GUID is found
    }
}
