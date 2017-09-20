package com.directoryPathNavigation;

import java.io.File;

import com.directoryPathNavigation.util.DirectoryPathNavigateUtil;;

public class MainApp {
	public static void main(String args[]){
        File rootDirectory = DirectoryPathNavigateUtil.createRootDir("./testdirectory/Main Project");
        DirectoryPathNavigateUtil.listAllFilesRecursively(rootDirectory);
    }
}
