package com.service;

import java.util.Scanner;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Random;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.IOException;

public class TableServiceImpl implements TableServiceInterface{
	private LinkedList<LinkedList<String>> keys = new LinkedList<LinkedList<String>>();
	private LinkedList<LinkedList<String>> values = new LinkedList<LinkedList<String>>();
	private File file;
	private BufferedReader bufferedReader;
	int keyOccurrence=0;
	int valueOccurrence=0;
	int overallOccurrence=0;

	public TableServiceImpl() {
		populateTable();
		file = new File("defaultFile.txt");
	}

	public TableServiceImpl(String filename,String fileContents){
		file = new File((String) filename);
		String[] pairs;
		String[] keyValuePair;
		String line = null;

			// System.out.println(filename);
			int counter=0;
			Scanner scanner = new Scanner(fileContents);
			try{
			while (scanner.hasNextLine()) {
			  line = scanner.nextLine();
				pairs = line.split("\t\t");
				keys.add(new LinkedList<String>());
				values.add(new LinkedList<String>());
				for(String pair : pairs) {
					keyValuePair = pair.split("\t");
					keys.get(counter).add(keyValuePair[0]);
					values.get(counter).add(keyValuePair[1]);
				}
				counter++;
			}
			scanner.close();
		}catch(Exception ex) {
			System.out.println("File contains an invalid key-value format. Generating a new table");
			populateTable();
		}
	}

	public void populateTable() {
		keys.clear();
		values.clear();
		Random random = new Random();
		int rows = random.nextInt(5)+1;
		for(int x=0;x<rows;x++){
			addRow();
		}
	}

	public void saveFile() {
		try(PrintWriter out = new PrintWriter(file)) {
			for(int i=0;i<keys.size();i++){
				for(int j=0;j<keys.get(i).size();j++){
					out.print(keys.get(i).get(j)+"\t"+values.get(i).get(j)+"\t\t");
				}
				out.println();
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}


	public void closeFile(BufferedReader reader) {
		try {
	        if (reader != null) {
	            reader.close();
	        }
	    } catch (IOException e) {
	    }
	}

	public String generateString() {
		Random random = new Random();
		String randomString;

		randomString = String.valueOf((char) (random.nextInt(93)+33));
		randomString += String.valueOf((char) (random.nextInt(93)+33));
		randomString += String.valueOf((char) (random.nextInt(93)+33));

		return randomString;
	}

	public void search(String substring) {
		keyOccurrence=0;
		valueOccurrence=0;
		overallOccurrence=0;
		for(int i=0;i<keys.size();i++) {
			for(int j=0;j<keys.get(i).size();j++) {
				for(int k=0;k<=keys.get(i).get(j).length()-substring.length();k++) {
					if(substring.equals(keys.get(i).get(j).substring(k,substring.length()+k))) {
						keyOccurrence++;
						overallOccurrence++;

					}
				}
				for(int k=0;k<=values.get(i).get(j).length()-substring.length();k++) {
					if(substring.equals(values.get(i).get(j).substring(k,substring.length()+k))) {
						valueOccurrence++;
						overallOccurrence++;
					}
				}

				if(keyOccurrence>0) {
					System.out.println(keyOccurrence+" occurrence(s) at key["+i+","+j+"]");
				}
				if(valueOccurrence>0) {
					System.out.println(valueOccurrence+" occurrence(s) at value["+i+","+j+"]");
				}
			}
		}
		if(overallOccurrence==0) {
			System.out.println("String not found");
		}
	}

	public void editKey(int row, int col, String key) {
		try {
			keys.get(row).set(col,key);
		} catch(Exception ex) {
			System.out.println("Out array out of bounds");
		}
	}

	public void editValue(int row, int col, String value) {
		try {
			values.get(row).set(col,value);
		} catch(Exception ex) {
			System.out.println("Out array out of bounds");
		}
	}

	public void editCell(int row, int col, String key, String value) {
		try {
			keys.get(row).set(col,key);
			values.get(row).set(col,value);
		} catch(Exception ex) {
			System.out.println("Out array out of bounds");
		}
	}

	public void addRow(){
		Random random = new Random();
		int cols = random.nextInt(5)+1;

		keys.add(new LinkedList<String>());
		values.add(new LinkedList<String>());
		while(cols>0) {
			keys.getLast().add(generateString());
			values.getLast().add(generateString());
			cols--;
		}
	}

	public void addColumn(int row) {
		try {
			keys.get(row).add(generateString());
			values.get(row).add(generateString());
		} catch(Exception ex) {
			System.out.println("Out array out of bounds");
		}
	}

	public void printTable() {
		for(int i=0;i<keys.size();i++) {
			for(int j=0;j<keys.get(i).size();j++) {
				System.out.print(" ("+keys.get(i).get(j)+" : "+values.get(i).get(j)+") ");
			}
			System.out.println();
		}
	}

	public void sortRow(int row,int choice) {
		LinkedList<String> keyValuePairs = new LinkedList<String>();
		String[] sortedPair;
		int index=0;

		for(int x=0;x<keys.get(row).size();x++) {
			keyValuePairs.add(String.join("\t",keys.get(row).get(x),values.get(row).get(x)));
		}
		Collections.sort(keyValuePairs);

		if(choice == 2){
			Collections.reverse(keyValuePairs);
		}

		for(String pair : keyValuePairs) {
			sortedPair = pair.split("\t");
			keys.get(row).set(index,sortedPair[0]);
			values.get(row).set(index,sortedPair[1]);
			index++;
		}
	}

	public LinkedList<LinkedList<String>> getKeys() {
		return keys;
	}
	public LinkedList<LinkedList<String>> getValues() {
		return values;
	}

	public void setKeys(LinkedList<LinkedList<String>> keys){
		this.keys = keys;
	}

	public void setValues(LinkedList<LinkedList<String>> values){
		this.values = values;
	}
}
