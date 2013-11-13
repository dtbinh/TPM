package edu.turtlekit2.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XMLParser {

	/** Parse the xml tree */
	public static Document getDocFromFile(String configPath){
		Document document = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			File configFile =  new File(configPath);
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse( configFile);
		} catch (SAXParseException spe) {
			System.out.println("\n** Parsing error: "+ configPath + ", line " + spe.getLineNumber() + ", uri " + spe.getSystemId());
			System.out.println("   " + spe.getMessage() );
			Exception  x = spe;
			if (spe.getException() != null) x = spe.getException();
			x.printStackTrace();

		} catch (SAXException sxe) {
			Exception  x = sxe;
			if (sxe.getException() != null) x = sxe.getException();
			x.printStackTrace();

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return document;
	}


	public static Element getRootNodeFromFile(String path){
		if(path!=null)
			try {
				FileInputStream configFile = new FileInputStream(new File(path));
				DocumentBuilderFactory factory  = DocumentBuilderFactory.newInstance();
				Document config =  factory.newDocumentBuilder().parse(configFile);
				return config.getDocumentElement();
			}catch(IOException e){
				System.err.println("File read error with !\n" + path);
				e.printStackTrace();
			}catch(SAXException e){
				System.err.println("Load file: Parsing error of the file !\n" + path);
				e.printStackTrace();
			}catch(Exception e){
				System.err.println("Load file error !\n" + path);
				e.printStackTrace();
			}
			return null;
	}

	public static Element getRootNodeFromFile(File file){
		try {
			FileInputStream configFile = new FileInputStream(file);
			DocumentBuilderFactory factory  = DocumentBuilderFactory.newInstance();
			Document config =  factory.newDocumentBuilder().parse(configFile);
			return config.getDocumentElement();
		}catch(IOException e){
			System.err.println("File read error with !\n" + file);
			e.printStackTrace();
		}catch(SAXException e){
			System.err.println("Load file: Parsing error of the file !\n" + file);
			e.printStackTrace();
		}catch(Exception e){
			System.err.println("Load file error !\n" + file);
			e.printStackTrace();
		}
		return null;
	}

}
