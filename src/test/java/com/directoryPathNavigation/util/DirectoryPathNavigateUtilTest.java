package com.directoryPathNavigation.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import com.directoryPathNavigation.util.DirectoryPathNavigateUtil;

public class DirectoryPathNavigateUtilTest {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void cleanUpStreams() {
		System.setOut(null);
	}

	@Test
	public void createRootDir_NotNull() {
		File rootDirectory = DirectoryPathNavigateUtil.createRootDir("./testdirectory/Main Project");
		assertThat(rootDirectory, is(notNullValue()));
	}

	@Test
	public void createRootDir_Null() {
		File rootDirectory = DirectoryPathNavigateUtil.createRootDir("");
		assertThat(rootDirectory, is(nullValue()));
	}

	@Test
	public void sortFilesByExtensions_SorrtedFileList() {
		List<String> expectedListOfFileNames = Arrays
				.asList(new String[] { "WordFile1.docx", "ExcelFile1.xlsx", "Project A" });
		String[] inputListOfFileNames = { "Project A", "ExcelFile1.xlsx", "WordFile1.docx" };
		List<String> sortList = DirectoryPathNavigateUtil.sortFilesByExtensions(inputListOfFileNames);
		assertThat(sortList, is(notNullValue()));
		assertEquals(expectedListOfFileNames, sortList);
	}

	@Test
	public void sortFilesByExtensions_UnSorrtedDirList() {
		String[] inputListOfFileNames = { "Project A", "Project 2", "Project 3" };
		List<String> expectedListOfFileNames = Arrays.asList(inputListOfFileNames);
		List<String> finalListOfAllFiles = DirectoryPathNavigateUtil.sortFilesByExtensions(inputListOfFileNames);
		assertThat(finalListOfAllFiles, is(notNullValue()));
		assertEquals(expectedListOfFileNames, finalListOfAllFiles);
	}

	@Test
	public void listAllFilesRecursively_NumOfDirAndFiles() {

		File rootDirectory = new File("./testdirectory/Main Project/Project 2");
		DirectoryPathNavigateUtil.listAllFilesRecursively(rootDirectory);
		assertEquals(DirectoryPathNavigateUtil.getNumOfDirs(), 1);
		assertEquals(DirectoryPathNavigateUtil.getNumOfFiles(), 2);
		assertTrue(outContent.toString().contains("WordFile2.docx"));
	}

	@Test
	public void iterateListOfFiles_NumOfDirAndFiles() {

		File rootDirectory = DirectoryPathNavigateUtil.createRootDir("./testdirectory/Main Project/Project 1");
		List<String> sortedListOfFiles = new ArrayList<>();
		sortedListOfFiles.add("WordFile1.docx");
		sortedListOfFiles.add("ExcelFile1.xlsx");
		sortedListOfFiles.add("Project A");
		DirectoryPathNavigateUtil.iterateListOfFiles(sortedListOfFiles, rootDirectory);
		assertEquals(DirectoryPathNavigateUtil.getNumOfDirs(), 1);
		assertEquals(DirectoryPathNavigateUtil.getNumOfFiles(), 3);
		assertTrue(outContent.toString().contains("WordFile1.docx"));
	}
}
