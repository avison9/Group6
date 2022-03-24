package com.napier.coursework;

/*
 * SET08803 Coursework Application
 *
 */

import java.sql.*;

public class App {

    public static void main(String[] args) {

        // Connect to database
        MySQLConnection mySQLConnection = new MySQLConnection();

        Connection connection = mySQLConnection.connect();


        // Creating CityReport instance
        CityReport cityReport = new CityReport();
        // Display results for all the cities in the world organised by largest population to smallest.
        cityReport.getWorldCitiesByPopulationDesc(connection);
        // Display results for all the cities in a continent organised by largest population to smallest.
        cityReport.getContinentCitiesByPopulationDesc(connection, "Africa");
        // Display results for all the cities in a region organised by largest population to smallest.
        cityReport.getRegiondCitiesByPopulationDesc(connection, "Caribbean");
        //Display results for all the cities in a country organised by largest population to smallest.
        cityReport.getCountryCitiesByPopulationDesc(connection, "Poland");
        //Display results for all the cities in a district organised by largest population to smallest.
        cityReport.getDistirctCitiesByPopulationDesc(connection, "Zachodnio-Pomorskie");



        CapitalCityReport capitalCityReport = new CapitalCityReport();
        // Display results for all the capital cities in the world organised by largest population to smallest.
        capitalCityReport.getAllCapitalCityPopulation(connection);
        // Display results for all the capital cities in a continent organised by largest population to smallest.
        capitalCityReport.getCapitalCityPopulationByContinent(connection);
        // Display results for all the capital cities in a region organised by largest population to smallest.
        capitalCityReport.getCapitalCityPopulationByRegion(connection);
        // Display results for the top N populated capital cities in the world where N is provided by the user.
        capitalCityReport.getTopNCapitalCities(connection, 10);
        // Display results for the top N populated capital cities in a continent where N is provided by the user.
        capitalCityReport.getTopNCapitalCitiesInOneContinent(connection, 10, "Asia");
        // Display results for the top N populated capital cities in a region where N is provided by the user.
        capitalCityReport.getTopNCapitalCitiesInOneRegion(connection, 10, "Western Europe");
        // Display results for the top N populated capital cities in all continents where N is provided by the user.
        capitalCityReport.getTopNCapitalCitiesInAllContinents(connection, 10);
        // Display results for the top N populated capital cities in all global regions where N is provided by the user.
        capitalCityReport.getTopNCapitalCitiesInAllRegions(connection, 10);


        // Start of Country Series of Reports -->
        // Create new Country Report (from class)
        CountryReport countryReport = new CountryReport();

        //For now, we will use static references for N until interface is developed
        //This will cycle through Report IDs 1 to 6

        for (int x = 1; x < 7; x++) {
            // Call on getReport method to create individual report
            countryReport.getReport(x, connection);
        }
        // End of Country Series of Reports -->


        LanguagesReport languagesReport = new LanguagesReport();
        // Display results
        languagesReport.getWorldLanguagesByPopulationDesc(connection);


        PopulationReport populationReport = new PopulationReport();


        populationReport.getPopulationPerContinent(connection);
        populationReport.getPopulationPerRegion(connection);
        populationReport.getPopulationPerCountry(connection);
        populationReport.getPopulationOfWorld(connection);
        populationReport.getPopulationOfContinent(connection, "Asia");
        populationReport.getPopulationOfRegion(connection, "Baltic Countries");


//         Disconnect from database
        mySQLConnection.disconnect(connection);
    }
}