package client;

import java.util.LinkedList;
/**
 * The lists of words to be displayed.
 * @author Stephanie Garcia Ribot
 *
 */
public class WordList {
	public static final int WORD = 0;
	public static final int REVERSE_WORD = 1;
	public static final int WORD_POINTS = 2;
	public static final int WORD_NOT_FOUND = -1;
	public static int listLowerBound = 0;
	private static int listUpperBound = 9;
	
	static LinkedList<LinkedList<String>> listOfLists = new LinkedList<LinkedList<String>>();

	public WordList() {
		buildList();
	}
	
	private static LinkedList<LinkedList<String>> buildList() {
		LinkedList<String> word0 = new LinkedList<String>();
		word0.add("pizza");
		word0.add("azzip");
		word0.add("5");
		listOfLists.add(word0);
		
		
		LinkedList<String> word1 = new LinkedList<String>();
		word1.add("melon");
		word1.add("nolem");
		word1.add("5");
		listOfLists.add(word1);
		
		LinkedList<String> word2 = new LinkedList<String>();
		word2.add("carrot");
		word2.add("torrac");
		word2.add("6");
		listOfLists.add(word2);
		
		LinkedList<String> word3 = new LinkedList<String>();
		word3.add("mofongo");
		word3.add("ognofom");
		word3.add("7");
		listOfLists.add(word3);
		
		LinkedList<String> word4 = new LinkedList<String>();
		word4.add("pepperoni");
		word4.add("inoreppep");
		word4.add("9");
		listOfLists.add(word4);
		
		LinkedList<String> word5 = new LinkedList<String>();
		word5.add("zucchini");
		word5.add("inihccuz");
		word5.add("8");
		listOfLists.add(word5);
		
		LinkedList<String> word6 = new LinkedList<String>();
		word6.add("squash");
		word6.add("hsauqs");
		word6.add("6");
		listOfLists.add(word6);
		
		LinkedList<String> word7 = new LinkedList<String>();
		word7.add("cabbage");
		word7.add("egabbac");
		word7.add("7");
		listOfLists.add(word7);
		
		LinkedList<String> word8 = new LinkedList<String>();
		word8.add("pasteles");
		word8.add("seletsap");
		word8.add("8");
		listOfLists.add(word8);
		
		LinkedList<String> word9 = new LinkedList<String>();
		word9.add("tembleque");
		word9.add("euqelbmet");
		word9.add("9");
		listOfLists.add(word9);
		
		LinkedList<String> word10 = new LinkedList<String>();
		word10.add("batata");
		word10.add("atatab");
		word10.add("6");
		listOfLists.add(word10);
		
		LinkedList<String> word11 = new LinkedList<String>();
		word11.add("melocoton");
		word11.add("notocolem");
		word11.add("9");
		listOfLists.add(word11);
		
		LinkedList<String> word12 = new LinkedList<String>();
		word12.add("apple");
		word12.add("elppa");
		word12.add("5");
		listOfLists.add(word12);
		
		LinkedList<String> word13 = new LinkedList<String>();
		word13.add("grape");
		word13.add("eparg");
		word13.add("5");
		listOfLists.add(word13);
		
		LinkedList<String> word14 = new LinkedList<String>();
		word14.add("avocado");
		word14.add("odacova");
		word14.add("7");
		listOfLists.add(word14);
		
		LinkedList<String> word15 = new LinkedList<String>();
		word15.add("gandules");
		word15.add("seludnag");
		word15.add("8");
		listOfLists.add(word15);
		
		LinkedList<String> word16 = new LinkedList<String>();
		word16.add("pear");
		word16.add("raep");
		word16.add("4");
		listOfLists.add(word16);
		
		LinkedList<String> word17 = new LinkedList<String>();
		word17.add("plum");
		word17.add("mulp");
		word17.add("4");
		listOfLists.add(word17);
		
		LinkedList<String> word18 = new LinkedList<String>();
		word18.add("pasteles");
		word18.add("seletsap");
		word18.add("8");
		listOfLists.add(word18);
		
		LinkedList<String> word19 = new LinkedList<String>();
		word19.add("raisin");
		word19.add("nisiar");
		word19.add("6");
		listOfLists.add(word19);
		
		
		return(listOfLists); 
	}
	/**
	 * Verifies if attacking word is on the list.
	 * @param word
	 * @return i The word index or -1 if word is not found in the list.
	 */
	
	public int isAttackWordInList(String word) {
		for(int i = listLowerBound; i <= listUpperBound; i++) {
			if(word.equals(listOfLists.get(i).get(WordList.WORD))) {
				return i;
			}
		}
		return WordList.WORD_NOT_FOUND;
	}
	
	/**
	 * Verifies if defending word is on the list.
	 * @param word
	 * @return i The word index or -1 if word is not found in the list.
	 */
	
	public int isDefendWordInList(String word) {
		for(int i = 0; i < 19; i++) {
			if(word.equals(listOfLists.get(i).get(WordList.REVERSE_WORD))) {
				return i;
			}
		}
		return WordList.WORD_NOT_FOUND;
	}
	/**
	 * Sets the lower bound
	 * @param listLowerBound
	 */
	public static void setListLowerBound(int listLowerBound) {
		WordList.listLowerBound = listLowerBound;
	}
	/**
	 * Sets the upper bound
	 * @param listUpperBound
	 */
	public static void setListUpperBound(int listUpperBound) {
		WordList.listUpperBound = listUpperBound;
	}

}

