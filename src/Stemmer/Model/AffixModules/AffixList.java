package Stemmer.Model.AffixModules;

import java.util.HashMap;

/**
 * Created by laurenztolentino on 02/09/2017.
 */
public class AffixList
{
	final static char vowels[] = {
			'a', 'e', 'i', 'o', 'u'
	};

	final static String commonPrefixes[] = {
			"nag", "na", "mag", "ma", "i", "i-", "ika-", "isa-", "ipag",
			"ipang", "ipa", "pag", "pa", "um", "in", "ka", "ni", "pinaka",
			"pinag", "pina"
	};

	final static String prefixAssimiliation[] = {
			"pang", "pam", "pan",
			"mang", "mam", "man",
			"sang", "sam", "san",
			"sing", "sim", "sin"
	};

	final static String commonInfixes[] = {
		"um", "in"
	};

	final static String commonSuffixes[] = {
			"nan", "han", "hin", "an", "in", "ng"
	};

	final static String longPrefixes[] = {
			"napag", "mapag", "nakipag", "nakikipag", "makipag", "makikipag",
			"nakiki", "makiki", "naka", "nakaka",
	};

	final static String[] phonemeChangePrefixes  = { "mar" };

	final static char[] possiblePhonemeCharReplacements = { 'd', 'b', 'p' };

	final static char[] possibleAssimilationCharReplacements = { 'p', 's', 'b', 'k', 'd', 'l', 't' };

	/**
	 * List down all the affixes with their equivalent breakdown
	 * @return
	 * HashMap with prefix and it's equivalent format
	 */
	public HashMap initiateEquivalentBreakdown()
	{
		HashMap hm = new HashMap();
		hm.put("pinag", 	 "~pinag");
		hm.put("pinagpa",	 "~pinag~pa");
		hm.put("ipang", 	 "~ipang");
		hm.put("ipinag",	 "~i~pinag");
		hm.put("ipinagpa", 	 "~i~pinag~pa");
		hm.put("nakiki", 	 "~na$ki$ki");
		hm.put("makiki", 	 "~ma$ki$ki");
		hm.put("nakikipag",  "~na$ki$ki~pag");
		hm.put("napag", 	 "~na~pag");
		hm.put("mapag", 	 "~ma~pag");
		hm.put("nakipag", 	 "~na~ki~pag");
		hm.put("makipag", 	 "~ma~ki~pag");
		hm.put("makikipag",  "~ma~ki~pag");
		hm.put("naka", 		 "~na~ka");
		hm.put("nagpa", 	 "~nag~pa");
		hm.put("nakaka", 	 "~na$ka$ka");
		hm.put("maka", 	 	 "~ma~ka");
		hm.put("makaka", 	 "~ma$ka$ka");
		hm.put("nagka", 	 "~nag~ka");
		hm.put("nagkaka", 	 "~nag$ka$ka");
//		hm.put("magka", 	 "~mag~ka");
//		hm.put("magkaka", 	 "~mag$ka$ka");
		hm.put("napaki", 	 "~na~pa~ki");
		hm.put("napakiki", 	 "~na~pa$ki$ki");
		hm.put("mapaki", 	 "~ma~pa~ki");
		hm.put("mapakiki", 	 "~ma~pa$ki$ki");
		hm.put("paki", 		 "~pa~ki");
		hm.put("pagka", 	 "~pag~ka");
		hm.put("pakiki", 	 "~pa$ki$ki");
		hm.put("pakikipag",  "~pa$ki$ki~pag");
		hm.put("pagki", 	 "~pag~ki");
		hm.put("pagkiki", 	 "~pag$ki$ki");
		hm.put("pagkikipag", "~pag$ki$ki~pag");
		/* recently added jan 2017 */
		hm.put("ika", 		 "~i~ka");
		hm.put("ikapag",	 "~i~ka~pag");
		hm.put("ikapagpa", 	 "~i~ka~pag~pa");
		hm.put("ikina", 	 "~i~ka@in");
		hm.put("ikapang", 	 "~i~ka~pang");
		hm.put("ipa", 		 "~i~pa");
		hm.put("ipaki", 	 "~i~paki");
		hm.put("ipag", 		 "~i~pag");
		hm.put("ipagka", 	 "~i~pag~ka");
		hm.put("ipagpa", 	 "~i~pag~pa");
		hm.put("ipapang", 	 "~i$pa$pang");
		hm.put("makapag", 	 "~maka~pag");
		hm.put("magkanda", 	 "~mag~kanda");
		hm.put("magkang", 	 "~mag~ka~ng");
		hm.put("magkasing",  "~mag~ka~sing");
		hm.put("maging",  	 "~maging");
		hm.put("nakapag-", 	 "~nakapag");
		hm.put("nakapaka", 	 "~napa~ka");
		return hm;
	}

	public static String[] getCommonPrefixes() {
		return commonPrefixes;
	}

	public static String[] getLongPrefixes() {
		return longPrefixes;
	}

	public static String[] getCommonInfixes() {
		return commonInfixes;
	}

	public static String[] getCommonSuffixes() {
		return commonSuffixes;
	}

	public static char[] getVowels() {
		return vowels;
	}

	public static String[] getPrefixAssimiliation() {
		return prefixAssimiliation;
	}

	public static String[] getPhonemeChangePrefixes() {
		return phonemeChangePrefixes;
	}

	public static char[] getPossiblePhonemeCharReplacements() {
		return possiblePhonemeCharReplacements;
	}

	public static char[] getPossibleAssimilationCharReplacements() {
		return possibleAssimilationCharReplacements;
	}
}
