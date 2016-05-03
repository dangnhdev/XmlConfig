package org.dangnh.xmlconfig;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Properties;

/**
 * @author Nguyen Hai Dang
 */
public class XmlToPropParser {
    private SAXParserFactory factory = null;

    private SAXParserFactory factory(){
        if (factory == null){
            synchronized (this){
                if (factory == null){
                    this.factory = SAXParserFactory.newInstance();
                    factory.setValidating(true);
                }
            }
        }
        return factory;
    }

    public void load(Properties props, String uri) throws IOException {
        try{
            SAXParser parser = factory().newSAXParser();
            XmlToPropsHandler handler = new XmlToPropsHandler(props);
            parser.parse(uri, handler);
        } catch (ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    private static class XmlToPropsHandler extends DefaultHandler {
        private final Properties props;
        private String path = "";
        private StringBuilder sb = null;

        public XmlToPropsHandler(Properties props){
            this.props = props;
        }

        @Override
        public void startElement(String uri, String localName, String qName,
                                 Attributes attributes) throws SAXException{
            sb = new StringBuilder();
            path = path.isEmpty() ? qName : path + "." + qName;
            for (int i = 0; i < attributes.getLength(); i++){
                String attrName = attributes.getQName(i);
                String attrValue = attributes.getValue(i);
                props.setProperty(path + "." + attrName, attrValue);
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            sb.append(new String(ch, start, length));
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            String propertyValue = sb.toString().trim();
            if (!propertyValue.isEmpty()){
                props.setProperty(path, propertyValue);
            }
        }

        @Override
        public void error(SAXParseException e) throws SAXException {
            throw e;
        }
    }
}