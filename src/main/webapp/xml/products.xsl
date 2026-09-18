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
                XML Product Database
            </title>

            <style>

                body {
                    font-family: Arial;
                    margin: 30px;
                }

                table {
                    border-collapse: collapse;
                    width: 100%;
                }

                th, td {
                    border: 1px solid #ccc;
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
                Product XML Database
            </h1>

            <p><xsl:value-of select="$validationLabel"/></p>
            <p>XPath: <code><xsl:value-of select="$xpathExpression"/></code></p>
            <p>Products rated above 3: <strong><xsl:value-of select="$xpathMatches"/></strong></p>

            <table>

                <tr>

                    <th>ID</th>
                    <th>Name</th>
                    <th>Category</th>
                    <th>Price</th>
                    <th>Rating</th>
                    <th>Stock</th>

                </tr>

                <xsl:for-each
                    select="products/product">

                    <tr>

                        <td>
                            <xsl:value-of select="@id"/>
                        </td>

                        <td>
                            <xsl:value-of select="name"/>
                        </td>

                        <td>
                            <xsl:value-of
                                select="category"/>
                        </td>

                        <td>
                            ₹<xsl:value-of
                                select="price"/>
                        </td>

                        <td>
                            <xsl:value-of
                                select="rating"/>
                        </td>

                        <td>
                            <xsl:value-of
                                select="stock"/>
                        </td>

                    </tr>

                </xsl:for-each>

            </table>


            <h2>
                Products Rated Above 3
            </h2>

            <ul>

                <xsl:for-each
                    select="products/product[rating &gt; 3]">

                    <li>

                        <xsl:value-of select="name"/>

                        -
                        <xsl:value-of select="rating"/>

                    </li>

                </xsl:for-each>

            </ul>

        </body>

        </html>

    </xsl:template>

</xsl:stylesheet>
