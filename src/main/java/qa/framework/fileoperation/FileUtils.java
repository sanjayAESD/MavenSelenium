package qa.framework.fileoperation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;



public class FileUtils {
	
	public static boolean isFileExist(String filepath) {
		
		return new File(filepath).exists();
		
		
	}
	
	/***
	 * Creating file
	 * 
	 * @param filepath
	 * @return
	 * @throws IOException
	 * 
	 */
	
	 public static boolean createFile(String filepath) throws IOException {
		 
		 File file = new File(filepath);
		 
		return file.exists() ? true : file.createNewFile();
		 
		 
	 }
	 
	 /**
	  * Create Directory
	  * 
	  * @author sanjay
	  * @param dirpath
	  * @return
	  */
	 
	 public static boolean createDir(String dirpath) throws IOException {
		 
		 File file = new File(dirpath);
		 
		 return file.exists() ? true : file.mkdir();
		 
	 }
	 
	 public static void write(String filepath, String write) throws IOException {
		 
		 FileWriter fw = null;
		 BufferedWriter bw = null;
		 
		 try {
			 
			 fw = new FileWriter(filepath);
			 bw = new BufferedWriter(fw);
			 bw.write(write);
			 
		 } catch(Exception e) {
			 
			 
		 } finally {
			 
			 bw.close();
			 fw.close();		 
		 }
		 
	 }
	 
	 public static void append(String filepath, String write) {
		 
		 try {
			 Files.write(Paths.get(filepath), write.getBytes(), StandardOpenOption.APPEND);
			 
		 } catch(Exception e) {
			 
			 
		 }
		 
	 }
	 
	 public static String readFile(String filepath) throws IOException {
		 
		 StringBuffer content = new StringBuffer();
		 BufferedReader bufferedReader = null;
		 FileReader fileReader = null;
		 
		 try {
			 
			 if(isFileExist(filepath)) {
				 
				 fileReader = new FileReader(filepath);
				 bufferedReader = new BufferedReader(fileReader);
				 String line = null;
				 
				 while ((line = bufferedReader.readLine()) != null){
					 content.append(line).append("\n");
								 
				 }
			 }else {
					 throw new FileNotFoundException(filepath);
					 
				 }
			 }catch(Exception e) {
				 
			 } finally {
				 
				 bufferedReader.close();
				 fileReader.close();
			 }
		 
		 return content.toString();
		 
		 }
	 
	 /** 
	  * Cleans directory path provided.
	  * 
	  * @author sanjay
	  * @param dirpath
	  * @throws Exception
	  */
	 
	 public static void emptyDir(String dirpath) throws Exception{
		 
		 File dir = new File(dirpath);
		 if(dir.isDirectory()) {
			 
			org.apache.commons.io.FileUtils.cleanDirectory(dir);
			
		 } else {
			 throw new Exception("Error: Not a directory path !!");
		 }
	 }
	 
	 /**
	  * Deleting directory
	  * 
	  * @author sanjay
	  * @param dirPath
	  * 
	  */
		
	 public static void deleteDir(String dirPath) {
		 
		 try {
			 
			 org.apache.commons.io.FileUtils.deleteDirectory(new File(dirPath));
			 
		 }catch(IOException e) {
			 e.printStackTrace();
		 }
	 }
	 
	 public static void copy(String src, String dest) throws IOException{
		 
		 org.apache.commons.io.FileUtils.copyFile(new File(src), new File(dest));
	 }
	 
	 public static void copyDirContent(String src, String dest) throws IOException {
		 
		 org.apache.commons.io.FileUtils.copyDirectory(new File(src), new File(dest), true);
	 }
	 
}


