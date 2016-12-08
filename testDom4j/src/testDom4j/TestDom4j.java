package testDom4j;

import java.io.InputStream;
import java.io.StringBufferInputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class TestDom4j {
	public static void main(String[] args) {
		String infoXML = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
				+ "<!DOCTYPE xxx SYSTEM \"gcf.dtd\" ["
				+ "<!ELEMENT xxx (#PCDATA)>"
				+ "]><topology elementId=\"c9ca05f0e5e1468f83c955f464c25ae9\" time=\"\">"
				+ "  <attributes>"
				+ "    <attribute name=\"background\" value=\"\"/>"
				+ "    <attribute name=\"zoom\" value=\"1.00\"/>"
				+ "  </attributes>"
				+ "  <currentDisplay>"
				+ "    <nodes>"
				+ "      <node moEntityId=\"2427\" moEntityName=\"device1\" moTypeId=\"b601dad8e1434e0a8b7d2bfd6d638bd2\" x=\"13.00\" y=\"39.95\">"
				+ "        <attributes>"
				+ "          <attribute name=\"image\" value=\"/images/topomodel/Default/Access/BITS.png\"/>"
				+ "        </attributes>"
				+ "      </node>"
				+ "      <node moEntityId=\"2428\" moEntityName=\"device2\" moTypeId=\"b601dad8e1434e0a8b7d2bfd6d638bd2\" x=\"103.00\" y=\"39.95\">"
				+ "        <attributes>"
				+ "          <attribute name=\"image\" value=\"/images/topomodel/Default/Access/BITS.png\"/>"
				+ "        </attributes>"
				+ "      </node>"
				+ "    </nodes>"
				+ "    <links>"
				+ "      <link moEntityId=\"2426\" moEntityName=\"linka1\" moTypeId=\"015c053a16d94461bc68fecaed2d1ad6\" aNodeId=\"2427\" zNodeId=\"2428\">"
				+ "        <attributes>"
				+ "          <attribute name=\"linkStyleFlag\" value=\"RealLink\"/>"
				+ "          <attribute name=\"drillDownFlag\" value=\"NO\"/>"
				+ "          <attribute name=\"kpi1\" value=\"\"/>"
				+ "        </attributes>" + "      </link>" + "    </links>"
				+ "  </currentDisplay>" + "  <add>" + "    <nodes/>"
				+ "    <links/>" + "  </add>" + "  <delete>" + "    <nodes/>"
				+ "    <links/>" + "  </delete>" + "</topology>";
		try {
			InputStream ism = new StringBufferInputStream(infoXML);
			org.dom4j.Document document = DocumentHelper.parseText(infoXML);
			/*SAXReader sx = new SAXReader();
			sx.setValidation(false);
			sx.setIncludeExternalDTDDeclarations(false);
			sx.setIncludeInternalDTDDeclarations(false);
			sx.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false); 
			*/
			
			 /*SAXBuilder build = new SAXBuilder();
			 build.setExpandEntities(false);
			 build.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false); 
			 Document doc = null;
			 doc = (Document) build.build(ism);
			 org.jdom.Element rootEle = (org.jdom.Element) doc.getRootElement();*/
			
			XMLInputFactory factory = XMLInputFactory.newInstance();
			factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);  
			factory.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, false);
			XMLStreamReader fr = factory.createXMLStreamReader(ism);
			System.out.println(fr.getElementText());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}

