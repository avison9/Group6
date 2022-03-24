package com.napier.coursework;

/*
 * The report engine outputs a selected report ID for display
 * Last update: March 23, 2022
 */


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import static com.napier.coursework.QueryHelper.getResultSet;

public class ReportEngine {

    // begin static references
    Hashtable<Integer, Integer> rID = new Hashtable<>();

    // load constants
    static int REPORT_CAPITAL_CITY = 1;
    static int REPORT_CITY = 2;
    static int REPORT_COUNTRY = 3;
    static int REPORT_LANGUAGES = 4;
    static int REPORT_POPULATION = 5;

    // sql queries
    String[] reportSQL;

    // Each query MUST correspond with the Report ID indicated on README.me
    {
        reportSQL = new String[33];
        reportSQL[1] = """
                SELECT country.Code,country.Name,country.Continent,country.Region,
                       country.Population,
                       c.name AS 'Capital'
                FROM   country
                       LEFT JOIN city c
                              ON country.capital = c.id
                ORDER  BY country.population DESC;
                """;
        reportSQL[2] = """
                SELECT country.Code,country.Name,country.Continent,country.Region,
                       country.Population,
                       c.name AS 'Capital'
                FROM   country
                       LEFT JOIN city c
                              ON country.capital = c.id
                WHERE  country.continent LIKE 'XXvarArgXX'
                ORDER  BY country.population DESC;
                """;
        reportSQL[3] = """
                SELECT country.Code,country.Name,country.Continent,country.Region,
                       country.Population,
                       c.name AS 'Capital'
                FROM   country
                       LEFT JOIN city c
                              ON country.capital = c.id
                WHERE  country.region LIKE 'XXvarArgXX'
                ORDER  BY country.population DESC;
                """;
        reportSQL[4] = """
                SELECT country.Code,country.Name,country.Continent,country.Region,
                       country.Population,
                       c.name AS 'Capital'
                FROM   country
                       LEFT JOIN city c
                              ON country.capital = c.id
                ORDER  BY country.population DESC
                LIMIT  0, YYvarLimitYY;
                """;
        reportSQL[5] = """
                SELECT country.Code,country.Name,country.Continent,country.Region,
                       country.Population,
                       c.name AS 'Capital'
                FROM   country
                       LEFT JOIN city c
                              ON country.capital = c.id
                WHERE  country.continent LIKE 'XXvarArgXX'
                ORDER  BY country.population DESC
                LIMIT  0, YYvarLimitYY;
                """;
        reportSQL[6] = """
                SELECT country.Code,country.Name,country.Continent,country.Region,
                       country.Population,
                       c.name AS 'Capital'
                FROM   country
                       LEFT JOIN city c
                              ON country.capital = c.id
                WHERE  country.Region LIKE 'XXvarArgXX'
                ORDER  BY country.population DESC
                LIMIT  0, YYvarLimitYY;
                """;
        reportSQL[7] = """
                SELECT city.Name AS City, country.Name AS Country, city.District, city.Population
                FROM city
                INNER JOIN country  ON city.CountryCode = country.Code
                ORDER BY 4 DESC;
                """;
        reportSQL[8] = """
               SELECT city.Name AS City, country.Name AS Country, city.District, city.Population
               FROM city
               INNER JOIN country  ON city.CountryCode = country.Code
               WHERE country.Continent = 'XXvarArgXX'
               ORDER BY 4 DESC, 1;
               """;
        reportSQL[9] = """
               SELECT city.Name AS City, country.Name AS Country, city.District, city.Population
               FROM city  INNER JOIN country  ON city.CountryCode = country.Code
               WHERE country.Region = 'XXvarArgXX' ORDER BY 4 DESC, 1;
               """;
        reportSQL[10] = """
               SELECT city.Name AS City, country.Name AS Country, city.District, city.Population
               FROM city  INNER JOIN country  ON city.CountryCode = country.Code
               WHERE country.Name = 'XXvarArgXX' ORDER BY 4 DESC, 1;
               """;
        reportSQL[11] = """
               SELECT city.Name AS City, country.Name AS Country, city.District, city.Population
               FROM city  INNER JOIN country  ON city.CountryCode = country.Code
               WHERE city.District = 'XXvarArgXX' ORDER BY 4 DESC, 1;
               """;
        reportSQL[12] = """
               SELECT city.Name AS City, country.Name AS Country, city.District, city.Population
               FROM city  INNER JOIN country  ON city.CountryCode = country.Code
               ORDER BY 4 DESC LIMIT 0, YYvarLimitYY;
               """;
        reportSQL[13] = """
                SELECT city.Name AS City, country.Name AS Country, city.District, city.Population
                FROM city  INNER JOIN country  ON city.CountryCode = country.Code
                WHERE country.Continent = 'XXvarArgXX'
                ORDER BY 4 DESC LIMIT 0, YYvarLimitYY;
                """;
        reportSQL[14] = """
                SELECT city.Name AS City, country.Name AS Country, city.District, city.Population
                FROM city  INNER JOIN country  ON city.CountryCode = country.Code
                WHERE country.Region = 'XXvarArgXX'
                ORDER BY 4 DESC LIMIT 0, YYvarLimitYY;
                """;
        reportSQL[15] = """
                SELECT city.Name AS City, country.Name AS Country, city.District, city.Population
                FROM city  INNER JOIN country  ON city.CountryCode = country.Code
                WHERE country.Name = 'XXvarArgXX'
                ORDER BY 4 DESC LIMIT 0, YYvarLimitYY;
                """;
        reportSQL[16] = """
                SELECT city.Name AS City, country.Name AS Country, city.District, city.Population
                FROM city  INNER JOIN country  ON city.CountryCode = country.Code
                WHERE city.District = 'XXvarArgXX'
                ORDER BY 4 DESC LIMIT YYvarLimitYY;
                """;
        reportSQL[17] = """
                SELECT b.Name as 'Name', a.Name as 'Country', b.Population as 'Population'
                FROM   country a INNER JOIN city b ON a.Capital = b.ID
                ORDER  BY 3 DESC, 1;""";
        reportSQL[18] = """
                SELECT b.Name as 'Name', a.Name as 'Country', a.Continent as 'Continent',
                       b.Population as 'Population'
                FROM   country a INNER JOIN city b ON a.Capital = b.ID ORDER BY 3, 4 DESC, 1
                ORDER  BY 3, 4 DESC, 1;""";
        reportSQL[19] = """
                SELECT b.Name as 'Name', a.Name as 'Country', a.Region as 'Region',
                       b.Population as 'Population'
                FROM   country a INNER JOIN city b ON a.Capital = b.ID
                ORDER  BY 3, 4 DESC, 1;""";
        reportSQL[20] = """
                SELECT b.Name as 'Name', a.Name as 'Country', b.Population as 'Population'
                FROM   country a INNER JOIN city b ON a.Capital = b.ID
                ORDER  BY 3 DESC, 1 LIMIT 0, YYvarLimitYY;""";
        reportSQL[21] = """
                SELECT b.Name as 'Name', a.Name as 'Country', a.Continent as 'Continent',
                       b.Population as 'Population'
                FROM   country a INNER JOIN city b ON a.Capital = b.ID
                WHERE  a.Continent = "XXvarArgXX"
                ORDER  BY 4 DESC, 1 LIMIT 0, YYvarLimitYY;""";
        reportSQL[22] = """
                SELECT b.Name as 'Name', a.Name as 'Country', a.Region as 'Region',
                       b.Population as 'Population'
                FROM   country a INNER JOIN city b ON a.Capital = b.ID
                WHERE  a.Region = "XXvarArgXX"
                ORDER  BY 4 DESC, 1 LIMIT 0, YYvarLimitYY;""";
        reportSQL[23] = """
                ORDER  BY country.population DESC;""";
        reportSQL[24] = """
                ORDER  BY country.population DESC;""";
        reportSQL[25] = """
                ORDER  BY country.population DESC;""";
        reportSQL[26] = """
                ORDER  BY country.population DESC;""";
        reportSQL[27] = """
                ORDER  BY country.population DESC;""";
        reportSQL[28] = """
                ORDER  BY country.population DESC;""";
        reportSQL[29] = """
                ORDER  BY country.population DESC;""";
        reportSQL[30] = """
                ORDER  BY country.population DESC;""";
        reportSQL[31] = """
                ORDER  BY country.population DESC;""";
        reportSQL[32] = """
                ORDER  BY country.population DESC;""";
    }

    private static final String[] reportIndex;

    static {
        reportIndex = new String[33];
        reportIndex[1] = "All the countries in the world organised by largest population to smallest";
        reportIndex[2] = "All the countries in a continent organised by largest population to smallest";
        reportIndex[3] = "All the countries in a region organised by largest population to smallest";
        reportIndex[4] = "The top YYvarLimitYY populated countries in the world";
        reportIndex[5] = "The top YYvarLimitYY populated countries in XXvarArgXX";
        reportIndex[6] = "The top YYvarLimitYY populated countries in XXvarArgXX";
        reportIndex[7] = "All the cities in the world organised by largest population to smallest.";
        reportIndex[8] = "All the cities in XXvarArgXX organised by largest population to smallest.";
        reportIndex[9] = "All the cities in XXvarArgXX organised by largest population to smallest.";
        reportIndex[10] = "All the cities in XXvarArgXX organised by largest population to smallest.";
        reportIndex[11] = "All the cities in XXvarArgXX organised by largest population to smallest.";
        reportIndex[12] = "The top YYvarLimitYY populated cities in the world";
        reportIndex[13] = "The top YYvarLimitYY populated cities in XXvarArgXX";
        reportIndex[14] = "The top YYvarLimitYY populated cities in XXvarArgXX";
        reportIndex[15] = "The top YYvarLimitYY populated cities in XXvarArgXX";
        reportIndex[16] = "The top YYvarLimitYY populated cities in XXvarArgXX";
        reportIndex[17] = "All the capital cities in the world organised by largest population to smallest.";
        reportIndex[18] = "All the capital cities in XXvarArgXX organised by largest population to smallest.";
        reportIndex[19] = "All the capital cities in XXvarArgXX organised by largest to smallest.";
        reportIndex[20] = "The top YYvarLimitYY populated capital cities in the world";
        reportIndex[21] = "The top YYvarLimitYY populated capital cities in XXvarArgXX";
        reportIndex[22] = "The top YYvarLimitYY populated capital cities in XXvarArgXX";
        reportIndex[23] = "The population of people, people living in cities, and people not living in cities in each continent.";
        reportIndex[24] = "The population of people, people living in cities, and people not living in cities in each region.";
        reportIndex[25] = "The population of people, people living in cities, and people not living in cities in each country.";
        reportIndex[26] = "The population of the world.";
        reportIndex[27] = "The population of XXvarArgXX";
        reportIndex[28] = "The population of XXvarArgXX";
        reportIndex[29] = "The population of XXvarArgXX";
        reportIndex[30] = "The population of XXvarArgXX";
        reportIndex[31] = "The population of XXvarArgXX";
        reportIndex[32] = "The number of people who speak the following the following languages from greatest number to smallest, including the percentage of the world population: Chinese, English, Hindi, Spanish, Arabic";
    }


    // begin report generator
    private String createReport(int reportID, int reportClass, String varArg, String varLimit, Connection mySQLengine) throws SQLException {

        // HTML output to return
        String htmlOutput = "";

        String tmpSQL = reportSQL[reportID];
        String tmpDesc = reportIndex[reportID];

        // let's insert our variable
        tmpSQL = tmpSQL.replaceAll("XXvarArgXX", varArg);
        tmpSQL = tmpSQL.replaceAll("YYvarLimitYY", varLimit);

        tmpDesc = tmpDesc.replaceAll("XXvarArgXX", varArg);
        tmpDesc = tmpDesc.replaceAll("YYvarLimitYY", varLimit);

        // debug only
        System.out.println("Running Report ID (" + reportID + "): (Desc) = " + tmpDesc);
        System.out.println(tmpSQL);

        // create record set
        ResultSet rSet = getResultSet(mySQLengine, tmpSQL);

        //create array list for report classes
        ArrayList<CapitalCity> capitalCityArrayList = new ArrayList<>();
        ArrayList<City> cityArrayList = new ArrayList<>();
        ArrayList<Country> countryArrayList = new ArrayList<>();
        ArrayList<Languages> languagesArrayList = new ArrayList<>();
        ArrayList<Population> populationArrayList = new ArrayList<>();

        // let's create our classes to start reporting
        switch (reportClass) {
            case 1:
                // Report class: REPORT_CAPITAL_CITY

                // cycle through the record set return from mySQL
                while (rSet.next()) {

                    // create object
                    CapitalCity aCapitalCity = new CapitalCity();

                    // set values for array item
                    aCapitalCity.setName(rSet.getString("Name"));
                    aCapitalCity.setCountry(rSet.getString("Country"));
                    aCapitalCity.setPopulation(rSet.getLong("Population"));

                    // add to record set so we can recall later
                    capitalCityArrayList.add(aCapitalCity);

                }
                break;
            case 2:
                // Report class: REPORT_CITY

                // cycle through the record set return from mySQL
                while (rSet.next()) {

                    // create object
                    City aCity = new City();

                    // set values for array item
                    aCity.setName(rSet.getString("Name"));
                    aCity.setCountry(rSet.getString("Country"));
                    aCity.setDistrict(rSet.getString("District"));
                    aCity.setPopulation(rSet.getLong("Population"));

                    // add to record set so we can recall later
                    cityArrayList.add(aCity);
                }


                break;
            case 3:
                // Report class: REPORT_COUNTRY

                // cycle through the record set return from mySQL
                while (rSet.next()) {

                    // create object
                    Country aCountry = new Country();

                    // set values for array item
                    aCountry.setCode(rSet.getString("Code"));
                    aCountry.setName(rSet.getString("Name"));
                    aCountry.setContinent(rSet.getString("Continent"));
                    aCountry.setRegion(rSet.getString("Region"));
                    aCountry.setPopulation(rSet.getLong("Population"));
                    aCountry.setCapital(rSet.getString("Capital"));

                    // add to record set so we can recall later
                    countryArrayList.add(aCountry);
                }


                break;
            case 4:
                // Report class: REPORT_LANGUAGES

                // cycle through the record set return from mySQL
                while (rSet.next()) {

                    // create object
                    Languages aLanguages = new Languages();

                    // set values for array item
                    aLanguages.setName(rSet.getString("Name"));
                    aLanguages.setLanguage(rSet.getString("Language"));
                    aLanguages.setPopulation(rSet.getLong("Population"));
                    aLanguages.setPercentage(rSet.getFloat("Percentage"));

                    // add to record set so we can recall later
                    languagesArrayList.add(aLanguages);
                }

                break;
            case 5:
                // Report class: REPORT_POPULATION

                // cycle through the record set return from mySQL
                while (rSet.next()) {

                    // create object
                    Population aPopulation = new Population();

                    // set values for array item
                    aPopulation.setName(rSet.getString("Name"));
                    aPopulation.setInCities(rSet.getLong("Name"));
                    aPopulation.setTotalPopulation(rSet.getLong("Name"));
                    aPopulation.setInCitiesPercentage(rSet.getFloat("Name"));
                    aPopulation.setNotInCities(rSet.getLong("Name"));
                    aPopulation.setNotInCitiesPercentage(rSet.getFloat("Name"));

                    // add to record set so we can recall later
                    populationArrayList.add(aPopulation);
                }

                break;

        }

        // our data is now collected, let's exploit it

        //let's create our matrix
        List<String[]> reportTable = new ArrayList<>();

        switch (reportClass) {
            case 1 -> {
                // Build table headers for the Capital City Report
                reportTable.add(new String[]{"Name", "Country", "Population"});

                // add row result
                for (CapitalCity capitalCity : capitalCityArrayList) {
                    reportTable.add(new String[]{
                            capitalCity.getName(),
                            capitalCity.getCountry(),
                            Long.toString(capitalCity.getPopulation())
                    });
                }
                htmlOutput = generateHTML(reportTable, reportID, tmpDesc, "Capital City Report");
            }
            case 2 -> {

                // table headers
                reportTable.add(new String[]{"Name", "Name"});

                // add row result
                for (City city : cityArrayList) {

                    /// this needs to be completed
                    city.getName();

                }
                htmlOutput = generateHTML(reportTable, reportID, tmpDesc, "City Report");
            }
            case 3 -> {

                // Build table headers for Country Report
                reportTable.add(new String[]{"Code", "Name", "Continent", "Region", "Population", "Capital"});

                // Add row results to array
                for (Country country : countryArrayList) {
                    reportTable.add(new String[]{
                            country.getCode(),
                            country.getName(),
                            country.getContinent(),
                            country.getRegion(),
                            Long.toString(country.getPopulation()),
                            country.getCapital()
                    });
                }

                // let's pass the table to the HTML generator
                // Ray to update HTML string output
                htmlOutput = generateHTML(reportTable, reportID, tmpDesc, "Country Report");
            }
            case 4 -> {

                // table headers
                reportTable.add(new String[]{"Name", "Name"});

                // add row result
                for (Languages languages : languagesArrayList) {
                    /// this needs to be completed
                    languages.getName();

                }
                htmlOutput = generateHTML(reportTable, reportID, tmpDesc, "Languages Report");
            }
            case 5 -> {
                // table headers
                reportTable.add(new String[]{"Name", "Name"});

                // add row result
                for (Population population : populationArrayList) {
                    /// this needs to be completed
                    population.getName();

                }
                htmlOutput = generateHTML(reportTable, reportID, tmpDesc, "Population Report");
            }
        }

        return htmlOutput;

    }


    // begin constructor
    public ReportEngine() throws SQLException {

        // define report class associations
        rID.put(1, REPORT_COUNTRY);
        rID.put(2, REPORT_COUNTRY);
        rID.put(3, REPORT_COUNTRY);
        rID.put(4, REPORT_COUNTRY);
        rID.put(5, REPORT_COUNTRY);
        rID.put(6, REPORT_COUNTRY);
        rID.put(7, REPORT_CITY);
        rID.put(8, REPORT_CITY);
        rID.put(9, REPORT_CITY);
        rID.put(10, REPORT_CITY);
        rID.put(11, REPORT_CITY);
        rID.put(12, REPORT_CITY);
        rID.put(13, REPORT_CITY);
        rID.put(14, REPORT_CITY);
        rID.put(15, REPORT_CITY);
        rID.put(16, REPORT_CITY);
        rID.put(17, REPORT_CAPITAL_CITY);
        rID.put(18, REPORT_CAPITAL_CITY);
        rID.put(19, REPORT_CAPITAL_CITY);
        rID.put(20, REPORT_CAPITAL_CITY);
        rID.put(21, REPORT_CAPITAL_CITY);
        rID.put(22, REPORT_CAPITAL_CITY);
        rID.put(23, REPORT_LANGUAGES);
        rID.put(24, REPORT_LANGUAGES);
        rID.put(25, REPORT_LANGUAGES);
        rID.put(26, REPORT_POPULATION);
        rID.put(27, REPORT_POPULATION);
        rID.put(28, REPORT_POPULATION);
        rID.put(29, REPORT_POPULATION);
        rID.put(30, REPORT_POPULATION);
        rID.put(31, REPORT_POPULATION);

    }

    public String generateReport(int reportID, String argVar, String argLimit, Connection mySQLc) throws SQLException {

        // variables
        int reportClass;

        // translate our class
        System.out.println("From ReportEngine.generateReport === reportID is : "+reportID);
        reportClass = rID.get(reportID);
        System.out.println("From ReportEngine.generateReport === reportClass is : "+reportClass);

        // create the report
        return createReport(reportID, reportClass, argVar, argLimit, mySQLc);

    }


    private String generateHTML(List<String[]> matrix, Integer reportID, String reportDesc, String reportType) {

        // Default report template
        String htmlOut =
                """
                        <!DOCTYPE html>
                        <html lang="en">
                        <head>
                            <meta charset="UTF-8">
                            <title>Report</title>
                        </head>
                        <style>
                            table {
                                border-collapse: collapse;
                                border: 1px solid;
                                font-family: sans-serif;
                            }
                                        
                            th, td {
                                border: 1px solid;
                                padding: 10px 10px;
                                text-align: center;
                            }
                        </style>
                        <body style="font-family: Arial,serif; size: 11px;">
                        XXX-header-XXX
                        XXX-desc-XXX
                        XXX-table-XXX
                        </body>
                        </html>
                        """;

        // header
        String htmlHeader = "<h2 style=\"size: 14px;\">" + reportType + "</h2>";
        // report description
        String htmlDesc = "<h3 style=\"color: #666666;\">(Report ID: " + reportID + ") " + reportDesc + "</h3>";

        //build table
        String htmlTable = """
                <table>
                  <tr>
                   YYY-headers-YYY
                  </tr>
                   YYY-rows-YYY
                </table>
                """;

        String yyyHeaders = "";
        String yyyRows = "";

        // build headers
        for (int i = 0; i < matrix.size(); i++) {
            String[] values = matrix.get(i);

            yyyRows += "<tr>\r\n";

            for (int y = 0; y < values.length; y++) {
                if (i == 0) {
                    yyyHeaders += "<th>" + values[y] + "</th>\r\n";
                } else {
                    yyyRows += "<td>" + values[y] + "</td>\r\n";
                }
            }

            yyyRows += "</tr>\r\n";

        }

        // insert data into table
        htmlTable = htmlTable.replaceAll("YYY-headers-YYY", yyyHeaders);
        htmlTable = htmlTable.replaceAll("YYY-rows-YYY", yyyRows);

        // build rows

        htmlOut = htmlOut.replaceAll("XXX-header-XXX", htmlHeader);
        htmlOut = htmlOut.replaceAll("XXX-desc-XXX", htmlDesc);
        htmlOut = htmlOut.replaceAll("XXX-table-XXX", htmlTable);

        return htmlOut;

    }


}
