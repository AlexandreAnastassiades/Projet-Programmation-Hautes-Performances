package fr.tse.fise2.hpp.Utils;

import java.util.ArrayList;

import fr.tse.fise2.hpp.Objects.ContagionChain;

public class ContagionArray {

	/**
	 * Method to treat a new case and to add it to the correct chain. It also update the top three of contagious score
	 * 
	 * @param arrayOfContagionChains : list of the chains
	 * @param dataOnLineCountry : data corresponding to the new case
	 * @param countryNameToRead : name of the origin country of the new case
	 */
	public static void updateDataContagionChains(ArrayList<ContagionChain> arrayOfContagionChains,	ArrayList<String> dataOnLineCountry, String countryNameToRead) {

		// Browse ArrayList in END_TO_START Mode
		int lengthArray = arrayOfContagionChains.size() - 1;

		// Boolean to know if the contagious parent or root have been found in the
		// different ContagionChain
		boolean globalNoAddToChain = true;
		String idPerson = dataOnLineCountry.get(0);
		Double currentTime = Double.parseDouble(dataOnLineCountry.get(4));
		String idContagious = dataOnLineCountry.get(5);

		// Initialize three buffer for our top three to avoid useless operations
		long bufferTopOne = 0;
		long bufferTopTwo = 0;
		long bufferTopThree = 0;

		int i = lengthArray;

		while (i > -1) {

			// Get current information of chain i
			ContagionChain currentContagionChain = arrayOfContagionChains.get(i);
			String idRootChain = currentContagionChain.getRootId();

			// boolean goes to true if idRoot == idContaminedBy or else in the otherwise
			boolean isRootId = idContagious.equals(idRootChain);
			
			
			// Update data of current chain and get boolean which indicates if we found the right chain
			boolean isAnIdOfChain = currentContagionChain.deleteContagionsTooOld(currentTime, idContagious, bufferTopThree);
			boolean needToAddToChain = (isAnIdOfChain || isRootId);
			long currrentContagionScore = currentContagionChain.getContagionScore();

			// We need to add to the chain because chain exists again (score >0)
			if (needToAddToChain && currrentContagionScore != 0) {
				// Add new people to the chain and update score
				currentContagionChain.addNewPeopleToChain(idPerson, currentTime);
				currrentContagionScore += 10;
				currentContagionChain.setContagionScore(currrentContagionScore);
				// We add the new people in the chain --> so we haven't to create a new chain
				globalNoAddToChain = false;

			}
			// Chain is too old
			else if (currrentContagionScore == 0) {
				// Score == 0 --> remove the current chain because too old
				arrayOfContagionChains.remove(i);
			}

			// Iterates my top three buffer
			if (currrentContagionScore >= bufferTopOne) {
				bufferTopThree = bufferTopTwo;
				bufferTopTwo = bufferTopOne;
				bufferTopOne = currrentContagionScore;
			} else if (currrentContagionScore >= bufferTopTwo) {
				bufferTopThree = bufferTopTwo;
				bufferTopTwo = currrentContagionScore;
			} else if (currrentContagionScore >= bufferTopThree) {
				bufferTopThree = currrentContagionScore;
			}
			i--;
		}
		// The contagious id wasn't in the current Array of Contagion Chain --> because
		// too old or not corresponding
		// Create a new chain --> idRoot = idPersonn
		if (globalNoAddToChain) {
			arrayOfContagionChains.add(new ContagionChain(idPerson, currentTime, countryNameToRead));
		}

	}
}
