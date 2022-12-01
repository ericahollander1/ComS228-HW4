package edu.iastate.cs228.hw4;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * Message Tree class decodes using a given order in a file
 * In addition the file has a message inside it encrypted in 1 and 0s
 * Using the message tree, we will decode the message from the file
 * @author Erica Hollander
 *
 */
public class MsgTree {
	
	//The character that are the leaves
	public char payloadChar; //the leaf
	
	//pointer to the left child of the node
	public MsgTree left;
	
	//pointer to the right child of the node
	public MsgTree right;
	
	//stores the original tree 
	public static MsgTree originalTree;
	
	//Need static char idx to  the tree string for recursive solution 
	//tracks the location within the tree
	private static int staticCharIdx = -1;
	
	//index of the encrypted message
	private static int counter = 0;
	
	//holds the encoding String so it can be used for the statistics
	private static String myMsg ="";
	
	//cleans up my double values in statistics by rounding them to two decimals
	private static DecimalFormat df = new DecimalFormat("0.00");
	
	/**
	 * 
	 * Constructor building the tree from a string
	 * @param encodingString
	 */
	public MsgTree(String encodingString){
		 //recursive solution
		if(staticCharIdx < encodingString.length()-1) { //base case
			char[] array = encodingString.toCharArray();
			
			staticCharIdx++;
			
			if(array[staticCharIdx] != '^') {
				payloadChar = array[staticCharIdx];
				//System.out.println(payloadChar);

			}
			else {
				payloadChar = array[staticCharIdx];
				left = new MsgTree(encodingString);
				right = new MsgTree(encodingString);
			}
		}
		
	}
	
	/**
	 * Constructor for a single node with null children
	 * only needed if you do it iteratively
	 * @param payloadChar
	 */
	public MsgTree(char payloadChar){
		//not needed for recursion
		this.payloadChar = payloadChar;
	} 
	
	

	/**
	 * Recursive preoder transversal of the MsgTree
	 * Method to print characters and their binary 
	 * @param root
	 * @param code
	 */
	public static void printCodes(MsgTree root, String code){
		if(root.left == null && root.right == null) {
			if(root.payloadChar == '\n') {
				System.out.println("\\n\t"+ code);
			}
			else {
				System.out.println(root.payloadChar + "\t" + code);
			}
		}
		else {
			printCodes(root.left, code+"0");
			printCodes(root.right, code+"1");
		}
	}
	
	
	/**
	 * Prints the decoded message to the console
	 * @param codes
	 * @param msg
	 */
	public static void decode(MsgTree codes, String msg) {
		//TODO
		MsgTree current = codes;
		for(int i = 0; i < msg.length(); i++) {
			if(current.left == null && current.right == null) {
				//System.out.print(current.payloadChar);
				myMsg += current.payloadChar;
				current = codes; 
				//i--;
			}

			if(msg.charAt(i)== '0') {
				//System.out.print(codes.payloadChar);
				current = current.left;
					
					
				}
			else if(msg.charAt(i)== '1') {
				current = current.right;
					
			}
		}
		if(current.payloadChar != '^') {
			//System.out.println(current.payloadChar);
			myMsg += current.payloadChar;
		}	
		System.out.println(myMsg);
	}
	
	/**
	 * Printing out the statistics for extra credit
	 * 
	 * @param codesZeroOne
	 * @param msgTree
	 */
	public static void statistics(String codesZeroOne, String msgTree) {
		//The space savings calculation assumes that an uncompressed character is encoded
		//with 16 bits. It is defined as (1 – compressedBits/uncompressedBits)*100.
		int numbOfChars = 0;
		for(int i = 0; i < msgTree.length(); i++ ) {
			if(msgTree.charAt(i) != '^')
				numbOfChars++;
		}
//		System.out.println(numbOfChars);
		double avgBitsChar = (double) codesZeroOne.length()/ numbOfChars;
		int compressed = codesZeroOne.length();
		int uncompressed = myMsg.length()*16;
		double spaceSavings = (1- (double)compressed/uncompressed)*100;

		System.out.println("Avg bits/char: " + df.format((double)compressed/myMsg.length()));
		System.out.println("Total characters: " + myMsg.length());
		System.out.println("Space savings: " + df.format(spaceSavings) + "%");
	}
}
