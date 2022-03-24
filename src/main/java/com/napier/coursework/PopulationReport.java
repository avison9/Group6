package com.napier.coursework;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.napier.coursework.QueryHelper.getResultSet;

public class PopulationReport
{
    /*********** Report template with percentage, population in cities and outside of cities ***********/
    private void createReport(Connection connection, String query, String property) throws SQLException {
        ResultSet rset = getResultSet(connection, query);
        ArrayList<Population> populations = new ArrayList<>();


        while (rset.next()) {
            Population population = new Population();
            population.name = rset.getString(property);
            population.totalPopulation = rset.getLong("Total Population");
            population.notInCities = rset.getLong("NOT in cities");
            population.notInCitiesPercentage = rset.getInt("NOT in cities(%)");
            population.inCities = rset.getLong("IN cities");
            population.inCitiesPercentage = rset.getInt("IN cities(%)");
            populations.add(population);
        }

        //Prints out the query
        System.out.println(String.format(" %-30s  %-30s  %-30s  %-30s  %-30s  %-30s ", "Name", "Total Population", "NOT in cities", "NOT in cities(%)", "IN cities", "IN cities(%)"));
        for (Population population : populations) {
            String populationString =
                    String.format(" %-30s  %-30s  %-30s  %-30s  %-30s  %-30s ",
                            population.name, population.totalPopulation, population.notInCities, population.notInCitiesPercentage, population.inCities, population.inCitiesPercentage);
            System.out.println(populationString);
        }
    }
    /**************************************************************************************************/


    /*********************** Report template with only population number output ***********************/
    private void createReportOnlyPopulation(Connection connection, String query, String property) throws SQLException {
        ResultSet rset = getResultSet(connection, query);
        Population population = new Population();

        while (rset.next()) {
            population.totalPopulation = rset.getLong(property);
        }
        //Prints out the number
        String populationString =
                    String.format(" %-30s ", population.totalPopulation);
            System.out.println(populationString);

    }
    /***************************************************************************************************/

//ID23
    /***** The population of people, people living in cities, and people not living in cities in each continent. ****/
    public void getPopulationPerContinent(Connection connection) {
        try {
            String query = "SELECT country.continent,\n" +
                    "(SUM(DISTINCT(country.population))) AS 'Total Population',\n" +
                    "(SUM(DISTINCT(country.population)) - SUM(city.population)) AS 'NOT in cities',\n" +
                    "((SUM(DISTINCT(country.population)) - SUM(city.population))) / (SUM(DISTINCT(country.population)))*100 AS 'NOT in cities(%)',\n" +
                    "SUM(city. population) AS 'IN cities',\n" +
                    "(((SUM(DISTINCT(city.population))) / (SUM(DISTINCT(country.population)))*100)) AS 'IN cities(%)'\n" +
                    "FROM country JOIN city ON city.countrycode = country.code\n" +
                    "GROUP by country.continent;";
            System.out.println("\n The population of people, people living in cities, and people not living in cities in each continent. \n");
            createReport(connection, query,"country.continent");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get population details");

        }
    }
    /**************************************************************************************************************/

//ID24
    /***** The population of people, people living in cities, and people not living in cities in each region. ****/
    public void getPopulationPerRegion(Connection connection) {
        try {
            String query = "SELECT country.region,\n" +
                    "(SUM(DISTINCT(country.population))) AS 'Total Population',\n" +
                    "(SUM(DISTINCT(country.population)) - SUM(city.population)) AS 'NOT in cities',\n" +
                    "((SUM(DISTINCT(country.population)) - SUM(city.population))) / (SUM(DISTINCT(country.population)))*100 AS 'NOT in cities(%)',\n" +
                    "SUM(city. population) AS 'IN cities',\n" +
                    "(((SUM(DISTINCT(city.population))) / (SUM(DISTINCT(country.population)))*100)) AS 'IN cities(%)'\n" +
                    "FROM country JOIN city ON city.countrycode = country.code\n" +
                    "GROUP by country.region;";
            System.out.println("\n The population of people, people living in cities, and people not living in cities in each region. \n");
            createReport(connection, query,"country.region");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get population details");

        }
    }
    /**************************************************************************************************************/


//ID25
    /***** The population of people, people living in cities, and people not living in cities in each country. ****/
    public void getPopulationPerCountry(Connection connection) {
        try {
            String query = "SELECT country.name,\n" +
                    "(SUM(DISTINCT(country.population))) AS 'Total Population',\n" +
                    "(SUM(DISTINCT(country.population)) - SUM(city.population)) AS 'NOT in cities',\n" +
                    "((SUM(DISTINCT(country.population)) - SUM(city.population))) / (SUM(DISTINCT(country.population)))*100 AS 'NOT in cities(%)',\n" +
                    "SUM(city. population) AS 'IN cities',\n" +
                    "(((SUM(DISTINCT(city.population))) / (SUM(DISTINCT(country.population)))*100)) AS 'IN cities(%)'\n" +
                    "FROM country JOIN city ON city.countrycode = country.code\n" +
                    "GROUP by country.name;";
            System.out.println("\n The population of people, people living in cities, and people not living in cities in each country. \n");
            createReport(connection, query,"country.name");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get population details");

        }
    }
    /**************************************************************************************************************/


//ID26
    /************ Population of the world ***************/
    public void getPopulationOfWorld(Connection connection) {
        try {
            String query = "SELECT (SUM(DISTINCT(country.population))) AS 'Total Population Of World'\n" +
                    "FROM country JOIN city ON city.countrycode = country.code;";
            System.out.println("\n The population of the world is: ");
            createReportOnlyPopulation(connection, query,"Total Population Of World");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get population details");

        }
    }
    /*****************************************************/


//ID27
    /************ Population of selected continent ***************/
    public void getPopulationOfContinent(Connection connection, String detail) {
        try {
            String query = "SELECT (SUM(DISTINCT(country.population))) AS 'Population of a continent'\n" +
                    "FROM country JOIN city ON city.countrycode = country.code WHERE country.continent = '"+ detail +"';";
            System.out.println("\n The population of " + detail + " is: ");
            createReportOnlyPopulation(connection, query,"Population of a continent");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get population details");

        }
    }
    /************************************************************/


//ID28
    /************ Population of selected region ***************/
    public void getPopulationOfRegion(Connection connection, String detail) {
        try {
            String query = "SELECT (SUM(DISTINCT(country.population))) AS 'Population of a region'\n" +
                    "FROM country JOIN city ON city.countrycode = country.code WHERE country.region = '"+ detail +"';";
            System.out.println("\n The population of " + detail + " is: ");
            createReportOnlyPopulation(connection, query,"Population of a region");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get population details");

        }
    }
    /************************************************************/
}