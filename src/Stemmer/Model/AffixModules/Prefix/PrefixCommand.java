package Stemmer.Model.AffixModules.Prefix;

import Stemmer.Model.AffixModules.Prefix.Submodules.RemoveCommonPrefix;

import static Utility.print.println;

/**
 * Created by laurenztolentino on 02/09/2017.
 */
public class PrefixCommand
{
	public static class test
	{
		public static void main(String[] args)
		{
			RemoveCommonPrefix cp = new RemoveCommonPrefix();
			String word = "pinahiram";

			word = cp.reduceStem(word);
			println("word: " + word);
			println("Affix: " + cp.getFoundAffix());
			println("AffixFeatured: " + cp.getFoundAffixFeatured());
		}
	}
}