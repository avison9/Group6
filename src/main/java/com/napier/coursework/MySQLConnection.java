package com.napier.coursework;

import java.sql.*;

public class MySQLConnection {


    // default constructor
    public Connection connect()
    {
      return   connect("jdbc:mysql://db:3306/world?useSSL=false", "root", "example");
    }

    // constructor for connection with parameters
    public Connection connect(String uri, String user, String password)
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        // Connection to the database
        Connection con = null;
        int retries = 100;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection(uri, user, password);
                System.out.println("Successfully connected");
                // Wait a bit
                Thread.sleep(10000);
                // Exit for loop
                return con;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + i);
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
        return con;
    }

    // Disconnect from the MySQL database.
    public void disconnect(Connection connection)
    {
        if (connection != null)
        {
            try
            {
                // Close connection
                connection.close();
                System.out.println("Database connection closed.");
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }


}
