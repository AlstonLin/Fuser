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

    public void DAO()
    {

    }

    //Global variable for login status
    private boolean isLoginGood = false;
    private Connection conn;

    public boolean login(final String user, final String password)
    {
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    //Create connection to DB
                    conn = null;
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

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        return isLoginGood;
    }

    public boolean createUser(String user, String password)
    {
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
            ResultSet rset = stmt.executeQuery("Select , Password from songs WHERE Password ==" + password + " && WHERE UserName == " + user );

            while(rset.next())
            {
                String g = rset.getString(1);
                System.out.print(g);
            }
            //Close connection
        }catch(Exception e)
        {
            Log.w("Error Connecton", "" + e.getMessage());
        }
        try{
            conn.close();
        }catch (Exception e)
        {

        }
        return true;
    }

    //Wildcard on both artist and song
    public List<Song> getSong(final String term)
    {
        final List<Song> songs = new ArrayList<Song>();
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                //Create connection to DB
                conn = null;
                try{
                    String driver = "net.sourceforge.jtds.jdbc.Driver";
                    Class.forName(driver).newInstance();
                    String connString = "jdbc:jtds:sqlserver://ekwuetvgxd.database.windows.net:1433/djdb;encrypt=false;user=westernhack;password=Password1@;instance=SQLEXPRESS;";

                    String usernameSql = "westernhack";
                    String passwordSql = "Password1@";
                    conn = DriverManager.getConnection(connString, usernameSql, passwordSql);
                    Statement stmt = conn.createStatement();
                    ResultSet rset = stmt.executeQuery("Select Song, Artist, Duration, SongURL, ImageURL from Song WHERE Artist LIKE '" + term +   "' OR Song like '" + term + "'"  );

                    while(rset.next())
                    {
                        String song = rset.getString(1);
                        String artist = rset.getString(2);
                        String duration = rset.getString(3);
                        String songURL = rset.getString(4);
                        String imageURL = rset.getString(5);

                        Song s = new Song(song, duration, artist, songURL, imageURL );
                        songs.add(s);
                    }
                    //Close connection
                }catch(Exception e)
                {
                    Log.w("Error Connecton", "" + e.getMessage());
                }
            }
        });

        thread.start();
        try{
            conn.close();
        }catch (Exception e)
        {
            return songs;
        }
        return songs;
    }


}
