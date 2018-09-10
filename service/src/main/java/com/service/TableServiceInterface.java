package com.service;

public interface TableServiceInterface {
	public void populateTable();
	public void search(String substring);
	public void addRow();
	public void addColumn(int row);
	public void printTable();
	public void sortRow(int row,int choice);
}
