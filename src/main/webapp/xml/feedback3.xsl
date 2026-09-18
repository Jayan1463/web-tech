<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet
    version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output
        method="html"
        indent="yes"/>

    <xsl:param name="validationLabel"/>
    <xsl:param name="xpathExpression"/>
    <xsl:param name="xpathMatches"/>

    <xsl:template match="/">

        <html>

            <head>

                <title>
                    Feedback Summary
                </title>

                <style>

                    body {
                        font-family: Arial;
                        margin: 40px;
                    }

                    table {
                        border-collapse: collapse;
                        width: 100%;
                    }

                    th, td {
                        border: 1px solid #ddd;
                        padding: 12px;
                    }

                    th {
                        background: #eeeeee;
                    }

                </style>

            </head>

            <body>

                <h1>
                    Feedback Summary
                </h1>

                <p><xsl:value-of select="$validationLabel"/></p>
                <p>XPath: <code><xsl:value-of select="$xpathExpression"/></code></p>
                <p>Ratings above 3: <strong><xsl:value-of select="$xpathMatches"/></strong></p>

                <table>

                    <tr>

                        <th>Name</th>

                        <th>Rating</th>

                        <th>Comment</th>

                    </tr>

                    <xsl:for-each
                        select="feedbacks/feedback">

                        <tr>

                            <td>
                                <xsl:value-of
                                    select="name"/>
                            </td>

                            <td>
                                <xsl:value-of
                                    select="rating"/>
                            </td>

                            <td>
                                <xsl:value-of
                                    select="comment"/>
                            </td>

                        </tr>

                    </xsl:for-each>

                </table>

            </body>

        </html>

    </xsl:template>

</xsl:stylesheet>
