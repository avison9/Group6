package com.napier.coursework;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.napier.coursework.QueryHelper.getResultSet;



public class CityReport {


    //Method  handles report displaying
    private void createReport(Connection connection, String query) throws SQLException {
        // Create result set containing the query data
        ResultSet rset = getResultSet(connection, query);
        // Create a collection  to hold all the data for each instance of City
        ArrayList<City> cities = new ArrayList<City>();
        // Populate capitalCities with all the data for each instance of CapitalCity
        while (rset.next()) {
            City city = new City();
            city.name = rset.getString("City");
            city.country = rset.getString("Country");
            city.district = rset.getString("city.District");
            city.population = rset.getInt("city.Population");
            cities.add(city);
        }
        // Display report title
        System.out.println(String.format(" %-30s  %-30s  %-30s  %-30s ", "City", "Country", "District", "Population"));
        // Fetch each city data from the  arraylist, format them then display the data
        for (City city : cities) {
            String cityString =
                    String.format(" %-30s  %-30s  %-30s  %-30s ",
                            city.name, city.country, city.district, city.population);
            // Display report body
            System.out.println(cityString);
        }
    }

    //   All the cities in the world organised by largest population to smallest.
    public void getWorldCitiesByPopulationDesc(Connection connection) {
        try {
            String query = "SELECT city.Name AS City, country.Name AS Country, city.District, city.Population FROM city  INNER JOIN country  ON city.CountryCode = country.Code ORDER BY 4 DESC";
            //Display report topic
            System.out.println("\n All the cities in the world organised by largest population to smallest. \n");
            createReport(connection, query);
            // Handle errors
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");

        }

    }

    // All the cities in a continent organised by largest population to smallest.
    public void getContinentCitiesByPopulationDesc(Connection connection, String continent) {
        try {
            //Create the query string
            String query = "\n" +
                    "SELECT city.Name AS City, country.Name AS Country, city.District, city.Population FROM city  INNER JOIN country  ON city.CountryCode = country.Code WHERE country.Continent = '%s' ORDER BY 4 DESC, 1".formatted(continent);
            //Display report topic
            System.out.println(" \n All the cities in " + continent + " organised by largest population to smallest. \n");
            createReport(connection, query);
            // Handle errors
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");

        }
    }


    // All the cities in a region organised by largest population to smallest.
    public void getRegiondCitiesByPopulationDesc(Connection connection, String region) {
        try {
            //Create the query string
            String query = "\n" +
                    "SELECT city.Name AS City, country.Name AS Country, city.District, city.Population FROM city  INNER JOIN country  ON city.CountryCode = country.Code WHERE country.Region = '%s' ORDER BY 4 DESC, 1".formatted(region);
            //Display report topic
            System.out.println("\n All the cities in " + region + " organised by largest population to smallest.\n");
            createReport(connection, query);
            // Handle errors
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");

        }

    }

    //All the cities in a country organised by largest population to smallest.
    public void getCountryCitiesByPopulationDesc(Connection connection, String country) {
        try {
            //Create the query string
            String query = "\n" +
                    "SELECT city.Name AS City, country.Name AS Country, city.District, city.Population FROM city  INNER JOIN country  ON city.CountryCode = country.Code WHERE country.Name = '%s' ORDER BY 4 DESC, 1".formatted(country);
            //Display report topic
            System.out.println("\n All the cities in " + country + " organised by largest population to smallest.\n");
            createReport(connection, query);
            // Handle errors
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
        }
    }

    // All the cities in a district organised by largest population to smallest.
    public void getDistirctCitiesByPopulationDesc(Connection connection, String district) {
        try {
            //Create the query string
            String query = "\n" +
                    "SELECT city.Name AS City, country.Name AS Country, city.District, city.Population FROM city  INNER JOIN country  ON city.CountryCode = country.Code WHERE city.District = '%s' ORDER BY 4 DESC, 1".formatted(district);
            //Display report topic
            System.out.println("\n All the cities in " + district + " organised by largest population to smallest.\n");
            createReport(connection, query);
            // Handle errors
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
        }
    }

}