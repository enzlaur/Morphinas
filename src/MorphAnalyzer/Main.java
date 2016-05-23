package MorphAnalyzer;

import DataStructures.*;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main 
{
	MorphLearnerRedup mpl = new MorphLearnerRedup();
    WordsLoader training = new WordsLoader(WordsLoader.TRAINING);
	
    String inputSentence = "";
    String input = "";
    
	public Main() throws Exception
	{
		
	}

    /**
	 * noGUI launches fun stuff without that old-school JFrame
	 * @param input
     */
	public void noGUI(String input)
	{
		TestMaker tm = new TestMaker();
		Formatter fm;
		WordPair wp;
		input = input.toLowerCase();
//		println("Finding root of: " + input);
		// String ng result only
		String root = ""; 
        root = mpl.analyzeMultipleMod(input).result;
        // Result using MAResult
        MAResult maresult = mpl.analyzeMultipleMod(input); // Not working properly #why
        Word word = mpl.getWordObject();
        
//		root = mpl.analyzeMultipleModWithSemantic2(input).result;
//      println("From Sir Sol's MAResult:");
//      println("Result is:  " + root);
//      println("infix: " + maresult.infix);
//      println("suffix: " + maresult.suffix);
//      println("prefix: " + maresult.prefix);
//      println("Redup: " + maresult.redup);
//      println("\n \n");
//      println("word: " + word.getRootWord());

		fm = new Formatter(word);
		//fm.printWordContentDetailed();
        // fm.printBracketedResult();
		fm.printFormattedResult();
		fm.printFeaturesResult();
		println("");
//		AffixBreakdown ab = new AffixBreakdown();

		try {
			fm.printLongestOnly();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			println("whoopsies. Didn't find the longest ");
		}
        
	}

	public String startIt() throws Exception
	{
		TestMaker tm = new TestMaker("/Users/laurenztolentino/Eclipse/workspace/Morphinas/src/","testHPOST.words");
		String[] wordsList = tm.readFromFile();
		boolean skip = false;
		String result = "";
		MAResult maresult;
		Formatter fm;
		Word word;

		DBLexiconSQL t = new DBLexiconSQL();

		for( int i = 0; i < wordsList.length; i++ )
		{
//			println("wordList"+"[" + i + "]: " + wordsList[i]);
			String single = wordsList[i];
			single = single.toLowerCase();
			single = Formatter.removeNonLetters(single);

			if( t.lookup(single) )
			{
				result 	= result + "#" + single + " ";
				skip 	= true;
			} else {
				skip = false;

				// because all tagalog words are already root when <= 3
				if( single.length() > 3 && !skip)
				{
					mpl.analyzeMultipleMod(single);
					word = mpl.getWordObject();
					fm   = new Formatter(word);

					if( !fm.generateFeaturesResult().equalsIgnoreCase(""))
						result = result + fm.generateFeaturesResult() + " ";
				} else {
					result = result + "#" + single + " ";
				}
			}




		}

		println("");
		println(result);



		return result;
	}

	public void testSingleWord(String word)
	{
		input = Formatter.removeNonLetters(word);
		noGUI(input);
	}

	public static void main(String[] args) throws Exception 
	{
//		Just for counting the running time
		NumberFormat formatter = new DecimalFormat("#0.00000");
		long startTime, endTime;
		startTime  = System.currentTimeMillis();

		Main m = new Main();

//		startIt reads from a file
//		m.startIt();

//		The line below can be used for testing a single word only
		m.testSingleWord("karamihan");

		endTime = System.currentTimeMillis();


		println("Execution time is " + formatter.format((endTime - startTime) / 1000d) + " seconds");

	}



	public static void println(String in)
	{
		System.out.println("" + in);	
	}
	public static void print(String in) {
		System.out.print("" + in);
	}
}
