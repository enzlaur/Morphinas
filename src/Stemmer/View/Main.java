package Stemmer.View;

import Stemmer.Controller.MainController;
import Stemmer.Model.DBHandler;
import Stemmer.Model.Sentence;

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

	public void performStemming() throws Exception
	{
		/* Input variables */
		ArrayList<Sentence> sentences;
		ioHandler = new IOHandler( addressPrefix, testHPOST );
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

		ioHandler.printToTxtFileRoot("stemmerResult", toPrint);
	}

	public static class Test
	{
		public static void main(String[] args) throws Exception
		{
			Main m = new Main();
			m.performStemming();
		}
	}

}
