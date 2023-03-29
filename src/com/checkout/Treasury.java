package com.checkout;

import java.io.IOException;
import java.util.List;

import com.utils.FileSystem;

public class Treasury {
	private static Treasury instance;
	private final String FILE_NAME;
	private Double cash = 0d;

	private Treasury() { FILE_NAME = ""; }

	private Treasury(String fileName) {
		FILE_NAME = String
			.format("%1$s/treasury.csv", fileName);
	}

	public Double getCash() {
		return this.cash;
	}

	public void addCash(double ammount) throws IOException {
		this.cash += ammount;
		sendCashToTreasury();

		System.out.println("Updated cash: " + cash);
	}

	public void withdrawCash(double ammount) throws IOException {
		this.cash -= ammount;
		sendCashToTreasury();

		System.out.println("Updated cash: " + cash);
	}

	public void sendCashToTreasury() throws IOException {
		String cashFormatted = String.format("%.2f", this.cash);
		FileSystem.save(
				FILE_NAME, List.of("ammount", cashFormatted));
	}

	public void getCashInfoFromTreasury() throws IOException {
		String treasuryCash = FileSystem.readFile(FILE_NAME).get(1);

		if(treasuryCash != null) {
			cash += Double.parseDouble(treasuryCash);
		}
	}

	public static Treasury getInstance(String fileName) {
		if(instance == null)
			instance = new Treasury(fileName);

		return instance;
	}
}
