package Stemmer;

import Stemmer.Controller.MainController;

import static Utility.print.*;
/**
 * Created by laurenz on 20/04/2017.
 */
public class Stemmer
{
	private static Stemmer instance = new Stemmer();

	private Stemmer()
	{
		println("Stemmer has been called.");
	}

	public static Stemmer getInstance()
	{
		return instance;
	}

	public String[] generateLemmaFromArray( String[] words )
	{
		MainController mc = new MainController();
		return null;
	}

}
