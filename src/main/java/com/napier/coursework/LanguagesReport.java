package com.napier.coursework;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.napier.coursework.QueryHelper.getResultSet;

public class LanguagesReport {

    private void createReport(Connection connection, String query) throws SQLException {
        ResultSet rset = getResultSet(connection, query);
        ArrayList<Languages> languageslist = new ArrayList<Languages>();

        while (rset.next()) {
            Languages language = new Languages();
            language.language = rset.getString("countrylanguage.Language");
            language.population = rset.getInt("Speakers");
            language.percentage= rset.getFloat("Percentage");
            languageslist.add(language);
        }
        System.out.println(String.format(" %-30s  %-30s  %-30s  ", "Language", "Speakers", "Population"));
        for (Languages language : languageslist) {
            String languageString =
                    String.format(" %-30s  %-30s  %-30s ",
                            language.language, language.population, language.percentage);
            System.out.println(languageString);
        }
    }

    //   All the countries in the world organised by largest population to smallest.
    public void getWorldLanguagesByPopulationDesc(Connection connection) {
        {
            try {
                String query = "SELECT countrylanguage.Language, Sum(country.Population) AS Speakers, AVG(countrylanguage.Percentage) AS Percentage FROM countrylanguage  INNER JOIN country ON countrylanguage.CountryCode = country.Code\n" +
                        "        WHERE countrylanguage.Language= \"Chinese\" OR countrylanguage.Language=\"English\" OR countrylanguage.Language=\"Hindi\" OR countrylanguage.Language=\"Spanish\"\n" +
                        "        OR countrylanguage.Language=\"Arabic\" GROUP BY countrylanguage.Language ORDER BY Speakers DESC;";
                System.out.println("\n Five languages speakers from largest population to smallest. \n");
                createReport(connection, query);

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                System.out.println("Failed to get countries details");

            }
        }
    }

}