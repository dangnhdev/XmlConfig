package org.dangnh.xmlconfig;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.Properties;
import java.util.Stack;

/**
 * @author Nguyen Hai Dang
 */
class XmlToPropsParser {
    private static volatile SAXParserFactory factory = null;

    private SAXParserFactory parserFactory(){
        if (factory == null){
            synchronized (this){
                if (factory == null){
                    factory = SAXParserFactory.newInstance();
                }
            }
        }
        return factory;
    }

    void load(Properties props, String uri) throws IOException {
        try{
            SAXParser parser = parserFactory().newSAXParser();
            XmlToPropsHandler handler = new XmlToPropsHandler(props);
            parser.parse(uri, handler);
        } catch (ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    private static class XmlToPropsHandler extends DefaultHandler {
        private final Properties props;
        private final Stack<String> paths = new Stack<>();
        private final Stack<StringBuilder> value = new Stack<>();

        XmlToPropsHandler(Properties props) {
            this.props = props;
        }

        @Override
        public void startElement(String uri, String localName, String qName,
                                 Attributes attributes) throws SAXException{
            value.push(new StringBuilder());
            String path = paths.isEmpty() ? qName : paths.peek() + "." + qName;
            paths.push(path);
            for (int i = 0; i < attributes.getLength(); i++){
                String attrName = attributes.getQName(i);
                String attrValue = attributes.getValue(i);
                props.setProperty(path + "." + attrName, attrValue);
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            value.peek().append(new String(ch, start, length));
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            String propertyValue = value.peek().toString().trim();
            if (!propertyValue.isEmpty()){
                props.setProperty(paths.peek(), propertyValue);
            }
            value.pop();
            paths.pop();
        }

        @Override
        public void error(SAXParseException e) throws SAXException {
            throw e;
        }
    }
}