package edu.iastate.cs228.hw4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Class containing my main method that tests my MsgTree 
 * @author ericahollander
 *
 */
public class TreeTest {
	public static void main(String[] args) {
		
		System.out.println("Please enter filename to decode: ");
		String binTree = "";
		//scanning in the file
		Scanner scan = new Scanner(System.in);
		String filename = scan.nextLine();
		File file = new File(filename);
		scan.close();
		
		try {
			Scanner scan2 = new Scanner(file);
			Scanner scan3 = new Scanner(file).useDelimiter("\n");
			
			//count number of lines in the file
			int count = 0;
			while(scan2.hasNextLine()) {
				count++;
				scan2.nextLine();
				//System.out.println(count);
				
			}
			
			for(int i = 1; i < count; i++) { 
				if(i < count) {
					binTree += scan3.next();
					
					if(i < count -1) {
						binTree += '\n';
					}
				}
			}
			//the last line
			String codes = scan3.next();
			//System.out.println(codes);
			//System.out.println(binTree);
			
			MsgTree tree = new MsgTree(binTree);
			
			System.out.println("character code");
			System.out.println("-------------------------");
			MsgTree.originalTree = tree;
			MsgTree.printCodes(MsgTree.originalTree, "");
		
			//originalTree = tree;
			System.out.println("MESSAGE:");
			MsgTree.decode(tree, codes);
			scan2.close();
			scan3.close();
			
			System.out.println("STATISTICS:");
			MsgTree.statistics(codes, binTree);
			
			
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
