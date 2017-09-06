package Stemmer.View;

import Stemmer.Controller.MainController;
import Stemmer.Model.DBHandler;
import Stemmer.Model.Sentence;
import Stemmer.Stemmer;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static Utility.print.*;
/**
 * Created by laurenztolentino on 02/08/2017.
 */
public class Main
{
	IOHandler ioHandler;

	long startTime, endTime;
	final String addressPrefix = "/Users/laurenztolentino/Developer/morphinas/Morphinas/ReadFiles/";

	/* testHPOST Variations to load */
	final String testHPOSTuncleaned = "testHPOST-uncleaned.words";
	final String testHPOST 			= "testHPOST.words";
	final String testHPOST17 		= "testHPOST-17.words";
	final String morphRead 			= "morphRead.pinas";
	final String minitext			= "minitext.txt";
	final String readThisFile		= "correct_words.txt";
	final String pcariEval			= "pcari.eval.fil";


	final char[] nonAlphaCircum = { '"', '\'', '(', ')' };
	final char[] punctuation 	= { '!', '.', ',', '?', '\''};
	final char[] nums = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

	public Main() {}

	public void startTesting() throws Exception
	{
		ArrayList<Sentence> sentences;
		ioHandler = new IOHandler( addressPrefix, testHPOST);
		String[] content = ioHandler.readFromFile();
		sentences = ioHandler.createSentences( content );
		println("Sentence Size: " + sentences.size());
		for( Sentence sentence : sentences )
		{
			for( String word : sentence.getWords() )
			{
				print( word + " " );
			}
			println("");
		}
	}

	public void performStemmingLemmaOnly() throws Exception
	{
		/* New Stemmer */
		Stemmer stemmer 	= new Stemmer();
//		DBHandler dbHandler = new DBHandler();
		/* Input Variables */
		ArrayList<Sentence> sentences;
		ArrayList<String> words;
		String[] content;
		/* Output Variables */
		ArrayList<String> newWords;
		ArrayList<Sentence> newSentence;
		/* Temp Variables */
		String lowerCase, reduced;
		Sentence tempSentence;
		int i, max;
		/* Initialization */
		ioHandler 	= new IOHandler( pcariEval );
		content 	= ioHandler.readFromFile();
		sentences 	= ioHandler.createSentences( content );
		newSentence = new ArrayList<Sentence>();

		for( Sentence sentence : sentences )
		{
			words = (ArrayList<String>)sentence.getWords().clone();
			newWords = new ArrayList<String>();
			i = 0; max = words.size();
			for( String word : words )
			{
				lowerCase 	= word.toLowerCase();

				if( checkIfContainsNonAlpha(word) && lowerCase.length() > 3 )
				{
					lowerCase 	= cleanWord(lowerCase);
				}
				// double check because there are idiots in this world
				if( checkIfContainsNonAlpha(word) && lowerCase.length() > 3 )
				{
					lowerCase 	= cleanWord(lowerCase);
				}
				if( !isANumber(0, lowerCase) && lowerCase.length() < 11)
				{
					reduced = stemmer.lemmatizeSingle(lowerCase);
				}
				else
				{
					reduced = lowerCase;
				}
				newWords.add( reduced );
			}
			tempSentence = new Sentence();
			tempSentence.setWords( newWords );
			newSentence.add( tempSentence );
		}

		printSentencesContent( newSentence );
	}

	public boolean isANumber( int index, String word )
	{
		for( int i = 0; i < nums.length; i++ )
		{
			if( word.charAt( index ) == nums[i] )
				return true;
		}
		return false;
	}

	public String removeWithin( String word )
	{
		return word = word.replace("\'", "");
	}

	public boolean checkIfContainsNonAlpha( String word )
	{
		for( char item : nonAlphaCircum )
		{
			if ( word.contains( item + "") )
			{
				return true;
			}
		}
		for( char item : punctuation )
		{
			if( word.contains( item + ""))
			{
				return true;
			}
		}
		return false;
	}

	public String cleanWord(String word)
	{
		char first 	= word.charAt( 0 );
		char last 	= word.charAt( word.length() - 1);

		for( int i = 0; i < nonAlphaCircum.length; i++ )
		{
			if( first == nonAlphaCircum[i] )
			{
				word = word.substring(1);
			}
			if( last == nonAlphaCircum[i] )
			{
				word = word.substring(0, word.length() - 1);
			}
		}
		if( word.length() > 2 )
		{
			char befLast= word.charAt( word.length() - 2 );

			for( int i = 0; i < punctuation.length; i++ )
			{
				if( befLast == punctuation[i] )
				{
					word = word.substring(0, word.length() - 2 );

				}
			}
		}

		word = removeWithin( word );

		return word;
	}
	public void performStemming() throws Exception
	{
		/* Input variables */
		ArrayList<Sentence> sentences;
		ioHandler = new IOHandler( addressPrefix, pcariEval );
		String[] content = ioHandler.readFromFile();
		sentences = ioHandler.createSentences( content );
		/* Output variables */
		ArrayList<Sentence> stemmedSentences = new ArrayList<>();
		ArrayList<String> stemmedWords 		 = new ArrayList<>();
		Sentence stemmedSentence;
		/* Manipulation variables */
		String word, lowered, preAdd;
		/* Stemming clasees */
		MainController mc;
		/* Database Lookuper */
		DBHandler dbHandler = new DBHandler();
		for ( Sentence sentence : sentences )
		{
			ArrayList<String> words = (ArrayList<String>)sentence.getWords().clone();

			for ( int i = 0; i < words.size(); i++ )
			{
				word = words.get(i);
				lowered = word.toLowerCase();
				preAdd = "";
				/* setup preadd contents */
				if( i == 0 )
				{
					preAdd = preAdd + ":FS";
				}
				if( ! word.equals(lowered) && i > 0)
				{
					preAdd = preAdd + ":F";
				}
				word = lowered;
				/* proceed to stem the word */
				if( !word.equals(" ") || !word.equalsIgnoreCase(""))
				{
					if (word.contains("'"))
					{
						String rep = "";
						for(int c = 0; c < word.length(); c++)
						{
							if( word.charAt(c) != '\'' )
							{
								rep = rep + word.charAt(c);
							}
						}
						println("rep: " + rep);
						word = rep;
					}
					/* check if the word is already a root word*/
					if( dbHandler.lookup(word) )
					{
						stemmedWords.add( preAdd + "#" + word);
					}
					else
					{
						if( word.length() < 5)
						{
							stemmedWords.add( preAdd + "#" + word);
						}
						else
						{
							mc = new MainController(word);
							if( mc.getFeatures().equals("") || mc.getFeatures().equals(" "))
							{
								stemmedWords.add( preAdd + "*" + word);
							}
							else
							{
								stemmedWords.add( preAdd + mc.getFeatures() );
							}
						}
					}
				}

			}
			stemmedSentence = new Sentence();
			stemmedSentence.setWords( stemmedWords );
			stemmedWords = new ArrayList<String>();
			stemmedSentences.add( stemmedSentence );
		}
		println("stemmedSenteces count: " + stemmedSentences.size());

		printSentencesContent( stemmedSentences );

	}

	public void printSentencesContent( ArrayList<Sentence> sentences) throws Exception {
		String toPrint = "";
		for( Sentence sentence : sentences )
		{
			for( String word : sentence.getWords() )
			{
				print( word + " " );
				toPrint = toPrint + word + " ";
			}
			toPrint = toPrint + "\n";
			println("");
		}

		ioHandler.printToTxtFileRoot("pcariResult", toPrint);
	}

	public static class Test
	{
		public static void main(String[] args) throws Exception
		{
			Main m = new Main();
//			m.performStemming();
			m.performStemmingLemmaOnly();
//			Test t = new Test();
//			t.cleanWordTest();
		}

		public void cleanWordTest()
		{
			Main m = new Main();
			println( m.cleanWord("'prospect'") );
		}
	}

}
