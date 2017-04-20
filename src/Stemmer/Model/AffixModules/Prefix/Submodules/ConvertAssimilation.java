package Stemmer.Model.AffixModules.Prefix.Submodules;

import Stemmer.Model.AffixModules.AbstractMorphoChange;
import Stemmer.Model.AffixModules.AffixList;
import Stemmer.Model.DBHandler;
import Stemmer.Model.Stem;

import static Utility.print.println;

/**
 * Created by laurenz on 09/03/2017.
 */
public class ConvertAssimilation extends AbstractMorphoChange
{
	/* database for lookups */
//	DBHandler dbHandler;
	/* Copy the list of possible prefixes under assimilation */
	String[] assimilationPrefixes = AffixList.getPrefixAssimiliation();
	/* List of possible character replacements during stemming */
	char[] possibleReplacements = AffixList.getPossibleAssimilationCharReplacements();

	public ConvertAssimilation()
	{
		dbHandler = new DBHandler();
	}

	@Override
	public Stem reduceStem(Stem stem)
	{
//		println("ConvertAssim has been called");
		/* Original word */
		String word = stem.getStemString();
		/* Word Parts as always */
		String leftPart, rightPart, combinedPart;

		/* I should really find a way to combine all of this */
		for( int k = 0; k < assimilationPrefixes.length; k++ )
		{
			/* Check if the first half of the word contains assimilated prefixes */
			if( word.substring(0, word.length()/2).contains( assimilationPrefixes[k]) )
			{
				/* if the prefix in the list matches the one in the word being stemmed */
				leftPart = word.substring( 0, assimilationPrefixes[k].length() );
				if( assimilationPrefixes[k].equalsIgnoreCase(leftPart) )
				{
					rightPart = word.substring( assimilationPrefixes[k].length() );
					/* replace removed left part with a possible replacement character */
					for( int replacementCnt = 0; replacementCnt < possibleReplacements.length; replacementCnt++ )
					{
						leftPart = possibleReplacements[ replacementCnt ] + "";
						combinedPart = leftPart + rightPart;
//						println( "combinedPart: " + combinedPart );
						/* check if root word */
						if( dbHandler.lookup( combinedPart ) )
						{
//							println("found");
							stem.setStemString( combinedPart );
							stem.setPrefixFeatures( stem.getPrefixFeatures() + applyFeature( assimilationPrefixes[k]));
						}
					}
				}
			}
		}

		/* Unreachable return? Oo. Hotel? Trivago. */
		return stem;
	}

	@Override
	public String applyFeature(String foundAffix) {
		return "~" + foundAffix;
	}

	public static class Test
	{
		public static void main(String[] args)
		{
			ConvertAssimilation ca = new ConvertAssimilation();

			String word = "pangatlo";
			Stem stem = new Stem( word );
			stem = ca.reduceStem( stem );
			println( "Reduced stem: " + stem.getStemString() );
		}
	}
}
