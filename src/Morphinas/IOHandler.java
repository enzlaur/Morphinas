package Morphinas;

import java.io.*;
import java.util.ArrayList;
import DataStructures.Sentence;
import DataStructures.Word;
/**
 * Created by laurenztolentino on 05/31/2016.
 */
public class IOHandler
{
	String fileDirectory = "/Users/laurenz/Developer/morphinas/Morphinas/ReadFiles/";
	String fileName		 = "words.txt";

	public IOHandler()
	{}

	/**
	 * Wherein the fileDirectory is the default "/Users/laurenztolentino/Developer/Morphinas/morphinas/" and you need to feed a fileName.
	 * @param fileName
	 */
	public IOHandler( String fileName )
	{
		this.fileName = fileName;
	}
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

	public String[] readFromFile (String fileName) throws Exception
	{
		//this.fileDirectory 	= "";
		this.fileName 	  	= fileName;
		return readFromFile();
	}

	/**
	 *
	 * @return
	 * an array list of sentences. Each array contains a sentence (one line = one sentence).
	 * @throws Exception
	 */
	public String[] readFromFile () throws Exception
	{
		ArrayList<String> input;
		String finalContent = "";
		String content = "";
		String[] words = { "" };
		int lineNumber = 0;
		BufferedReader br;

		try
		{
			br = new BufferedReader( new FileReader(fileDirectory + fileName) );
			println("Reading from file ....");

			while ((content = br.readLine()) != null)
			{
				lineNumber++;
				System.out.print(""+lineNumber);
				System.out.print("\r " + lineNumber + " out of (lagpas sa sampung daliriri)");
				if( content.toString().matches("^.*[.].*$")) {
					if( content.toString().length() > 1 )
					{
						/* hmmmm what goes in here? */
					}
				}
				finalContent = finalContent + content + "\n";
			}
			println("\n Done reading from file.");
			/* Splits content by spaces. */
			words = finalContent.split("\n");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		/* return result */
		return words;
	}

	/**
	 * Runs readFromFileToSentence() with a modified fileName to be read from.
	 * @param fileName
	 * A fileName that is different from the one used in the constructor
	 * @return
	 * result from the original readFromFileToSentence using the modified fileName
	 * @throws Exception
	 */
	public ArrayList<Sentence> readFromFileToSentence( String fileName ) throws Exception
	{
		this.fileName = fileName;
		return readFromFileToSentences();
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Sentence> readFromFileToSentences() throws Exception
	{
		/* to be returned */
		ArrayList<Sentence> resultSentences = new ArrayList<>();
		/* usable variables */
		String[] stringSentences;
		String[] stringWords;
		/* DataStructure variables */
		ArrayList<Word> words;
		Sentence sentence;
		Word word;
		/* get String[] of sentences from readFromFile */
		stringSentences = readFromFile();

		for( String singleSentence: stringSentences )
		{
			/* generate String[] of words */
			stringWords = singleSentence.split(" ");
			/* create array list of words that will be added to the sentence */
			words = new ArrayList<>();
			/* iterate all words in the String[] */
			for( String singleWord: stringWords )
			{
				word = new Word( singleWord );
				words.add( word );
			}
			/* add the generated word()s to a sentence */
			sentence = new Sentence();
			sentence.setWords( words );
			/* update resultSentences with the new sentence() generated */
			resultSentences.add( sentence );
		}

		return resultSentences;
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

	public void printToTxtFile(String toPrint) throws Exception
	{
		PrintWriter writer = new PrintWriter("morphinas-result.txt", "UTF-8");
		// Write the result to file
		writer.println(toPrint);
		// Close the printer
		writer.close();
	}

	public static void main( String[] args ) throws Exception
	{
		IOHandler ioh = new IOHandler("/Users/laurenztolentino/Developer/Morphinas/morphinas/","minitext.txt");
		String[] test = new String[0];
		try {
			test = ioh.readFromFile();
		} catch (Exception e) {
			println("Error reading file. File is probably missing. <sad face emoticon>");
		}

		for ( String temp: test )
		{
			println(temp + "");
		}


	}

	public static void print( Object input )
	{
		System.out.print("" + input.toString() );
	}
	public static void println(Object input)
	{
		System.out.println("" + input.toString());
	}
}
