package Stemmer.Model.AffixModules;


import Stemmer.Model.DBHandler;
import Stemmer.Model.Stem;

/**
 * Created by laurenztolentino on 02/09/2017.
 */
public abstract class AbstractMorphoChange
{
	public DBHandler dbHandler;
	public String foundAffix, foundAffixFeatured = null, leftStem, rightStem;
	Stem stem;

	public abstract Stem reduceStem(Stem stem);
	public abstract String applyFeature(String foundAffix);

	public String getFoundAffixFeatured() {
		return foundAffixFeatured;
	}

	public String getFoundAffix() {
		return this.foundAffix;
	}

	public String getLeftStem() {
		return leftStem;
	}

	public String getRightStem() {
		return rightStem;
	}
}
