package Stemmer.View;

import Stemmer.Model.RootSet;
import Stemmer.Model.Sentence;

import java.io.*;
import java.util.ArrayList;

import static Utility.print.*;

/**
 * Created by laurenz on 22/03/2017.
 */
public class IOHandler
{
	String fileDirectory = "/Users/laurenztolentino/Developer/Morphinas/morphinas/ReadFiles/";
	String fileName		 = "words.txt";

	public IOHandler()
	{}

	/**
	 * When you have a custom fileDirectory and fileName
	 * @param fileDirectory
	 * @param fileName
	 */
	public IOHandler(String fileDirectory, String fileName)
	{
		this.fileDirectory 	= fileDirectory;
		this.fileName 		= fileName;
	}

	public IOHandler(String fileName)
	{
		this.fileName 		= fileName;
	}

	public ArrayList<Sentence> createSentences( String[] sentenceStringList ) throws Exception
	{
		/* To be returned */
		ArrayList<Sentence> sentences = new ArrayList<>();
		/* Lists */
		ArrayList<String> words = new ArrayList<>();
		ArrayList<RootSet> rootSets;
		String[] wordStringList;
		/* Non list temp variables */
		String replaceUnderscore;
		Sentence sentence;
		String word;

		/* Iterate the entire sentence array */
		for ( String sentenceString : sentenceStringList)
		{
			/* removes '_' in a sentence and splits them */
			replaceUnderscore = sentenceString.replace("_", " ");
			/* split it kapag may espasyo */
			wordStringList = replaceUnderscore.split(" ");
			/* Putting array of words into ArrayList of words */
			for ( String wordString: wordStringList )
			{
				words.add( wordString );
			}
			sentence = new Sentence();
			sentence.setWords( words );
			sentences.add( sentence );
			words = new ArrayList<>();
		}
		return sentences;
	}

	public String[] readFromFile () throws Exception
	{
		/* To be returned */
		String[] sentences 			 = { "" };
		/* Please label properly */
		String finalContent 	 = "";
		String content 			 = "";

		int lineNumber 	 		 = 0;
		BufferedReader br;

		try
		{
			br = new BufferedReader( new FileReader(fileDirectory + fileName ) );
			while ((content = br.readLine()) != null)
			{
				lineNumber++;
				println(""+lineNumber);
				println("\r " + lineNumber + " out of (lagpas sa sampung daliriri)");
				if( content.toString().matches("^.*[.].*$")) {
					if( content.toString().length() > 1 )
					{
						/* hmmmm what goes in here? */
					}
				}
				finalContent = finalContent + content + "\n";
			}
			println("\n Done reading from file.");
			/* loads not words but sentences so sad */
			sentences = finalContent.split("\n");
		}
		catch ( FileNotFoundException e)
		{
			e.printStackTrace();
		}
		println("Sentence Size from IOHandler: " + sentences.length );
		return sentences;
	}

	public void printToTxtFileRoot(String fileName, String toPrint) throws Exception
	{
		String completeFileName = fileName + ".txt";
		PrintWriter writer = new PrintWriter(completeFileName, "UTF-8");
		// Write the result to file
		writer.println(toPrint);
		// Close the printer
		writer.close();
	}
}
