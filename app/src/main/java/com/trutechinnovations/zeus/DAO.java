package com.trutechinnovations.zeus;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.jtds.jdbc.*;

/**
 * Created by Cale Gibson on 3/28/2015.
 */
public class DAO {

    private static DAO instance = new DAO();

    public boolean login(final String user, final String password)
    {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        boolean isLoginGood = false;
        //Create connection to DB
        Connection conn = null;
        try{
            String driver = "net.sourceforge.jtds.jdbc.Driver";
            Class.forName(driver).newInstance();
            String connString = "jdbc:jtds:sqlserver://ekwuetvgxd.database.windows.net:1433/djdb;encrypt=false;user=westernhack;password=Password1@;instance=SQLEXPRESS;";

            String usernameSql = "westernhack";
            String passwordSql = "Password1@";
            conn = DriverManager.getConnection(connString, usernameSql, passwordSql);
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery("Select UserName, UserPassword from Users WHERE UserPassword = '" + password + "' AND UserName = '" + user + "'" );

            while(rset.next())
            {
                if(rset.getString(1) == user && rset.getString(2) == password)
                {
                    isLoginGood = true;
                    break;
                }
            }
            //Close connection
            conn.close();
        }catch(Exception e)
        {
            Log.w("Error Connecton", "" + e.getMessage());
        }

        return isLoginGood;
    }

    public boolean createUser(String user, String password)
    {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        //Create connection to DB
        Connection conn = null;
        try{
            String driver = "net.sourceforge.jtds.jdbc.Driver";
            Class.forName(driver).newInstance();
            String connString = "jdbc:jtds:sqlserver://ekwuetvgxd.database.windows.net:1433/djdb;encrypt=fasle;user=xxxxxxxxx;password=xxxxxxxx;instance=SQLEXPRESS;";

            String usernameSql = "westernhack";
            String passwordSql = "Password1@";
            conn = DriverManager.getConnection(connString, usernameSql, passwordSql);
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery("Select UserName from Users WHERE UserName = '" + user + "'");

            while(rset.next())
            {
                if(rset.getString(1) == user);
                    return false;
            }
            ResultSet rsetInsert = stmt.executeQuery("INSERT INTO Users VALUES('" + user + ',' + password +"')");
            //Close connection
        }catch(Exception e)
        {
            Log.w("Error Connecton", "" + e.getMessage());
            return false;
        }
        try{
            conn.close();
        }catch (Exception e)
        {
            return false;
        }
        return true;
    }

    //Wildcard on both artist and song
    public List<Song> getSong(final String term)    {

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        ArrayList<Song> songs = new ArrayList<Song>();
        //Create connection to DB
        Connection conn = null;
        try {
            String driver = "net.sourceforge.jtds.jdbc.Driver";
            Class.forName(driver).newInstance();
            String connString = "jdbc:jtds:sqlserver://ekwuetvgxd.database.windows.net:1433/djdb;encrypt=false;user=westernhack;password=Password1@;instance=SQLEXPRESS;";

            String usernameSql = "westernhack";
            String passwordSql = "Password1@";
            conn = DriverManager.getConnection(connString, usernameSql, passwordSql);
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery("Select Song, Artist, Duration, SongURL, ImageURL from Song WHERE Artist COLLATE SQL_Latin1_General_CP1_CI_AS LIKE '%" + term + "%' OR Song COLLATE SQL_Latin1_General_CP1_CI_AS LIKE '%" + term + "%'");

            while (rset.next()) {
                String song = rset.getString(1);
                String artist = rset.getString(2);
                String duration = rset.getString(3);
                String songURL = rset.getString(4);
                String imageURL = rset.getString(5);

                Song s = new Song(song, 0, artist, songURL, imageURL);
                songs.add(s);
            }
            //Close connection
            //Close connection
            conn.close();
        } catch (Exception e) {
            Log.w("Error Connecton", "" + e.getMessage());
        }

        return songs;
    }

    public static DAO getInstance(){
        return instance;
    }

}
