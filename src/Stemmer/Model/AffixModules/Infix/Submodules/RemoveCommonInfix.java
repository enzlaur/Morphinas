package Stemmer.Model.AffixModules.Infix.Submodules;

import Stemmer.Model.AffixModules.AbstractMorphoChange;
import Stemmer.Model.AffixModules.AffixList;
import Stemmer.Model.Stem;

import static Utility.print.*;

/**
 * Created by laurenztolentino on 02/09/2017.
 */
public class RemoveCommonInfix extends AbstractMorphoChange
{
	String[] commonInfixes = AffixList.getCommonInfixes();
	char[] vowels			= AffixList.getVowels();
	@Override
	public Stem reduceStem(Stem stem)
	{
		String word = stem.getStemString();
		String inString;
		int infixLength;
		int stemLength = word.length();
		/* iterates on the set of existing canonical infixes */
		for( String infix: commonInfixes )
		{
			infixLength = infix.length();
			String firstHalf = word.substring(1, (word.length()/2));
			if ( firstHalf.contains(infix) )
			{
				for( int i = 1; i < ( (stemLength/2) + 1) + 1; i++ )
				{
					inString = word.substring(i, i+infixLength);
//					println("inString: " + inString);
					/* if it matches */
					if ( infix.equalsIgnoreCase(inString) )
					{
						if( ruleNotCCAfterStemming(word, i, i) )
						{
							/* return original */
							return stem;
						}
						else
						{
							if( word.charAt(i) != vowels[0])
							{
								leftStem 	= word.substring(0, i);
							}
							rightStem 	= word.substring( i+infixLength);
							foundAffix 	= infix;
							/* Set or Update stem properties */
							stem.setStemString(leftStem.concat(rightStem));
							stem.addInfix( infix );
						}
						return stem;
					}
				}
			}
		}
		return stem;
	}

	/**
	 * Assures that the resulting stem after removing the infix will satisfy the non-CCAfterInfixRemoval
	 * @param word
	 * Word to be stemmed
	 * @param prevCharIndex
	 * Location of the character at the left of the infix
	 * @param nextCharIndex
	 * Location of the character at the right of the infix
	 * @return
	 * Returns true if the infix spot will contain CC
	 * False otherwise.
	 */
	public boolean ruleNotCCAfterStemming(String word, int prevCharIndex, int nextCharIndex)
	{
//		println( word + "/" + prevCharIndex + "/" + nextCharIndex);
		int consonantCount = 0;
		prevCharIndex--;
		nextCharIndex += 2;
//		for( int i = 0; i < vowels.length; i++ )
//		{
//			if( word.charAt( prevCharIndex) != vowels[i] )
//		}
		for( int i = 0; i < vowels.length; i++ )
		{
			if( word.charAt(prevCharIndex) != vowels[i])
			{
				consonantCount++;
				break;
			}
		}

		for( int i = 0; i < vowels.length; i++ )
		{
//			println("word: " + word);
			if( word.charAt(nextCharIndex) == vowels[i])
			{
				consonantCount = 0;
				break;
			} else
			{
				consonantCount++;
			}
		}

//		for( int i = 0; i < vowels.length; i++ )
//		{
//			println(word.charAt(prevCharIndex)+"-"+word.charAt(nextCharIndex));
//			if( word.charAt(prevCharIndex) != vowels[i] && word.charAt(nextCharIndex) != vowels[i] )
//			{
//				println("consonantCount increased");
//			}
//		}

		if( consonantCount >= 2)
		{
				return true;
		}
		return false;
	}

	@Override
	public String applyFeature(String foundAffix)
	{
		foundAffixFeatured = "@" + foundAffix;
		return foundAffixFeatured;
	}
}
