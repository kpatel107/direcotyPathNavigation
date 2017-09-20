package com.directoryPathNavigation.util;
import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;

public final class  DirectoryPathNavigateUtil {
	
		private static final String PROJECT = "Project";
		private static final String URL = "URL";
	    private static final String EXTENSION = "Extension";
	    private static final String DOCUMENT = "Document";
	    private static final String DASH = "-";
	    private static final String COLON = ":";
	    private static final String DOT = ".";
	    private static final String SPACE = "  ";
		private static final String DELIMETER = Pattern.quote(DOT);
		private static final String SLASH = File.separator;
		private DirectoryPathNavigateUtil(){
			
		}
	    /**this contents use for unit test***/
		public  static int numOfDirs;
		public  static int numOfFiles;
		public static int getNumOfDirs() {
	    		return numOfDirs;
	    }
	    public static int getNumOfFiles() {
			return numOfFiles;
	    }
	    /*******/
	    private static int lenghtOfDirectory;
	    private static int tabs;
		
		/**
		 * This method creates root dir
		 *
		 * @param filename
		 * @return rootDirectory
		 */
		public static File createRootDir(String filename) {
			if(filename!=null && !"".equals(filename)) {
				File rootDirectory = new File(filename);
				lenghtOfDirectory = rootDirectory.getAbsolutePath().length() - rootDirectory.getName().length();
				numOfDirs=numOfFiles=tabs=0;
				return rootDirectory;
			}
			return null;
		}
		/**
		 * This methods list and print all files recursively
		 *
		 * @param dir
		 * @param tabs
		 */
		public static void listAllFilesRecursively(File dir) {
	       
			if (dir != null) {
				if (dir.isDirectory()) {
					printFileOnconsole(dir);
					/*
					 * Add the following code to remove hidden files
					 */
					File[] files  =dir.listFiles(new FileFilter() {
					    @Override
					    public boolean accept(File file) {
					        return !file.isHidden();
					    }
					});
					/**/
					String listOfFileNames[]=Arrays.stream(files).map(File::getName)
			        .toArray(String[]::new);
					List<String> sortedListOfFiles = sortFilesByExtensions(listOfFileNames);
					iterateListOfFiles(sortedListOfFiles,  dir);
				} else {
					System.out.println("Please provide a path to root directory");
				}
			} else {
				System.out.println(dir + " is not a directory");
			}

		}

		/**
		 * Iterate directory and sub directory
		 *
		 * @param sortedListOfFiles
		 * @param tabs
		 * @param dir
		 */
		public static void iterateListOfFiles(List<String> sortedListOfFiles,  File dir) {
			for (String item : sortedListOfFiles) {
				tabs++;
				File file = new File(dir + SLASH + item);
				if (file.isDirectory()) {
					listAllFilesRecursively(file);
					tabs--;
				} else {
					printFileOnconsole(file);
					tabs--;
				}
			}
		}

		/**
		 * This methods sort the list of files by extension
		 *
		 * @param listOfFileNames
		 * @return
		 */
		public static List<String> sortFilesByExtensions(String[] listOfFileNames) {
			List<String> orginalList = new CopyOnWriteArrayList<>(Arrays.asList(listOfFileNames));
			Set<String> setOfuniqueExtension = new TreeSet<>();
			for (String item : listOfFileNames) {
				if (item.contains(DOT)) {
					String[] split = item.split(DELIMETER);
					String temp = "." + split[split.length - 1];
					setOfuniqueExtension.add(temp);
				}
			}
			if(!setOfuniqueExtension.isEmpty()) {
			List<String> finalListOfAllFiles = new LinkedList<>();
				setOfuniqueExtension.stream().forEach((s1) -> {
					for (int i = 0; i < orginalList.size(); i++) {
						if (orginalList.get(i).contains(s1)) {
							finalListOfAllFiles.add(orginalList.get(i));
							orginalList.remove(orginalList.get(i));
							i--;
						}
					}
				});
				orginalList.stream().filter((s1) -> (!finalListOfAllFiles.contains(s1))).forEach((s1) -> {
					finalListOfAllFiles.add(s1);
				});
		
				return finalListOfAllFiles;
			}
			return orginalList;
			
		}

		/**
		 * This method prints directory and files on console
		 *
		 * @param file
		 * @param tabs
		 */
		private static void printFileOnconsole(File file) {
			String fileName = file.getName();
			String[] split = fileName.split(DELIMETER);
			for (int i = 0; i < tabs; i++) {
				System.out.print(SPACE);
			}
			if(file.isDirectory()) {
				System.out.println(
						DASH + SPACE + PROJECT + COLON + SPACE
						+ file.getName()
						+ SPACE + DASH + SPACE + URL + COLON + SPACE
						+ file.getAbsolutePath().substring(lenghtOfDirectory));
				numOfDirs++;
			}else {
				System.out.println(
						 DASH + SPACE + DOCUMENT + COLON + SPACE
						+ file.getName()
						+ SPACE + DASH + SPACE + EXTENSION + COLON + SPACE+ DOT
						+ split[split.length - 1]
						+ SPACE + DASH + SPACE + URL + COLON + SPACE
						+ file.getAbsolutePath().substring(lenghtOfDirectory));
				numOfFiles++;
			}
		}

}
