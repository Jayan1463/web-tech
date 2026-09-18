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
    <xsl:param name="showHighRatings" select="'false'"/>

    <xsl:template match="/">

        <html>

        <head>

            <title>
                GroceryHub Feedback Summary
            </title>

            <style>

                body {
                    font-family: Arial;
                    padding: 30px;
                }

                table {
                    width: 100%;
                    border-collapse: collapse;
                }

                th, td {
                    border: 1px solid #ddd;
                    padding: 10px;
                }

                th {
                    background: #222;
                    color: white;
                }

            </style>

        </head>

        <body>

            <h1>
                GroceryHub Feedback Summary
            </h1>

            <p><xsl:value-of select="$validationLabel"/></p>
            <p>XPath: <code><xsl:value-of select="$xpathExpression"/></code></p>
            <p>Ratings above 3: <strong><xsl:value-of select="$xpathMatches"/></strong></p>

            <p>
                Total Feedback:
                <strong>
                    <xsl:value-of
                        select="count(feedbacks/feedback)"/>
                </strong>
            </p>

            <p>
                High Rated Feedback:
                <strong>
                    <xsl:value-of
                        select="count(
                            feedbacks/feedback[rating &gt; 3]
                        )"/>
                </strong>
            </p>

            <table>

                <tr>

                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Rating</th>
                    <th>Category</th>
                    <th>Comment</th>

                </tr>

                <xsl:for-each
                    select="feedbacks/feedback[number(rating) &gt; 3 or $showHighRatings = 'false']">

                    <tr>

                        <td>
                            <xsl:value-of
                                select="@id"/>
                        </td>

                        <td>
                            <xsl:value-of
                                select="name"/>
                        </td>

                        <td>
                            <xsl:value-of
                                select="email"/>
                        </td>

                        <td>
                            <xsl:value-of
                                select="rating"/>
                        </td>

                        <td>
                            <xsl:value-of
                                select="category"/>
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
