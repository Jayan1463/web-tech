package com.grocery.web;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

@WebServlet("/xml-report")
public class XmlReportServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String source = request.getParameter("source");

        ReportDefinition report = ReportDefinition.from(source);

        if (report == null) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Unknown XML report source."
            );
            return;
        }

        try {
            File xmlFile = webFile(report.xmlFile);
            Document document = report.validateWithDtd
                    ? validateDtd(xmlFile)
                    : validateSchema(xmlFile, webFile(report.schemaFile));

            XPath xpath = XPathFactory.newInstance().newXPath();
            Double matches = (Double) xpath.evaluate(
                    "count(" + report.xpathExpression + ")",
                    document,
                    XPathConstants.NUMBER
            );

            Transformer transformer = TransformerFactory.newInstance()
                    .newTransformer(
                            new javax.xml.transform.stream.StreamSource(
                                    webFile(report.xslFile)
                            )
                    );

            transformer.setParameter(
                    "validationLabel",
                    report.validationLabel
            );
            transformer.setParameter(
                    "xpathExpression",
                    report.xpathExpression
            );
            transformer.setParameter(
                    "xpathMatches",
                    String.valueOf(matches.intValue())
            );
            transformer.setParameter(
                    "showHighRatings",
                    report.showHighRatings ? "true" : "false"
            );

            response.setContentType("text/html;charset=UTF-8");
            transformer.transform(
                    new DOMSource(document),
                    new StreamResult(response.getWriter())
            );
        } catch (Exception e) {
            throw new ServletException("Unable to create XML report.", e);
        }
    }

    private File webFile(String path) {
        return new File(getServletContext().getRealPath(path));
    }

    private Document validateDtd(File xmlFile) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);

        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setErrorHandler(new ValidationErrorHandler());
        return builder.parse(xmlFile);
    }

    private Document validateSchema(File xmlFile, File schemaFile)
            throws Exception {

        Schema schema = SchemaFactory.newInstance(
                XMLConstants.W3C_XML_SCHEMA_NS_URI
        ).newSchema(schemaFile);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setSchema(schema);

        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setErrorHandler(new ValidationErrorHandler());
        return builder.parse(xmlFile);
    }

    private static class ValidationErrorHandler extends DefaultHandler {

        @Override
        public void error(org.xml.sax.SAXParseException exception)
                throws SAXException {
            throw exception;
        }

        @Override
        public void fatalError(org.xml.sax.SAXParseException exception)
                throws SAXException {
            throw exception;
        }
    }

    private static class ReportDefinition {

        private final String xmlFile;
        private final String schemaFile;
        private final String xslFile;
        private final String validationLabel;
        private final String xpathExpression;
        private final boolean validateWithDtd;
        private final boolean showHighRatings;

        private ReportDefinition(
                String xmlFile,
                String schemaFile,
                String xslFile,
                String validationLabel,
                String xpathExpression,
                boolean validateWithDtd,
                boolean showHighRatings) {

            this.xmlFile = xmlFile;
            this.schemaFile = schemaFile;
            this.xslFile = xslFile;
            this.validationLabel = validationLabel;
            this.xpathExpression = xpathExpression;
            this.validateWithDtd = validateWithDtd;
            this.showHighRatings = showHighRatings;
        }

        private static ReportDefinition from(String source) {
            if ("feedback3".equals(source)) {
                return new ReportDefinition(
                        "/xml/feedback3.xml",
                        null,
                        "/xml/feedback3.xsl",
                        "DTD validation passed for the 3-field feedback database.",
                        "/feedbacks/feedback[rating > 3]",
                        true,
                        false
                );
            }

            if ("feedback5".equals(source)) {
                return new ReportDefinition(
                        "/xml/feedback5.xml",
                        "/xml/feedback.xsd",
                        "/xml/feedback.xsl",
                        "XSD validation passed for the 5-field feedback database.",
                        "/feedbacks/feedback[rating > 3]",
                        false,
                        false
                );
            }

            if ("feedback5-high".equals(source)) {
                return new ReportDefinition(
                        "/xml/feedback5.xml",
                        "/xml/feedback.xsd",
                        "/xml/feedback.xsl",
                        "XSD validation passed. Showing only feedback with ratings above 3.",
                        "/feedbacks/feedback[rating > 3]",
                        false,
                        true
                );
            }

            if ("products".equals(source)) {
                return new ReportDefinition(
                        "/xml/products.xml",
                        "/xml/products.xsd",
                        "/xml/products.xsl",
                        "XSD validation passed for the product XML database.",
                        "/products/product[rating > 3]",
                        false,
                        false
                );
            }

            return null;
        }
    }
}
