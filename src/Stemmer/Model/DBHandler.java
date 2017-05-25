package Stemmer.Model;

import Stemmer.Model.ObjectPool.JDBCConnectionPool;
import Stemmer.Model.ObjectPool.Test.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static Utility.print.println;

/**
 * Created by laurenztolentino on 02/08/2017.
 */
public class DBHandler
{
	static final String DB_URL 				= "jdbc:sqlite::resource:morphinas.db";
	// SQL Returns
	ResultSet rs;
	PreparedStatement query 				= null;
	String word;
	ArrayList<String> results;

	public DBHandler()
	{

	}

	public void createConnection()
	{
		results = new ArrayList<>();
		// instantiate the jdbcpool
		JDBCConnectionPool pool = new JDBCConnectionPool(DB_URL);
		// Get a connection:
		Connection con = pool.checkOut();
		/* Perform query */
		try
		{
			query = con.prepareStatement("select * from rootWords WHERE Word = '" +word+ "'");
			rs 	  = query.executeQuery();
			while ( rs.next() )
			{
				results.add(rs.getString(1));
			}
		}
		catch (SQLException e)
		{
			println("Hi Ma'am/Sir, may problema po sa inyong query o baka sa inyong database connection");
			println("Paki-check nalang po ang inyong DB_URL, Username, at Password at siguraduhin na ito ay tama");
			e.printStackTrace();
		}
		// Return the connection:
		pool.checkIn(con);
		pool.expire(con);
	}

	/**
	 * Performs a lookup on a word.
	 * @param word
	 * @return
	 * TRUE if the word in question is a root word and part of the database of root words.
	 * FALSE if not a root word.
	 */
	public Boolean lookup(String word)
	{
		this.word = word;
		createConnection();
		try {
			if (results.get(0) != null)
			{
				String result = results.get(0).toString();
				// well para sure huhuhuhu
				if( result.equalsIgnoreCase(word) )
					return true;
				else
					return false;
			}
		} catch (Exception e)
		{
			return false;
		}
		return false;
	}

	public ArrayList<String> getResults()
	{
		return results;
	}

	public void setResults(ArrayList<String> results) {
		this.results = results;
	}

	public static class testThis
	{
		public static void main(String args[])
		{
			DBHandler m = new DBHandler();
			println("Word: " + m.lookup("marikit"));
			println("Word: " + m.lookup("bangkay"));
			println("Word: " + m.lookup("barangay"));
			println("Word: " + m.lookup("vsdgfhdfg"));
		}
	}
}
