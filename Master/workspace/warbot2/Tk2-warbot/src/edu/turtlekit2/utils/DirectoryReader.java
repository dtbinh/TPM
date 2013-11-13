package edu.turtlekit2.utils;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;


public class DirectoryReader {


	private static void listFiles(String path, ArrayList<String> filesListed){
		File[] files = null;
		File directoryToScan = new File(path);
		if(directoryToScan.isDirectory())
			files = directoryToScan.listFiles();
		for (int i = 0; i < files.length; i++) {
			if(files[i].isDirectory()){
				listFiles(files[i].getAbsolutePath(), filesListed);
			}else{
				filesListed.add(files[i].getAbsolutePath());
			}
		}
	}
	
	public static ArrayList<String> getSimulationFilesUrl(){
		ArrayList<String> simulations = new ArrayList<String>();
		ArrayList<String> result = new ArrayList<String>();
		String curDir = System.getProperty("user.dir");
		listFiles(curDir, simulations);
		for (Iterator<String> iterator = simulations.iterator(); iterator.hasNext();) {
			String path = (String) iterator.next();
			if((path.endsWith("xml") || path.endsWith("XML")) && !path.contains("bin")){
				try{
					if(XMLParser.getDocFromFile(path).getDocumentElement().getNodeName().equals("simulation"))
						result.add(path);
				}catch (Exception e) {
					
				}
				
			}
		}
		return result;
	}
} 