package OfficeUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;
import java.net.URL;

public class JaxBParsing {

    public static void main(String[] args) throws Exception {
        JAXBContext jc = JAXBContext.newInstance("org.w3._2005.atom");

        Unmarshaller unmarshaller = jc.createUnmarshaller();
        URL url = new URL("http://bdoughan.blogspot.com/atom.xml");
        InputStream xml = url.openStream();
//        JAXBElement<FeedType> feed = unmarshaller.unmarshal(new StreamSource(xml), FeedType.class);
        xml.close();

        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//        marshaller.marshal(feed, System.out);
    }
}
