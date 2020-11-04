package fr.tse.fise2.hpp.AppLauncher;

import java.io.IOException;
import java.util.Scanner;

import fr.tse.fise2.hpp.Utils.FileUtils;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		// Small interface to enter the value of the case to be analyzed
		System.out.println("Veuillez saisir un nombre de ligne : 20, 5.000 ou 1.000.000");
		Scanner scannerInput = new Scanner(System.in);
		String rowsNumberInput = scannerInput.next();
		// Check if the input value is correct
		while (!rowsNumberInput.equals("20") && !rowsNumberInput.equals("5000") && !rowsNumberInput.equals("1000000")) {
			System.out.println("Veuillez saisir un nombre de ligne : 20, 5.000 ou 1.000.000");
			rowsNumberInput = scannerInput.next();
		}
		String defaultPath = new Main().getClass().getClassLoader()
				.getResource("Results/contagion" + rowsNumberInput + ".csv").getFile();
		
		System.out.println("Voulez vous utilisez " + defaultPath + " comme pah par défaut pour l'écriture ? (yes/no)");
		String answer = scannerInput.next();
		while (!answer.toLowerCase().equals("yes") && !answer.toLowerCase().equals("y") && !answer.toLowerCase().equals("no") && !answer.toLowerCase().equals("n")) {
			System.out.println("Voulez vous utilisez " + defaultPath + " comme pah par défaut pour l'écriture ? (yes/no)");
			answer = scannerInput.next();
		}

		if (answer.toLowerCase().equals("no") || answer.toLowerCase().equals("n")) {
			System.out.println("Veuillez saisir un chemin par défaut pour l'écrire de vos résultats :");
			defaultPath =  scannerInput.next();
		}
		scannerInput.close();
				
		// Convert rowsNumber into an Integer to call function writeResults()
		int rowsNumber = Integer.parseInt(rowsNumberInput);

		// Measure an estimation of algorithm running
		long startTime = System.nanoTime();
		FileUtils.writeResults(rowsNumber, defaultPath);
		long estimatedTime = System.nanoTime() - startTime;
		// Get estimation in seconds
		System.out.println("Time estimation : " + estimatedTime / Math.pow(10, 9) + " s");
		// Get estimation in minutes
		System.out.println("Time estimation : " + estimatedTime / Math.pow(10, 9) / 60 + " min");
		// Get estimation in hours
		System.out.println("Time estimation : " + estimatedTime / Math.pow(10, 9) / 3600 + " h");
	}

}
