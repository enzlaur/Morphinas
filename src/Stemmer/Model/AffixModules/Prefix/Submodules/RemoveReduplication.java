package Stemmer.Model.AffixModules.Prefix.Submodules;

import Stemmer.Model.Stem;

/**
 * Created by laurenztolentino on 02/09/2017.
 */
public class RemoveReduplication
{
	public Stem reduceStem(Stem stem)
	{
		Stem newStem = stem.cloneThis();
		String word = newStem.getStemString();
		if( word.contains("-") )
		{
			stem = wholeReduplication( newStem );
		}
		return stem;
	}

	public Stem wholeReduplication(Stem stem)
	{
		String word = stem.getStemString();
		String leftPart = "", rightPart = "";

		for( int i = 0; i < word.length(); i++ )
		{
			if( word.charAt(i) == '-' )
			{
				leftPart 	= word.substring(0, i);
				rightPart 	= word.substring(i + 1);
			}
		}
//		println("LP: " + leftPart + " RP: " + rightPart);
		stem.setFeature( stem.getFeature() + "$" + leftPart);
		stem.setStemString( rightPart );
		return stem;
	}

	public Stem partialReduplication(Stem stem)
	{
		String word = stem.getStemString();
		String leftPart, rightPart, possibleRedup;

		for( int i = 0; i < (word.length()/2); i++)
		{

		}
		// do something
		return stem;
	}

	public static class TestMe
	{
		public static void main(String[] args)
		{
			/* Create stem first */
			Stem stem = new Stem("hati-hatian");
			RemoveReduplication redup = new RemoveReduplication();
			redup.reduceStem(stem);
		}
	}
}


///*
//	 * Reduplicates the cluster of consonants including the succeeding vowel of
//	 * the stem.
//	 */
//private void redupRule4() {
//	word = word.substring(3);
//}
//
//	/* If the first syllable of the root has a cluster of consonants, */
//	private void redupRule3() {
//		word = word.substring(2);
//	}
//
//	/*
//	 * In a two-syllable root, if the first syllable of the stem starts with a
//	 * consonant- vowel, the consonant and the succeeding vowel is reduplicated.
//	 */
//	private void redupRule2() {
//		word = word.substring(2);
//	}
//
//	/*
//	 * If the root of a two-syllable word begins with a vowel, the initial
//	 * letter is repeated.
//	 */
//	private void redupRule1() {
//		word = word.substring(1);
//	}
// */