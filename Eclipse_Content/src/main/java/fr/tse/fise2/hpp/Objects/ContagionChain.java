package fr.tse.fise2.hpp.Objects;

import java.util.ArrayList;

import fr.tse.fise2.hpp.Utils.TimeUtils;

public class ContagionChain {

	// Definition of an chain according to ourselves
	private String rootId;
	private ArrayList<String> idOfContagiousPeople;
	private ArrayList<Double> contagionTimes;
	private long contagionScore;
	private String countryName;

	/**
	 * Constructor of the object ContagionChain
	 * 
	 * @param i : ID of the first member of the chain
	 * @param d : time of contamination of the first member of the chain
	 * @param countryName : France / Italy / Spain
	 */
	public ContagionChain(String i, double d, String countryName) {
		super();
		this.countryName = countryName;
		this.rootId = i;
		this.idOfContagiousPeople = new ArrayList<String>();
		this.idOfContagiousPeople.add(i);
		this.contagionTimes = new ArrayList<Double>();
		this.contagionTimes.add(d);
		this.contagionScore = 10;
	}

	public String getRootId() {
		return rootId;
	}

	public void setRootId(String rootId) {
		this.rootId = rootId;
	}

	public ArrayList<String> getIdOfContagiousPeople() {
		return idOfContagiousPeople;
	}

	public void setIdOfContagiousPeople(ArrayList<String> idOfContagiousPeople) {
		this.idOfContagiousPeople = idOfContagiousPeople;
	}

	public ArrayList<Double> getContagionTimes() {
		return contagionTimes;
	}

	public void setContagionTimes(ArrayList<Double> contagionTimes) {
		this.contagionTimes = contagionTimes;
	}

	public long getContagionScore() {
		return contagionScore;
	}

	public void setContagionScore(long contagionScore) {
		this.contagionScore = contagionScore;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (contagionScore ^ (contagionScore >>> 32));
		result = prime * result + ((contagionTimes == null) ? 0 : contagionTimes.hashCode());
		result = prime * result + ((countryName == null) ? 0 : countryName.hashCode());
		result = prime * result + ((idOfContagiousPeople == null) ? 0 : idOfContagiousPeople.hashCode());
		result = prime * result + ((rootId == null) ? 0 : rootId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContagionChain other = (ContagionChain) obj;
		if (contagionScore != other.contagionScore)
			return false;
		if (contagionTimes == null) {
			if (other.contagionTimes != null)
				return false;
		} else if (!contagionTimes.equals(other.contagionTimes))
			return false;
		if (countryName == null) {
			if (other.countryName != null)
				return false;
		} else if (!countryName.equals(other.countryName))
			return false;
		if (idOfContagiousPeople == null) {
			if (other.idOfContagiousPeople != null)
				return false;
		} else if (!idOfContagiousPeople.equals(other.idOfContagiousPeople))
			return false;
		if (rootId == null) {
			if (other.rootId != null)
				return false;
		} else if (!rootId.equals(other.rootId))
			return false;
		return true;
	}
	
	/**
	 * Method to add a new member to the chain
	 * 
	 * @param idPerson : ID of the new member
	 * @param currentTime : time of contamination of the new member
	 */
	public void addNewPeopleToChain(String idPerson, Double currentTime) {
		// TODO Auto-generated method stub
		this.idOfContagiousPeople.add(idPerson);
		this.contagionTimes.add(currentTime);
	}

	/**
	 * Method to erase of the chains the members who are too old to contaminate anyone
	 * 
	 * @param currentTime : time of contamination of the last case treated
	 * @param idContaminedBy : ID of the person who contaminated the new case
	 * @param bufferTopThree : buffer containing the lowest contamination rate of the current top three of the chains
	 * @return
	 */
	public boolean deleteContagionsTooOld(Double currentTime, String idContaminedBy, long bufferTopThree) {
		// Initialize boolean to false which indicates if idContaminedBy has been found
		// in this contagionChain
		boolean isAnIdOfChain = false;

		// Browse ArrayList in END_TO_START Mode
		int numberOfContagiousPeople = this.idOfContagiousPeople.size() - 1;

		// Initialize the new contagion score to zero
		int newContagionScore = 0;

		int j = numberOfContagiousPeople;
		while (j > -1) {
			// Current Contagious id correspond to idContaminedBy --> we found it, so
			// boolean goes to true
			if (this.idOfContagiousPeople.get(j).equals(idContaminedBy)) {
				isAnIdOfChain = true;
			}

			// If a contagious id is too old, we remove it and the following elements
			Double timeNumberJ = this.contagionTimes.get(j);
			if (TimeUtils.isTooOld(timeNumberJ, currentTime)) {
				deleteElementsFromJtoZero(j);
				j = -1; // End of the loop
			} else if (!TimeUtils.isMoreSevenDayOld(timeNumberJ, currentTime)) {
				newContagionScore += 10;
				j--;
			} else {
				newContagionScore += 4;
				j--;
			}
			// Forecast of newContagionScore is inferior to the bufferTopThree --> we
			// needn't calculate the score
			if (bufferTopThree > (numberOfContagiousPeople * 10 + 1) && newContagionScore > 0) {
				j = -1; // End of he loop
			}
		}

		// Update score and return information on if idContaminedBy is present or not
		this.setContagionScore(newContagionScore);
		return isAnIdOfChain;
	}

	/**
	 * Method to erase elements and time of contamination from the beginning to a specified element of the chain
	 * 
	 * @param j : index of the last element to erase
	 */
	public void deleteElementsFromJtoZero(int j) {
		// TODO Auto-generated method stub
		for (int k = j; k > -1; k--) {
			this.idOfContagiousPeople.remove(k);
			this.contagionTimes.remove(k);
		}
	}

	/**
	 * Method to print the different data of the chain
	 * 
	 * @return a list of the data of the chain
	 */
	public String toStringDisplay() {
		return ("{ countryName: " + this.countryName + ", contagionScore: " + this.contagionScore + ", rootId: "
				+ this.rootId + ", contagionTimes" + this.contagionTimes.toString() + ", idOfContagiousPeople: "
				+ this.idOfContagiousPeople.toString() + " }");
	}

}
