package Stemmer.Model.AffixModules.Infix;

import Stemmer.Model.AffixModules.AbstractAffixCommand;
import Stemmer.Model.AffixModules.Infix.Submodules.RemoveCommonInfix;
import Stemmer.Model.Stem;

import static Utility.print.println;

/**
 * Created by laurenztolentino on 02/09/2017.
 */
public class InfixCommand extends AbstractAffixCommand
{
	Stem newStem;

	public InfixCommand(){}

	public Stem performStemmingModules(Stem stem)
	{
		/* Initialize new Stem objects for immutability */
		Stem oldStem = stem.cloneThis();
		Stem newStem = stem.cloneThis();
		/* Initialize submodules for stemming */
		RemoveCommonInfix rci = new RemoveCommonInfix();
		/* Perform stemming */
		newStem = rci.reduceStem( newStem );
		/* Check for Changes and update boolean changed */
		checkForChanges(oldStem, newStem);
		return newStem;
	}

	public static class Test
	{
		public static void main(String[] args)
		{
			Test t = new Test();
			t.runTest();
		}

		public void runTest()
		{
//			String word = "pinapinta";
			String word = "gagawin";
			Stem stem = new Stem( word );
			Stem newStem;
			InfixCommand ic = new InfixCommand();

			newStem = ic.performStemmingModules( stem );
			println("NewStem: " + newStem.getStemString());
			println("Changes: " + ic.isChanged());
			ic = new InfixCommand();
			Stem newStem2 = newStem.cloneThis();
			newStem2 = ic.performStemmingModules( newStem2 );
			println("NewStem: " + newStem2.getStemString());
			println("Changes: " + ic.isChanged());
		}
	}
}
