import java.util.Scanner;
import java.io.PrintStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class project5main {

	public static void main(String[] args) {
		
		File inFile = new File(args[0]);
		File outFile = new File(args[1]);
		
		Scanner reader;
		try {
			reader = new Scanner(inFile);
		}
		catch(FileNotFoundException e){
			return;
		}
		
		PrintStream printer;
		try {
			printer = new PrintStream(outFile);
		}
		catch(IOException e) {
			reader.close();
			return;
		}
		
		//Reading input
		String[] productTypes = reader.nextLine().split(" ");
		int productNum = productTypes.length; // Total product number
		
		int[] timeOnA = new int[productNum];
		int[] timeOnB = new int[productNum];
		int[] profits = new int[productNum];
		int[] startTimes = new int[productNum];
		
		for(int i=0;i<productNum;i++ ) {
			timeOnA[i] = reader.nextInt();
		}
		for(int i=0;i<productNum;i++ ) {
			timeOnB[i] = reader.nextInt();
		}
		for(int i=0;i<productNum;i++ ) {
			profits[i] = reader.nextInt();
		}
		for(int i=0;i<productNum;i++ ) {
			startTimes[i] = reader.nextInt();
		}
		
		//Calculating end times
		ArrayList<Integer> endTimes = new ArrayList<Integer>();
		ArrayList<Integer> newEndTimes = new ArrayList<Integer>();
		for(int i=0;i<productNum;i++) {
			if(productTypes[i].equals("s")) {
				endTimes.add(startTimes[i] + timeOnA[i]);
				newEndTimes.add(startTimes[i] + timeOnA[i]);
			}
			else {// if the product is l type
				endTimes.add(startTimes[i] + timeOnB[i]);
				newEndTimes.add(startTimes[i] + timeOnB[i]);
			}
		}
		//Sorting end times and adjusting startTimes and profits accordingly
		Collections.sort(newEndTimes);
		
		int[] newStartTimes = new int[productNum];
		int[] newProfits = new int[productNum];
		
		for(int newIndex=0;newIndex<productNum;newIndex++) {
			int oldIndex = endTimes.indexOf(newEndTimes.get(newIndex));
			endTimes.set(oldIndex,0);
			newStartTimes[newIndex] = startTimes[oldIndex];
			newProfits[newIndex] = profits[oldIndex];
		}
		
		startTimes = newStartTimes;
		profits = newProfits;
		endTimes = newEndTimes;
		
		//Solving problem
		
		int[] maxProfits = new int[productNum];
		maxProfits[0] = profits[0];
		for(int i=1;i<productNum;i++) {
			int j=i-1;
			int preMaxProfit = 0; // Previous max profit that doesn't overlap with the current one
			while(0 <= j && startTimes[i] < endTimes.get(j) ) {
				j--;
			}
			if(0 <= j) { //There is a possibility that j is negative so we check
				preMaxProfit = maxProfits[j];
			}
			
			if(maxProfits[i-1] < preMaxProfit + profits[i]) {
				maxProfits[i] = preMaxProfit + profits[i];
			}
			else {
				maxProfits[i] = maxProfits[i-1];
			}
		}
		
		printer.print(maxProfits[productNum-1]);
		
		reader.close();
		printer.close();
	}
}
