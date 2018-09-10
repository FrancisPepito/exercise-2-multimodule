package com.service;

import java.util.LinkedList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import java.io.File;

import org.mockito.Mock;
import org.junit.Test;
import org.junit.Before;

public class TableServiceTest{


  LinkedList<LinkedList<String>> keys = new LinkedList<LinkedList<String>>();
  LinkedList<LinkedList<String>> values = new LinkedList<LinkedList<String>>();

  @Before
  public void setup() {
    keys.add(new LinkedList<String>());
    keys.get(0).add("a");
    keys.get(0).add("b");
    keys.get(0).add("e");
    values.add(new LinkedList<String>());
    values.get(0).add("c");
    values.get(0).add("d");
    values.get(0).add("e");
  }

  @Test
  public void testConstructorWithoutArguments() {
    TableServiceImpl table = new TableServiceImpl();
    assertTrue("Error keys and values not filled", table.getKeys().size() > 0);
  }


  @Test
  public void testConstructorWithInvalidArguments() throws Exception{
  String values = "asd\tkjh\t\tjskd\t";
    TableServiceImpl table = new TableServiceImpl("invalidTextFile.txt",values);
    assertTrue("Error keys and values not filled", table.getKeys().size() > 0);
  }

  @Test
  public void testConstructorWithValidArguments() throws Exception {
  String values = "asd\tkjh\t\tjskd\tlkjsd";
    TableServiceImpl table = new TableServiceImpl("sample.txt",values);
    assertTrue("Error keys and values not filled", table.getKeys().size() > 0);
  }

  @Test
  public void testEditKeyWithValidCoordinates() {
    TableServiceImpl table = new TableServiceImpl();
    int row=0;
    int col=0;
    String key="asd";

    table.editKey(row,col,key);

    assertEquals("Error - key not edited",key,table.getKeys().get(row).get(col));
  }

  @Test
  public void testEditValueWithValidCoordinates() {
    TableServiceImpl table = new TableServiceImpl();
    int row=0;
    int col=0;
    String value="asd";

    table.editValue(row,col,value);

    assertEquals("Error - value not edited",value,table.getValues().get(row).get(col));
  }

  @Test
  public void testEditKeyWithInvalidCoordinates() {
    TableServiceImpl table = new TableServiceImpl();
    int row=-1;
    int col=0;
    String key="asd";

    table.editKey(row,col,key);

  }

  @Test
  public void testEditValueWithInvalidCoordinates() {
    TableServiceImpl table = new TableServiceImpl();
    int row=-1;
    int col=0;
    String value="asd";
    table.editValue(row,col,value);
  }

  @Test
  public void testEditCellWithValidCoordinates() {
    TableServiceImpl table = new TableServiceImpl();
    int row=0;
    int col=0;
    String key="asd";
    String value="value";

    table.editCell(row,col,key,value);

    assertEquals("Error - value not edited",key+""+value,table.getKeys().get(row).get(col)+""+table.getValues().get(row).get(col));
  }

  @Test
  public void testEditCellWithInvalidCoordinates() {
    TableServiceImpl table = new TableServiceImpl();
    int row=-1;
    int col=0;
    String key="asd";
    String value="asd";
    table.editCell(row,col,key,value);
  }

  @Test
  public void testAddColumnWithValidCoordinates() {
    TableServiceImpl table = new TableServiceImpl();
    int row=0;
    int CURRENT_ROW_COUNT = table.getKeys().get(row).size();
    int EXPECTED_ROW_COUNT = CURRENT_ROW_COUNT+1;
    table.addColumn(row);

    assertEquals("Error - row not added",EXPECTED_ROW_COUNT,table.getKeys().get(row).size());
  }

  @Test
  public void testAddColumnWithInvalidCoordinates() {
    TableServiceImpl table = new TableServiceImpl();
    int row=-1;
    table.addColumn(row);
  }

  @Test
  public void testSortRow() {
    TableServiceImpl table = new TableServiceImpl();
    int row = 0;
    table.setKeys(keys);
    table.setValues(values);
    table.sortRow(row,1);

    assertEquals("Error - row not sorted","ab", table.getKeys().get(row).get(0)+""+table.getKeys().get(row).get(1));
  }

  @Test
  public void testSortRowReversed() {
    TableServiceImpl table = new TableServiceImpl();
    int row = 0;
    table.setKeys(keys);
    table.setValues(values);
    table.sortRow(row,2);

    assertEquals("Error - row not reverse sorted","eb", table.getKeys().get(row).get(0)+""+table.getKeys().get(row).get(1));
  }

  @Test
  public void testSearchKeyOccurence() {
    TableServiceImpl table = new TableServiceImpl();
    table.setKeys(keys);
    table.setValues(values);
    table.search("a");
    assertEquals("Error - should be found", 1, table.keyOccurrence);
  }

  @Test
  public void testSearchValueOccurence() {
    TableServiceImpl table = new TableServiceImpl();
    table.setKeys(keys);
    table.setValues(values);
    table.search("c");
    assertEquals("Error - should be found", 1, table.valueOccurrence);
  }

  @Test
  public void testSearchOverallOccurence() {
    TableServiceImpl table = new TableServiceImpl();
    table.setKeys(keys);
    table.setValues(values);
    table.search("e");
    assertEquals("Error - should be found", 2, table.overallOccurrence);
  }

  @Test
  public void testSearchNonOccurence() {
    TableServiceImpl table = new TableServiceImpl();
    table.setKeys(keys);
    table.setValues(values);
    table.search("f");
    assertEquals("Error - should be found", 0, table.overallOccurrence);
  }
}
