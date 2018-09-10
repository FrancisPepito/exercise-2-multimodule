package com.app;

import com.service.*;
import com.util.*;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.IOException;

public class Main{
	public static void main(String[] args) {
		TableServiceImpl table1;
		int choice;
		String input;
		int row;
		int col;
		File file;
		String fileContents;

		try{
			file = new File((String) args[0]);
			BufferedReader reader = new BufferedReader(new FileReader(args[0]));
			StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			String ls = System.getProperty("line.separator");
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append(ls);
			}
			// delete the last new line separator
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			reader.close();
			fileContents = stringBuilder.toString();
			table1 = new TableServiceImpl(args[0],fileContents);
		}catch(Exception ex) {
			table1 = new TableServiceImpl();
		}
		table1.printTable();
		table1.saveFile();

		while(true){
			System.out.println("1]Search\n2]Edit\n3]Add Row\n4]Add Column\n5]Print\n6]Sort\n7]Exit");
			choice = Validate.integerInput();
			switch(choice) {
				case 1:
					table1.search(Validate.stringInput());
					break;
				case 2:

					System.out.println("Row");
					row = Validate.integerInput();
					System.out.println("Column");
					col = Validate.integerInput();
					System.out.println("1]Key\n2]Value\n3]Both");
					choice = Validate.integerInput();

					if(!Validate.arrayIndex(table1.getKeys(),row,col)){
						break;
					}

					if(choice==1){
						System.out.println("Key");
						input = Validate.stringInput();
						table1.editKey(row,col,input);
					}else if(choice==2) {
						System.out.println("Value");
						input = Validate.stringInput();
						table1.editKey(row,col,input);
					}else if(choice==3) {
						input = Validate.stringInput();
						String value = Validate.stringInput();
						table1.editCell(row,col,input,value);
					}
					table1.saveFile();
					break;
				case 3:
					table1.addRow();
					System.out.println("Successfully added a row");
					table1.saveFile();
					break;
				case 4:
					System.out.println("Enter row index");
					row = Validate.integerInput();
					if(Validate.arrayIndex(table1.getKeys(),row)) {
						System.out.println("Successfully added a column");
						table1.addColumn(row);
					}
					table1.saveFile();
					break;
				case 5:
					table1.printTable();
					break;
				case 6:
					System.out.println("Row");
					row = Validate.integerInput();
					if(Validate.arrayIndex(table1.getKeys(),row)) {
						System.out.println("1]Ascending\n2]Descending");
						choice = Validate.integerInput();
						table1.sortRow(row,choice);
						if(choice!=1&&choice!=2){
							break;
						}
						System.out.println("Row successfully sorted");
					}
					table1.saveFile();
					break;
				case 7:
					table1.saveFile();
					System.exit(0);

			}
		}
	}
}
