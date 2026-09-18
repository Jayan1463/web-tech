package com.grocery.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

@WebServlet("/feedback")
public class FeedbackServlet
        extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String name =
                request.getParameter("name");

        String email =
                request.getParameter("email");

        String rating =
                request.getParameter("rating");

        String category =
                request.getParameter("category");

        String comment =
                request.getParameter("comment");

        if (name == null ||
            email == null ||
            rating == null ||
            category == null ||
            comment == null) {

            response.sendRedirect(
                    "feedback.jsp"
            );

            return;
        }

        try {

            String path =
                getServletContext()
                .getRealPath(
                    "/xml/feedback5.xml"
                );

            File file =
                new File(path);

            DocumentBuilderFactory factory =
                DocumentBuilderFactory
                .newInstance();

            DocumentBuilder builder =
                factory.newDocumentBuilder();

            Document document =
                builder.parse(file);

            Element root =
                document.getDocumentElement();

            Element feedback =
                document.createElement(
                    "feedback"
                );

            feedback.setAttribute(
                "id",
                String.valueOf(
                    root.getElementsByTagName(
                        "feedback"
                    ).getLength() + 1
                )
            );

            addElement(
                document,
                feedback,
                "name",
                name
            );

            addElement(
                document,
                feedback,
                "email",
                email
            );

            addElement(
                document,
                feedback,
                "rating",
                rating
            );

            addElement(
                document,
                feedback,
                "category",
                category
            );

            addElement(
                document,
                feedback,
                "comment",
                comment
            );

            root.appendChild(feedback);

            Transformer transformer =
                TransformerFactory
                .newInstance()
                .newTransformer();

            transformer.setOutputProperty(
                OutputKeys.INDENT,
                "yes"
            );

            transformer.transform(
                new DOMSource(document),
                new StreamResult(file)
            );

            response.setContentType(
                "text/html;charset=UTF-8"
            );

            PrintWriter out =
                response.getWriter();

            out.println(
                "<html><head>" +
                "<title>Feedback Submitted</title>" +
                "<link rel='stylesheet' " +
                "href='css/style.css'>" +
                "</head><body>"
            );

            out.println(
                "<div class='confirmation'>"
            );

            out.println(
                "<div class='confirmation-card'>"
            );

            out.println(
                "<h1>Thank You!</h1>"
            );

            out.println(
                "<p>Your feedback has been recorded.</p>"
            );

            out.println(
                "<a class='btn' href='index.jsp'>" +
                "Return Home</a>"
            );

            out.println(
                "</div></div></body></html>"
            );

        } catch (Exception e) {

            throw new ServletException(e);
        }
    }


    private void addElement(
            Document document,
            Element parent,
            String name,
            String value) {

        Element element =
            document.createElement(name);

        element.appendChild(
            document.createTextNode(value)
        );

        parent.appendChild(element);
    }
}
