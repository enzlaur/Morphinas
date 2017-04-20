package Stemmer.Controller;

import Stemmer.Model.AffixModules.AffixCommand;
import Stemmer.Model.DBHandler;
import Stemmer.Model.RootSet;

import java.util.ArrayList;

import static Utility.print.*;

/**
 * Created by laurenz on 21/03/2017.
 */
public class MainController
{
	AffixCommand affixCommand = new AffixCommand();
	String inflectedWord;
	String lemma;
	String features;
	boolean processed = false;

	public MainController(){}

	/**
	 * This constructor will perform stemming on a single word
	 * @param inflectedWord
	 * inflected word to be stemmed
	 */
	public MainController(String inflectedWord)
	{
		this.inflectedWord = inflectedWord;
		createRootSet();
	}

	public void setInflectedWord(String inflectedWord) {
		this.inflectedWord = inflectedWord;
	}

	public RootSet createRootSet()
	{
		RootSet rs;
		processed 		= true;
		rs 				= affixCommand.generatePISTree3( this.getInflectedWord() );
		this.lemma	 	= rs.getLemma();
		this.features 	= rs.getFeatures();
		return rs;
	}

	public String getInflectedWord()
	{
		return inflectedWord;
	}

	public String getLemma()
	{
//		if( !processed )
//		{
//
//			createRootSet();
//		}
		return this.lemma;
	}

	public String getFeatures()
	{
		String changedWord = specialResultsFeatures( this.inflectedWord );
//		if( changedWord.equalsIgnoreCase( inflectedWord ) )
//		{
//			if( !processed )
//			{
//				createRootSet();
//			}
//		}
//		else if ( this.features.length() < 1)
//		{
//			createRootSet();
//		}
//		else
//		{
//			return changedWord;
//		}
//		createRootSet();
		return this.features;
	}


	/**
	 * Checks if word is already a root word or a punctuation mark
	 * @param specialWord
	 * @return
	 */
	public String specialResultsFeatures( String specialWord )
	{
		/* Return this */
		String result = "";
		/* Database lookup */
		DBHandler dbHandler = new DBHandler();
		if ( specialWord.length() < 4 )
		{
			return "#" + result;
		}
		else if ( dbHandler.lookup( specialWord) )
		{
			return "#" + result;
		}
		return result;
	}

	public RootSet[] performMultipleStemming(String[] words) throws Exception
	{
		/* result */
		RootSet[] rsList = new RootSet[ words.length];
		/* database for lookup */
		DBHandler dbHandler = new DBHandler();
		/* word level variables */
		String word, lowerCase, original;
		/* RootSet level variables */
		String lemma, feature = "";

		/* traverse the entire words array */
		for( int i = 0; i < words.length; i++ )
		{
			word 		= words[i];
			lowerCase	= word.toLowerCase();

			if( i == 0 )
			{
				feature = ":FS";
			}
			if( !word.equals(lowerCase) && i > 0 )
			{
				feature = ":F";
			}
			word = lowerCase;
			/*
				start stemming
			*/
			/* If the word does not start with a letter */
			if( !Character.isLetter( word.charAt(0) ) )
			{
				rsList[i] = new RootSet(word, "#"+word, word);
			}
			else
			{
				if( dbHandler.lookup(word) )
				{

				}
			}
		}

		return rsList;
	}

	/**
	 * Test only
	 */
	public static class Test
	{
		public static void main(String[] args)
		{

		}

		private void testMultipleWords()
		{
			MainController mc = new MainController();
		}

		private void testSingleWord()
		{
			MainController mc = new MainController("karamihan");
			RootSet rs = mc.createRootSet();
			println("RS Lemma: " + rs.getLemma());
			println("RS Features: " + rs.getFeatures());
			println( "MC Lemma: " + mc.getLemma() );
			println( "MC Features: " + mc.getFeatures() );
		}
	}
}
