package fr.tse.fise2.hpp.Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import fr.tse.fise2.hpp.Objects.ContagionChain;

public class FileUtils {

	/**
	 * Method to evaluate each file, create the chains and write the results in another file
	 * 
	 * @param rowsNumber : number of cases to treat 
	 * @throws IOException
	 */
	public static void writeResults(int rowsNumber,String writtingPath) throws IOException {

		// Create an array to store our data
		ArrayList<ContagionChain> arrayOfContagionChains = new ArrayList<ContagionChain>();

		// Our BufferedReader
		BufferedReader bufferReaderFrance = new BufferedReader(new FileReader(new FileUtils().getClass()
				.getClassLoader().getResource("Data_HPP/" + Integer.toString(rowsNumber) + "/France.csv").getFile()));
		BufferedReader bufferReaderSpain = new BufferedReader(new FileReader(new FileUtils().getClass().getClassLoader()
				.getResource("Data_HPP/" + Integer.toString(rowsNumber) + "/Spain.csv").getFile()));
		BufferedReader bufferReaderItaly = new BufferedReader(new FileReader(new FileUtils().getClass().getClassLoader()
				.getResource("Data_HPP/" + Integer.toString(rowsNumber) + "/Italy.csv").getFile()));

		// Initialize global buffers
		// France
		ArrayList<String> dataOnLineFrance;
		int idFrance = rowsNumber;
		try {
			dataOnLineFrance = DataTreatment.getInformationOnLine(bufferReaderFrance.readLine());
			idFrance = Integer.parseInt(dataOnLineFrance.get(0));
		} catch (Exception e) {
			// TODO: handle exception
			dataOnLineFrance = null;
		}

		// Spain
		ArrayList<String> dataOnLineSpain;
		int idSpain = rowsNumber;
		try {
			dataOnLineSpain = DataTreatment.getInformationOnLine(bufferReaderSpain.readLine());
			idSpain = Integer.parseInt(dataOnLineSpain.get(0));
		} catch (Exception e) {
			// TODO: handle exception
			dataOnLineSpain = null;
		}

		// Italy
		ArrayList<String> dataOnLineItaly;
		int idItaly = rowsNumber;
		try {
			dataOnLineItaly = DataTreatment.getInformationOnLine(bufferReaderItaly.readLine());
			idItaly = Integer.parseInt(dataOnLineItaly.get(0));
		} catch (Exception e) {
			// TODO: handle exception
			dataOnLineItaly = null;
		}

		// Execute thread in good order
		try {
			// Create our writer
			BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(writtingPath));

			long startTime = System.nanoTime();

			int idRow = 0;
			while (dataOnLineFrance != null || dataOnLineItaly != null || dataOnLineSpain != null) {

				String countryNameToRead = DataTreatment.getCountryLowerId(idFrance, idSpain, idItaly);
				ArrayList<String> dataOnLineCountry = null;
				if (countryNameToRead.equals("France")) {
					dataOnLineCountry = dataOnLineFrance;
					try {
						dataOnLineFrance = DataTreatment.getInformationOnLine(bufferReaderFrance.readLine());
						idFrance = Integer.parseInt(dataOnLineFrance.get(0));
					} catch (Exception e) {
						// TODO: handle exception
						idFrance = rowsNumber;
						dataOnLineFrance = null;
					}
				} else if (countryNameToRead.equals("Spain")) {
					dataOnLineCountry = dataOnLineSpain;
					try {
						dataOnLineSpain = DataTreatment.getInformationOnLine(bufferReaderSpain.readLine());
						idSpain = Integer.parseInt(dataOnLineSpain.get(0));
					} catch (Exception e) {
						// TODO: handle exception
						idSpain = rowsNumber;
						dataOnLineSpain = null;
					}
				} else {
					dataOnLineCountry = dataOnLineItaly;
					try {
						dataOnLineItaly = DataTreatment.getInformationOnLine(bufferReaderItaly.readLine());
						idItaly = Integer.parseInt(dataOnLineItaly.get(0));
					} catch (Exception e) {
						// TODO: handle exception
						idItaly = rowsNumber;
						dataOnLineItaly = null;
					}
				}
				// Idea to optimize is to split operation in 3 thread to run when countryName is
				// the right one // read stack
				if (dataOnLineCountry != null) {
					ContagionArray.updateDataContagionChains(arrayOfContagionChains, dataOnLineCountry,
							countryNameToRead);
					ArrayList<ContagionChain> arrayOfTopThreeChains = DataTreatment
							.getTopThreeContagionChains(arrayOfContagionChains);
					// Write results in a file -- before format text as in subject
					String resultOnRow = "";
					for (int i = 0; i < arrayOfTopThreeChains.size(); i++) {
						resultOnRow += arrayOfTopThreeChains.get(i).getCountryName() + ", ";
						resultOnRow += arrayOfTopThreeChains.get(i).getRootId() + ", ";
						resultOnRow += arrayOfTopThreeChains.get(i).getContagionScore() + ", ";
					}
					bufferWriter.write(resultOnRow + "\n");
				}
				idRow++;
				if (idRow % 10000 == 0) {
					long estimatedTime = System.nanoTime() - startTime;
					System.out.println("Time estimation ligne " + Integer.toString(idRow) + " : "
							+ estimatedTime / Math.pow(10, 9) + "s"); // Get estimation in seconds
				}
			}
			bufferWriter.close();

		} catch (Exception e) {
			System.out.println(e);
		}
		bufferReaderFrance.close();
		bufferReaderSpain.close();
		bufferReaderItaly.close();
	}
}
