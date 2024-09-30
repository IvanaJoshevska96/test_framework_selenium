import org.junit.jupiter.api.Test;
import utils.TestBase;

import static utils.xml_manipulation.XmlDuplicateChecker.checkForDuplicateIds;
import static utils.xml_manipulation.XmlGuidUpdater.updateGuidsInXml;

public class XmlTest extends TestBase {
    @Test
    public void testXml() throws Exception {
        updateGuidsInXml("src/test/resources/input.xml", "src/test/resources/output.xml");
        checkForDuplicateIds("src/test/resources/input.xml");
    }
}
