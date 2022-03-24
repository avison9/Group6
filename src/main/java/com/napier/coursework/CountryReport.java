package com.napier.coursework;

/*
 * The Country report drives the functionality of a country report and outputs a selected report ID for display
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import static com.napier.coursework.QueryHelper.getResultSet;

/// begin class
public class CountryReport {

    // begin static references --->
    // reportDesc is an array of user descriptions from README.md
    private static String[] reportDesc;

    static {
        reportDesc = new String[7];
        reportDesc[1] = "All the countries in the world organised by largest population to smallest:";
        reportDesc[2] = "All the countries in a continent organised by largest population to smallest:";
        reportDesc[3] = "All the countries in a region organised by largest population to smallest:";
        reportDesc[4] = "The top N populated countries in the world where N is provided by the user:";
        reportDesc[5] = "The top N populated countries in a continent where N is provided by the user:";
        reportDesc[6] = "The top N populated countries in a region where N is provided by the user:";
    }

    // reportSQL is array of the associated SQL commands; corresponds with reportDesc
    private static String[] reportSQL;

    static {
        reportSQL = new String[7];
        reportSQL[1] = "SELECT country.Code,country.Name,country.Continent,country.Region,\n" +
                "       country.Population,\n" +
                "       c.name AS 'Capital'\n" +
                "FROM   country\n" +
                "       LEFT JOIN city c\n" +
                "              ON country.capital = c.id\n" +
                "ORDER  BY country.population DESC;";
        reportSQL[2] = "SELECT country.Code,country.Name,country.Continent,country.Region,\n" +
                "       country.Population,\n" +
                "       c.name AS 'Capital'\n" +
                "FROM   country\n" +
                "       LEFT JOIN city c\n" +
                "              ON country.capital = c.id\n" +
                "WHERE  country.continent LIKE 'North America'\n" +
                "ORDER  BY country.population DESC;";
        reportSQL[3] = "SELECT country.Code,country.Name,country.Continent,country.Region,\n" +
                "       country.Population,\n" +
                "       c.name AS 'Capital'\n" +
                "FROM   country\n" +
                "       LEFT JOIN city c\n" +
                "              ON country.capital = c.id\n" +
                "WHERE  country.region LIKE 'South America'\n" +
                "ORDER  BY country.population DESC;";
        reportSQL[4] = "SELECT country.Code,country.Name,country.Continent,country.Region,\n" +
                "       country.Population,\n" +
                "       c.name AS 'Capital'\n" +
                "FROM   country\n" +
                "       LEFT JOIN city c\n" +
                "              ON country.capital = c.id\n" +
                "ORDER  BY country.population DESC\n" +
                "LIMIT  0, 5;";
        reportSQL[5] = "SELECT country.Code,country.Name,country.Continent,country.Region,\n" +
                "       country.Population,\n" +
                "       c.name AS 'Capital'\n" +
                "FROM   country\n" +
                "       LEFT JOIN city c\n" +
                "              ON country.capital = c.id\n" +
                "WHERE  country.continent LIKE 'Asia'\n" +
                "ORDER  BY country.population DESC\n" +
                "LIMIT  0, 5;";
        reportSQL[6] = "SELECT country.Code,country.Name,country.Continent,country.Region,\n" +
                "       country.Population,\n" +
                "       c.name AS 'Capital'\n" +
                "FROM   country\n" +
                "       LEFT JOIN city c\n" +
                "              ON country.capital = c.id\n" +
                "WHERE  country.Region LIKE 'Eastern Asia'\n" +
                "ORDER  BY country.population DESC\n" +
                "LIMIT  0, 5;";
    }

    // end static references

    // begin createReport class
    private void createReport(Connection connection, String query) throws SQLException {
        ResultSet rset = getResultSet(connection, query);

        // create array reference to Country class
        ArrayList<Country> countries = new ArrayList<Country>();

        // cycle through the record set return from mySQL
        while (rset.next()) {

            // create new record
            Country aCountry = new Country();

            // set properties from columns
            aCountry.Code = rset.getString("Code");
            aCountry.Name = rset.getString("Name");
            aCountry.Continent = rset.getString("Continent");
            aCountry.Region = rset.getString("Region");
            aCountry.Population = rset.getLong("Population");
            aCountry.Capital = rset.getString("Capital");

            //add finalized record to array
            countries.add(aCountry);
        }

        // Prepare report output
        // Write header
        System.out.println(String.format(" %-30s  %-30s  %-30s  %-30s  %-30s  %-30s", "Code", "Name", "Continent", "Region","Population","Capital"));

        // Cycle through array to write each row returned
        for (Country aCountry : countries) {

            // build row output
            String countryString =
                    String.format(" %-30s  %-30s  %-30s  %-30s  %-30s  %-30s ",
                            aCountry.Code, aCountry.Name, aCountry.Continent,
                            aCountry.Region, String.valueOf(aCountry.Population),
                            aCountry.Capital
                    );
            System.out.println(countryString);
        }
    }
    // end method

    //  Method creates report
    //  ID: integer of report from README.md table
    //  Connection: the global mySQL connection to the database
    public void getReport(int ID, Connection connection) {
        {
            try {

                System.out.println("\nReport ID: " + Integer.toString(ID) + "\n" + reportDesc[ID]);
                createReport(connection, reportSQL[ID]);

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                System.out.println("Failed to get countries details");

            }
        }
    }
    // end method

}
// end class