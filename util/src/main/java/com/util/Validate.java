package com.util;

import java.util.Scanner;
import java.util.LinkedList;
public class Validate{
	private static Scanner s = new Scanner(System.in);

	private Validate(){}

	public static int integerInput() {
		Scanner s = new Scanner(System.in);
		int input=0;
		System.out.print("Enter an integer: ");
		try {
			input = s.nextInt();
		}catch (Exception ex){
			return integerInput();
		}
		return input;
	}

	public static String stringInput() {
		String input="";
		try {
			System.out.print("Enter a string: ");
			input = s.nextLine();
			if(input.contains("\t")) {
				System.out.println("String must not contain a horizontal tab space");
				return stringInput();
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return input;
	}

	public static boolean arrayIndex(LinkedList<LinkedList<String>> list, int row, int col) {
		try {
			list.get(row).get(col);
			return true;
		}catch(Exception ex) {
			System.out.println("Invalid index");
			return false;
		}
	}
	public static boolean arrayIndex(LinkedList<LinkedList<String>> list, int index) {
		try {
			list.get(index);
			return true;
		}catch(Exception ex) {
			System.out.println("Invalid index");
			return false;
		}
	}

}
