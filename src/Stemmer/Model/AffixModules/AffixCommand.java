package Stemmer.Model.AffixModules;

import Stemmer.Model.AffixModules.Infix.InfixCommand;
import Stemmer.Model.AffixModules.Prefix.PrefixCommand;
import Stemmer.Model.AffixModules.Suffix.SuffixCommand;
import Stemmer.Model.Branch;
import Stemmer.Model.DBHandler;
import Stemmer.Model.Stem;
import Stemmer.Model.RootSet;
import java.util.ArrayList;

import static Utility.print.*;

/**
 * Created by laurenz on 09/02/2017.
 */
public class AffixCommand
{
	/* TreeTest properties */
	int treeDepth;
	boolean mustStop 	= false;
	boolean donePrefix 	= false;
	boolean doneInfix 	= false;
	boolean doneSuffix	= false;
	/* Commands */
	PrefixCommand pc;
	InfixCommand ic;
	SuffixCommand sc;
	/* DBHandler */
	DBHandler dbHandler = new DBHandler();


	public AffixCommand()
	{}

	/**
	 * Main working method. Do not use anything else.
	 * @param word
	 * Word to be stemmed. Stemming generates a tree.
	 * @return
	 * A Rootset which contains root word and features in G format
	 */
	public RootSet generatePISTree3(String word)
	{
		/* Result set */
		String result = "";
		RootSet rs;
		/* Ecological Creation */
		ArrayList<ArrayList<Branch>> ty 	= new ArrayList<>();
		ArrayList<Branch> tx 				= new ArrayList<>();
		/* Branches */
		Branch rootBranch;
		/* Temp Vars */
		ArrayList<Branch> tempX;
		/* Create the root node */
		Stem rootStem 	= new Stem( word );
		rootBranch		= new Branch( rootStem );
		/* Add the root node in ArrayList */
		tx.add( rootBranch );
		ty.add( tx );

		/* Go out and populate */
		for( int y = 0; y < ty.size(); y++ )
		{
			tx 		= new ArrayList<>();
			tempX	= ty.get( y );
			for( int x = 0; x < tempX.size(); x++ )
			{
				Stem stemX = tempX.get(x).getStem();
//				println("stemX: " + stemX.getStemString());
				if( stemX.getStemString().length() < 5 ) {
					// check if the current short stem is a root word
					if( dbHandler.lookup(stemX.getStemString()) )
					{
//						println("a root word was found: " + stemX.getStemString());
//						println(stemX.getCombinedFeatures());
					}
				}
				else
				{
					tempX.get(x).generateBranchChildren2(stemX);
				/*
				 *	Useful? Maybe. Hotel? Trivago.
				 */
					if( y == 2)
					{
						Stem nullStem = new Stem("NULL");
						Branch nullBranch = new Branch( nullStem );
						if ( !dbHandler.lookup(tempX.get(x).getPrefixBranch().getStem().getStemString() ) )
						{
							tx.add( nullBranch );
						} else {
							tx.add( tempX.get(x).getPrefixBranch() );
						}
						if ( !dbHandler.lookup(tempX.get(x).getInfixBranch().getStem().getStemString() ) )
						{
							tx.add( nullBranch );
						} else {
							tx.add( tempX.get(x).getInfixBranch() );
						}
						if ( !dbHandler.lookup(tempX.get(x).getSuffixBranch().getStem().getStemString() ) )
						{
							tx.add( nullBranch );
						} else {
							tx.add( tempX.get(x).getSuffixBranch() );
						}
					}
					else
					{
						tx.add( tempX.get(x).getPrefixBranch() );
						tx.add( tempX.get(x).getInfixBranch() );
						tx.add( tempX.get(x).getSuffixBranch() );
					}
					/*
					* Re-instate the 3 lines below once a working tree-stopper exists or maybe something comes up better
					* */
	//				tx.add( tempX.get(x).getPrefixBranch() );
	//				tx.add( tempX.get(x).getInfixBranch() );
	//				tx.add( tempX.get(x).getSuffixBranch() );
				}

			}
			ty.add(tx);

			if( ty.size() > 3)
			{
				break;
			}

		}
//		printTreeContent(ty);
		rs = getHighestFreqRoot( ty, word );
//		println( rs.getLemma() + "->" + rs.getOriginalWord() + "-> " + rs.getFeatures() );
		return new RootSet( rs.getLemma(), rs.getFeatures(), rs.getOriginalWord());
	}

	/**
	 * Does not get highest frequency yet. Just gets the last (i think) found non-NULL stem
	 * @param finishedTree
	 * @param originalWord
	 * @return
	 */
	public RootSet getHighestFreqRoot(ArrayList<ArrayList<Branch>> finishedTree, String originalWord)
	{
		/* Result */
		String result = "";
		RootSet rootSet;
		/* Iterated variables */
		ArrayList<Branch> leaves = finishedTree.get( finishedTree.size()-1 );
		ArrayList<String> roots;
		/* To be used in finding the possible root */
		ArrayList<RootSet> prList 	= new ArrayList<>();
		String foundRoot 			= "";
		String foundFeatures 		= "";

		for ( Branch leaf : leaves )
		{
			if ( !leaf.getStem().getStemString().equalsIgnoreCase("null") )
			{
				foundRoot 		= leaf.getStem().getStemString();
				foundFeatures 	= leaf.getStem().getCombinedFeatures();
//				println(foundRoot + "=" + foundFeatures);
			}
		}

		rootSet = new RootSet( foundRoot, foundFeatures, originalWord );
		return rootSet;
	}

	/**
	 * Wag mong gamitin. Please lang.
	 * @param word
	 * pucha wag nga eh
	 */
	public void generatePISTree2(String word)
	{
		/* saving the trees */
		ArrayList<ArrayList<Branch>> tY = new ArrayList<>();
		ArrayList<Branch> tX 			= new ArrayList<>();
		ArrayList<Branch> newParents 	= new ArrayList<>();
		/* Children of the Root */
		Branch root, parent, prefixBranch, infixBranch, suffixBranch, tempParent;
		int expectedTreeWidth = 1;
		/* Initialize first stem */
		Stem rootStem 	= new Stem( word );
		root 			= new Branch( rootStem );

		parent = root;
		tX.add( parent );
		tY.add( tX );

		for ( int y = 0; y < tY.size(); y++ )
		{
//			println(tX.get(0).getStem().getStemString() + " - " + tY.size());
			tX = new ArrayList<>();

			ArrayList<Branch> tempX = tY.get( y );
//			println("tempX.size: " + tempX.size() );

			for( int x = 0; x < tempX.size(); x++ )
			{
				Stem nullStem = new Stem("NULL");
				Branch nullBranch = new Branch( nullStem );
				tempX.get(x).generateBranchChildren();

				tX.add( tempX.get(x).getPrefixBranch() );
				tX.add( tempX.get(x).getInfixBranch() );
				tX.add( tempX.get(x).getSuffixBranch() );

			}

			tY.add( tX );

			if( tY.size() > 3 )
			{
				break;
			}
		}

//		printTreeContent(tY);
	}


	/*
	 * ********************************************************************
	 *                             Other Utility 						  *
	 * ********************************************************************
	 */


	public void printTreeContent( ArrayList<ArrayList<Branch>> tY )
	{
		/* try to print contents of tree */
		for( int y = 0; y < tY.size(); y++ )
		{
			ArrayList<Branch> tempTree = tY.get(y);
			for( int x = 0; x < tempTree.size(); x++ )
			{
				print( tempTree.size() +"-");
				print( tempTree.get(x).getStem().getStemString() +"/"+ tempTree.get(x).getStem().getCombinedFeatures() + " ");
				if( (x+1) % 3 == 0 )
				{
					print("|| ");
				}
			}
			println("");
		}
	}


	public void testTree()
	{
		/* mmhmm */
		ArrayList<ArrayList<Branch>> treeY = new ArrayList<>();
		ArrayList<Branch> treeX = new ArrayList<>();
		/* user given */
		String word = "pinahintayan";
		Stem stem = new Stem(word);
		/* treeStruct contents */
		Branch p, i, s, root;
		/* root of tree */
		root = new Branch(stem);
		treeX.add( root );
		treeY.add( treeX );
		/* first row of children */
		treeX = null;
		treeX = new ArrayList<>();
		root.generateBranchChildren();
		p = root.getPrefixBranch();
		i = root.getInfixBranch();
		s = root.getSuffixBranch();
		treeX.add(p);
		treeX.add(i);
		treeX.add(s);
		treeY.add(treeX);
		/* try to print contents of tree */
		for( int y = 0; y < treeY.size(); y++ )
		{
			ArrayList<Branch> tempTree = treeY.get(y);
			for( int x = 0; x < tempTree.size(); x++ )
			{
//				print( tempTree.size() +"-");
				print( tempTree.get(x).getStem().getStemString() +"[");
//				println( tempTree.get(x).getStem().getAffix());
				print( tempTree.get(x).getDirectionHistory() + "]");
			}
			println("");
		}
	}


	public void testCommands()
	{
		/* mmhmm */
		ArrayList<ArrayList<Branch>> treeY = new ArrayList<>();
		ArrayList<Branch> treeX = new ArrayList<>();
		/* user given */
		String word = "pinahintayan";
		/* treeStruct contents */
		Branch p, i, s, root;

		Stem stem = new Stem(word);

		root = new Branch(stem);
		root.generateBranchChildren();
		p = root.getPrefixBranch();
		i = root.getInfixBranch();
		s = root.getSuffixBranch();

		/* Prefix */
		println(p.getStem().getStemString());
		/* Infix */
		println(i.getStem().getStemString());
		println(i.getDirectionHistory());
		/* Suffix */
		println(s.getStem().getStemString());
	}


	public static class Test
	{
		AffixCommand ac = new AffixCommand();

		public static void main(String[] args)
		{
			/* don't change this line */
			Test t = new Test();
			/* write below */
//			t.testCreateBranch();
			t.original();
		}

		public void original()
		{
			ac.generatePISTree3("pinapahintayan");
//			ac.generatePISTree3("marami"); // works
//			ac.generatePISTree3("duguan"); // works
//			ac.generatePISTree3("dugo-duguan"); // works well
//			ac.generatePISTree3("pinakamarami"); // works
//			ac.generatePISTree3("mabangung-mabango"); // works well
//			ac.generatePISTree3("mabango-bango");
//			ac.generatePISTree3("tawanan"); // works pero must reconsider suffix "nan"
//			ac.generatePISTree3("napapanood");
//			ac.generatePISTree3("gagawin");
//			ac.generatePISTree3("ipagluto");
		}

		public void testCreateBranch()
		{

		}
	}


}
