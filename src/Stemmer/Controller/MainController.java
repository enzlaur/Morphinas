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
	String originalWord;
	boolean processed = false;
	/* Array of Lemmas and Features */
	String[] lemmasArray;
	String[] featuresArray;
	RootSet[] rootSetArray;

	public MainController(){}

	public MainController( String[] words )
	{
		rootSetArray = this.performMultipleStemming(words);
	}

	/**
	 * This constructor will perform stemming on a single word
	 * @param inflectedWord
	 * inflected word to be stemmed
	 */
	public MainController(String inflectedWord)
	{
		this.inflectedWord = inflectedWord;
		this.originalWord = inflectedWord;
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

	public RootSet createRootSet(String word)
	{
		return affixCommand.generatePISTree3(word);
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
		if( this.lemma == null || this.lemma.equalsIgnoreCase("") )
		{
			this.lemma = this.originalWord;
		}
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

		if( this.lemma == null || this.lemma.equalsIgnoreCase("") )
		{
			this.features = "*" + this.originalWord;
		}
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

	public RootSet[] performMultipleStemming(String[] words)
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
			original 	= word;
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
				rsList[i] = new RootSet(word, "#"+word, original);
			}
			else
			{
				if( dbHandler.lookup(word) )
				{
					rsList[i] = new RootSet(word, "#"+word, original);
				}
				else
				{
					if( createRootSet(word).getFeatures().equals("") || createRootSet(word).getFeatures().equals("") )
					{
						rsList[i] = new RootSet( word, "*"+word, original);
					}
					else
					{
						rsList[i] = createRootSet( word );
					}
				}
			}
		}
		/* set this class' rootSetArray */
		this.rootSetArray = rsList;
		/* update the class' lemmaArray and featuresArray */
		rsToArrayStrings( rsList );
		return rsList;
	}

	public void rsToArrayStrings( RootSet[] rsList )
	{
		String[] lemmas = new String[rsList.length];
		String[] features = new String[rsList.length];


		for( int i = 0; i < rsList.length; i++ )
		{
			lemmas[i] 	= rsList[i].getLemma();
			features[i] = rsList[i].getFeatures();
		}
		this.lemmasArray 	= lemmas;
		this.featuresArray 	= features;
	}

	public String[] getLemmasArray() {
		return lemmasArray;
	}

	public String[] getFeaturesArray() {
		return featuresArray;
	}

	public RootSet[] getRootSetArray() {
		return rootSetArray;
	}

	/**
	 * Test only
	 */
	public static class Test
	{
		public static void main(String[] args)
		{
			testLemmasArray();
		}

		private static void testLemmasArray()
		{
			String[] words = { "sumama", "sumpa", "pagluto", "pinagluluto","."};
			MainController mc 	= new MainController(words);
			String[] features 	= mc.getFeaturesArray();
			String[] lemmas 	= mc.getLemmasArray();
			println("");
			for( int i = 0; i < lemmas.length; i++ )
			{
				println(lemmas[i]);
			}
		}

		private static void testMultipleWords()
		{
			MainController mc = new MainController();

			String[] words = { "sumama", "sumpa", "pagluto", "pinagluluto","."};
			String[] features;
			RootSet[] rsList = mc.performMultipleStemming(words);

			for( int i = 0; i < rsList.length; i++ )
			{
				println( rsList[i].getLemma() );
			}
			features = mc.featuresArray;
			for( int i = 0; i < features.length; i++ )
			{
				println( features[i] );
			}
		}

		private static void testSingleWord()
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
