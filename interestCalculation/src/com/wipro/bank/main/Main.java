package com.wipro.bank.main;

import com.wipro.bank.service.BankService;

import java.util.Scanner;

public class Main {
	static int tenure;
	static float principal;
	static int age;
	static String gender;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter tenure (5 or 10): ");
		tenure = sc.nextInt();
		
		System.out.println("Enter principal amount (minimum 500): ");
		principal = sc.nextFloat();
		
		System.out.println("Enter age: ");
		age = sc.nextInt();
		
		System.out.println("Enter gender: ");
		gender = sc.next();
		
		BankService bs = new BankService();
		bs.calculate(principal, tenure, age, gender);
		
	}
}
