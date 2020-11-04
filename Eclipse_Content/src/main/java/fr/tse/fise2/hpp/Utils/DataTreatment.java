package fr.tse.fise2.hpp.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import fr.tse.fise2.hpp.Objects.ContagionChain;

public class DataTreatment {

	/**
	 * Method to set a line of data at the good format
	 * 
	 * @param line : line of data to set
	 * @return the line of data with the good format
	 */
	public static ArrayList<String> getInformationOnLine(String line) {
		// Read the line and split on type ', ' --> clean specialChar
		String tamponLine = line.replaceAll("[\n\" ]", "");

		// Get information in ArrayList<String>
		return new ArrayList<String>(Arrays.asList(tamponLine.split(",")));
	}

	/**
	 * Method to establish the top three of the most contagious chains
	 * 
	 * @param arrayOfContagionChains : list of all the chains
	 * @return the list of the three most contagious chains
	 */
	public static ArrayList<ContagionChain> getTopThreeContagionChains(ArrayList<ContagionChain> arrayOfContagionChains) {
		// TODO Auto-generated method stub
		ArrayList<ContagionChain> arrayOfTopThreeChains = new ArrayList<ContagionChain>();
		int numberOfChains = arrayOfContagionChains.size();
		int indexOne = -1;
		int indexTwo = -1;
		int indexThree = -1;
		long scoreOne = 0;
		long scoreTwo = 0;
		long scoreThree = 0;
		for (int i = 0; i < numberOfChains; i++) {
			long scoreCurrentChain = arrayOfContagionChains.get(i).getContagionScore();
			if (scoreCurrentChain > scoreOne) {
				scoreThree = scoreTwo;
				indexThree = indexTwo;
				scoreTwo = scoreOne;
				indexTwo = indexOne;
				scoreOne = scoreCurrentChain;
				indexOne = i;
			} else if (scoreCurrentChain > scoreTwo) {
				scoreThree = scoreTwo;
				indexThree = indexTwo;
				scoreTwo = scoreCurrentChain;
				indexTwo = i;
			} else if (scoreCurrentChain > scoreThree) {
				scoreThree = scoreCurrentChain;
				indexThree = i;
			}
		}
		if (scoreThree > 0) {
			arrayOfTopThreeChains.add(arrayOfContagionChains.get(indexOne));
			arrayOfTopThreeChains.add(arrayOfContagionChains.get(indexTwo));
			arrayOfTopThreeChains.add(arrayOfContagionChains.get(indexThree));
		} else if (scoreTwo > 0) {
			arrayOfTopThreeChains.add(arrayOfContagionChains.get(indexOne));
			arrayOfTopThreeChains.add(arrayOfContagionChains.get(indexTwo));
		} else if (scoreOne > 0) {
			arrayOfTopThreeChains.add(arrayOfContagionChains.get(indexOne));
		}

		return arrayOfTopThreeChains;
	}

	/**
	 * Method to determinate the lowest ID of the three countries next line
	 * 
	 * @param idFrance : ID of the line in the file for France
	 * @param idSpain : ID of the line in the file for Spain
	 * @param idItaly : ID of the line in the file for Italy
	 * @return the name of the country with the lowest ID
	 */
	public static String getCountryLowerId(int idFrance, int idSpain, int idItaly) {
		// TODO Auto-generated method stub
		int minId = Math.min(idFrance, Math.min(idSpain, idItaly));

		if (minId == idFrance) {
			return "France";
		} else if (minId == idSpain) {
			return "Spain";
		} else {
			return "Italy";
		}
	}
}
