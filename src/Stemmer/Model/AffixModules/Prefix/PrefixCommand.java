package Stemmer.Model.AffixModules.Prefix;

import Stemmer.Model.AffixModules.AbstractAffixCommand;
import Stemmer.Model.AffixModules.Prefix.Submodules.ConvertPhonemeChanges;
import Stemmer.Model.AffixModules.Prefix.Submodules.RemoveCommonPrefix;
import Stemmer.Model.AffixModules.Prefix.Submodules.RemoveReduplication;
import Stemmer.Model.Stem;

import static Utility.print.println;

/**
 * Created by laurenztolentino on 02/09/2017.
 */
public class PrefixCommand extends AbstractAffixCommand
{
	Stem newStem;

	public PrefixCommand(){}

	public Stem performStemmingModules(Stem stem)
	{
		Boolean changes = false;
		/* Initialize submodules for stemming */
		RemoveCommonPrefix 		rcp	= new RemoveCommonPrefix();
		RemoveReduplication 	rdp	= new RemoveReduplication();
		/* Initialize new Stem objects for immutability */
		Stem oldStem = stem.cloneThis();
		Stem newStem = stem.cloneThis();
		/*
		 *	Perform stemming
		 */
		// CommonPrefix
		newStem = rcp.reduceStem( newStem );
		/* Check for Changes and update boolean changed */
		if ( !checkForChanges(oldStem, newStem) ) {
			newStem = rdp.reduceStem( newStem );
		}
		if ( !checkForChanges(oldStem, newStem) ) {
			newStem = rdp.reduceStem( newStem );
		}

		/* Return the new stem */
		return newStem;
	}

	/*
	  * ********************************************************************
	  *              Getters and Setters only beyond this point
	  * ********************************************************************
	 */

	public Stem getNewStem() {
		return newStem;
	}

	/*
	  * ********************************************************************
	  *                    Testing Only Beyond this Point
	  * ********************************************************************
	 */

	public static class test
	{
		public static void main(String[] args)
		{
			String word = "pagluto";
			Stem stem  	= new Stem(word);
			PrefixCommand pc = new PrefixCommand();
			Stem newStem = pc.performStemmingModules(stem);
			println(stem.getStemString() + " -> " + newStem.getStemString());
			println( "Changes: " + pc.isChanged() );
		}
	}
}
