package com.checkout;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.checkout.operations.*;
import com.exception.CheckoutInitException;
import com.product.*;
import com.utils.FileSystem;

public class Checkout {
	private final String filter = "" +
	"order_id,tea_id,unity_price,quantity,created_at,order_status";
	private final String INIT_LOG = ""+
		"Available operations:\n"+
		"0. Buy something;\n"+
		"1. Close order;\n"+
		"2. Reverse an Order;\n"+
		"3. Get daily revenues;\n\n"+
		"What's your need? ";
	
	private static Checkout instance;
	private final String FILE_NAME;

	private Menu menu;
	private Treasury treasury;
	public List<String> externalFile;

	private int operation = 0;

	private Checkout() { FILE_NAME =  ""; }

	private Checkout(String rootDir) {
		FILE_NAME = String.format("%1$s/orders.csv", rootDir);

		try {
			menu = Menu.getInstance(rootDir);
			externalFile = FileSystem.readFile(FILE_NAME)
				.stream()
				.filter(t-> !t.contains(filter))
				.collect(Collectors.toList());
			treasury = Treasury.getInstance(rootDir);
			treasury.getCashInfoFromTreasury();
		} catch(Exception error) {
			System.out.println(error);
			throw new CheckoutInitException(
					"com.checkout.Checkout", error);
		}
	}

	public static Checkout getInstance(String rootDir) {
		if(instance == null) {
			instance = new Checkout(rootDir);
		}

		return instance;
	}

	public void init() throws IOException {
		Scanner sc = new Scanner(System.in);

		System.out.printf(INIT_LOG);
		operation = sc.nextInt();

		switch(operation) {
			case 0:
				CreateOrderCheckoutOperation
					.performOperation(externalFile, FILE_NAME, menu);
				break;
			case 1:
				PayOrderCheckoutOperation
					.performOperation(externalFile, FILE_NAME, treasury);
				break;
			case 2:
				ReverseOrderCheckoutOperation
					.performOperation(externalFile, FILE_NAME, treasury);
				break;
			case 3:
				CloseCheckoutOperation
					.performOperation(externalFile, FILE_NAME, menu);
				break;
			default:
				System.out.println("Operation not allowed, contact sysadmin.");
		}

		sc.close();
	}
}
