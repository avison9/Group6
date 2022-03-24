package com.napier.coursework;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.napier.coursework.QueryHelper.getResultSet;

public class CapitalCityReport {

    private void createReport(Connection connection, String query, String reportTitle) throws SQLException {

        // Create result set containing the query data
        ResultSet resSet = getResultSet(connection, query);
        // Create a collection where to hold all the data for each instance of CapitalCity
        ArrayList<CapitalCity> capitalCities = new ArrayList<CapitalCity>();
        // Populate capitalCities with all the data for each instance of CapitalCity
        while (resSet.next())
        {
            CapitalCity capCity = new CapitalCity();
            capCity.setName(resSet.getString("Capital City"));
            capCity.setCountry(resSet.getString("Country"));
            capCity.setPopulation(resSet.getInt("Population"));
            capitalCities.add(capCity);
        }

        // Display report title
        System.out.println(reportTitle);
        // Display the column headers for the report
        System.out.println(String.format(" %-30s  %-30s  %-30s ", "Capital City", "Country", "Population"));
        // Fetch each capital city data from the capitalCities arraylist, format them then display the data
        for (CapitalCity capCity : capitalCities) {
            String capCityString =
                    String.format(" %-30s  %-30s  %-30s ",
                            capCity.getName(), capCity.getCountry(), capCity.getPopulation());
            System.out.println(capCityString);
        }
    }

    private void createReport(Connection connection, String query, String reportTitle, String groups) throws SQLException {

        // Create result set containing the query data
        ResultSet resSet = getResultSet(connection, query);
        // Create a collection where to hold all the data for each instance of CapitalCity
        ArrayList<CapitalCity> capitalCities = new ArrayList<CapitalCity>();
        // Populate capitalCities with all the data for each instance of CapitalCity
        while (resSet.next())
        {
            CapitalCity capCity = new CapitalCity();
            capCity.setName(resSet.getString("Capital City"));
            capCity.setCountry(resSet.getString("Country"));
            if (groups == "region") {
                capCity.setRegion(resSet.getString("Region"));
            } else {
                capCity.setContinent(resSet.getString("Continent"));
            }
            capCity.setPopulation(resSet.getInt("Population"));
            capitalCities.add(capCity);
        }

        // Display report and headers title if reportTitle is not an empty string
        if (reportTitle != "") {
            // Display report title
            System.out.println(reportTitle);
            // Display the column headers
            if (groups == "region") {
                System.out.println(String.format(" %-30s  %-30s  %-30s  %-30s ", "Capital City", "Country", "Region", "Population"));
            } else {
                System.out.println(String.format(" %-30s  %-30s  %-30s  %-30s ", "Capital City", "Country", "Continent", "Population"));
            }

        }

        // Fetch each capital city data from the capitalCities arraylist, format them then display the data
        String capCityString = "";
        for (CapitalCity capCity : capitalCities) {
            if (groups == "region") {
                capCityString = String.format(" %-30s  %-30s  %-30s  %-30s ",
                        capCity.getName(), capCity.getCountry(), capCity.getRegion(), capCity.getPopulation());
            } else {
                capCityString = String.format(" %-30s  %-30s  %-30s  %-30s ",
                        capCity.getName(), capCity.getCountry(), capCity.getContinent(), capCity.getPopulation());
            }
            System.out.println(capCityString);
        }
    }

    // Report #17 generating "All the capital cities in the world organised by largest population to smallest."
    public void getAllCapitalCityPopulation(Connection connection)
    {
        try {
            String reportTitle = "\n All the capital cities in the world organised by largest population to smallest. \n";
            String query = "SELECT b.Name as 'Capital City', a.Name as 'Country', b.Population as 'Population' FROM country a INNER JOIN city b ON a.Capital = b.ID ORDER BY 3 DESC, 1";
            createReport(connection, query, reportTitle);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get capital city details");
        }
    }

    // Report #18 generating "All the capital cities in a continent organised by largest population to smallest."
    public void getCapitalCityPopulationByContinent(Connection connection)
    {
        try {
            String reportTitle = "\n All the capital cities in a continent organised by largest population to smallest. \n";
            String query = "SELECT b.Name as 'Capital City', a.Name as 'Country', a.Continent as 'Continent', b.Population as 'Population' "
                            + "FROM country a INNER JOIN city b ON a.Capital = b.ID ORDER BY 3, 4 DESC, 1";
            createReport(connection, query, reportTitle, "continent");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get capital city details by continent");
        }
    }

    // Report #19 generating "All the capital cities in a region organised by largest to smallest."
    public void getCapitalCityPopulationByRegion(Connection connection)
    {
        try {
            String reportTitle = "\n All the capital cities in a region organised by largest to smallest. \n";
            String query = "SELECT b.Name as 'Capital City', a.Name as 'Country', a.Region as 'Region', b.Population as 'Population' "
                            + "FROM country a INNER JOIN city b ON a.Capital = b.ID ORDER BY 3, 4 DESC, 1";
            createReport(connection, query, reportTitle, "region");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get capital city details by region");
        }
    }

    // Report #20 generating "The top N populated capital cities in the world where N is provided by the user."
    public void getTopNCapitalCities(Connection connection, int topNCities)
    {
        try {
            String reportTitle = "\n The top " + topNCities + " populated capital cities in the world. \n";
            String query = "SELECT b.Name as 'Capital City', a.Name as 'Country', b.Population as 'Population' "
                            + "FROM country a INNER JOIN city b ON a.Capital = b.ID ORDER BY 3 DESC, 1 LIMIT " + topNCities;
            createReport(connection, query, reportTitle);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get capital city details");
        }
    }

    // Report #21 generating "the top N populated capital cities in a continent where N is provided by the user."
    public void getTopNCapitalCitiesInOneContinent(Connection connection, int topNCities, String continentStr)
    {
        try {
            String reportTitle = "\n The top " + topNCities + " populated capital cities in " + continentStr + ". \n";
            String query = "SELECT b.Name as 'Capital City', a.Name as 'Country', a.Continent as 'Continent', b.Population as 'Population' "
                            + "FROM country a INNER JOIN city b ON a.Capital = b.ID WHERE a.Continent = \"" + continentStr + "\" ORDER BY 4 DESC, 1 LIMIT 10";
            createReport(connection, query, reportTitle,"continent");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get capital city details");
        }
    }

    // Report #22 generating "the top N populated capital cities in a region where N is provided by the user."
    public void getTopNCapitalCitiesInOneRegion(Connection connection, int topNCities, String regionStr)
    {
        try {
            String reportTitle = "\n The top " + topNCities + " populated capital cities in " + regionStr + ". \n";
            String query = "SELECT b.Name as 'Capital City', a.Name as 'Country', a.Region as 'Region', b.Population as 'Population' "
                    + "FROM country a INNER JOIN city b ON a.Capital = b.ID WHERE a.Region = \"" + regionStr + "\" ORDER BY 4 DESC, 1 LIMIT 10";
            createReport(connection, query, reportTitle,"region");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get capital city details");
        }
    }

    // Extra report generating "the top N populated capital cities in all continents where N is provided by the user."
    public void getTopNCapitalCitiesInAllContinents(Connection connection, int topNCities)
    {
        try {
            ArrayList<String> continents = new ArrayList<String>();
            continents.addAll(getDistinctGroups(connection,"continent"));
            String reportTitle = "\n The top " + topNCities + " populated capital cities in all continents. \n";
            String query = "SELECT b.Name as 'Capital City', a.Name as 'Country', a.Continent as 'Continent', b.Population as 'Population'  "
                    + "FROM country a INNER JOIN city b ON a.Capital = b.ID WHERE a.Continent = \"" + continents.get(0) + "\" ORDER BY 4 DESC, 1 LIMIT 10";
            createReport(connection, query, reportTitle,"continent");
            for (int i = 1; i < continents.size(); i++) {
                query = "SELECT b.Name as 'Capital City', a.Name as 'Country', a.Continent as 'Continent', b.Population as 'Population'  "
                        + "FROM country a INNER JOIN city b ON a.Capital = b.ID WHERE a.Continent = \"" + continents.get(i) + "\" ORDER BY 4 DESC, 1 LIMIT 10";
                createReport(connection, query, "", "continent");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get capital city data per continent");
        }
    }

    // Extra report generating "the top N populated capital cities in all global regions where N is provided by the user."
    public void getTopNCapitalCitiesInAllRegions(Connection connection, int topNCities)
    {
        try {
            ArrayList<String> regions = new ArrayList<String>();
            regions.addAll(getDistinctGroups(connection,"region"));
            String reportTitle = "\n The top " + topNCities + " populated capital cities in all global regions. \n";
            String query = "SELECT b.Name as 'Capital City', a.Name as 'Country', a.Region as 'Region', b.Population as 'Population' "
                    + "FROM country a INNER JOIN city b ON a.Capital = b.ID WHERE a.Region = \"" + regions.get(0) + "\" ORDER BY 4 DESC, 1 LIMIT 10";
            createReport(connection, query, reportTitle, "region");
            for (int i = 1; i < regions.size(); i++) {
                query = "SELECT b.Name as 'Capital City', a.Name as 'Country', a.Region as 'Region', b.Population as 'Population' "
                        + "FROM country a INNER JOIN city b ON a.Capital = b.ID WHERE a.Region = \"" + regions.get(i) + "\" ORDER BY 4 DESC, 1 LIMIT 10";
                createReport(connection, query, "", "region");

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get capital city data per region");
        }
    }

    public ArrayList<String> getDistinctGroups(Connection connection, String groupStr) {

        try {
            //Create the select statement
            String query = "SELECT DISTINCT " + groupStr + " FROM country ORDER BY 1";
            // Create result set containing the query data
            ResultSet resSet = getResultSet(connection, query);
            // Create a collection where to hold all the data for each instance of CapitalCity
            ArrayList<String> groups = new ArrayList<String>();
            // Populate capitalCities with all the data for each instance of CapitalCity
            while (resSet.next())
            {
                groups.add(resSet.getString(groupStr));
            }

            return groups;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get " + groupStr);
        }

        return null;

    }

}
