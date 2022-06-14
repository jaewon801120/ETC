package com.edu.test;

import java.io.*;

public class FileIO {

	public static void main(String[] args) {
		
		try
		{
			readAll("");
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	// 텍스트 파일 한 번에 모두 읽기 (Utility 타입)
	public static String readAll(String filePath) throws IOException {
		 
	    StringBuilder  stringBuilder;
	    FileReader     fileReader     = null;
	    BufferedReader bufferedReader = null;
	    try {
	        stringBuilder  = new StringBuilder();
	        fileReader     = new FileReader(filePath);
	        bufferedReader = new BufferedReader(fileReader);
	        String line;
	        while ((line = bufferedReader.readLine()) != null)
	            stringBuilder.append(line).append('\n');
	         
	    } finally {
	        if (bufferedReader != null) try { bufferedReader.close(); } catch (Exception ex) { /* Do Nothing */ }
	        if (fileReader     != null) try { fileReader    .close(); } catch (Exception ex) { /* Do Nothing */ }
	    }
	     
	    return stringBuilder.toString();
	}
	
	// 텍스트 파일 라인 단위로 읽어서 처리
	public static void readLine(String filePath) throws IOException {
		 
		FileReader     fileReader     = null;
		BufferedReader bufferedReader = null;
		try {
		    fileReader     = new FileReader(filePath);
		    bufferedReader = new BufferedReader(fileReader);
		    String line;
		    while ((line = bufferedReader.readLine()) != null) {
		        //[[line 값 처리]]
		    }
		     
		} finally {
		    if (bufferedReader != null) try { bufferedReader.close(); } catch (Exception ex) { /* Do Nothing */ }
		    if (fileReader     != null) try { fileReader    .close(); } catch (Exception ex) { /* Do Nothing */ }
		}
	}
	
	// 텍스트 파일 쓰기 (Utility 타입)
	public static void write(String filePath, String content) throws IOException {
		 
	    FileWriter     fileWriter     = null;
	    BufferedWriter bufferedWriter = null;
	    try {
	        fileWriter     = new FileWriter(filePath);
	        bufferedWriter = new BufferedWriter(fileWriter);
	        bufferedWriter.write(content);
	 
	    } finally {
	        if (bufferedWriter != null) try { bufferedWriter.close(); } catch (Exception ex) { /* Do Nothing */ }
	        if (fileWriter     != null) try { fileWriter    .close(); } catch (Exception ex) { /* Do Nothing */ }
	    }
	}
	
	// 폴더 내 파일 리스트 처리
	public static void folderRead() {

		File folder = new File(".");
		for (File filex: folder.listFiles()) {
		    String fileName     = filex.getName();
		    String absolutePath = filex.getAbsolutePath();
		}
		
	}
	
	// 파일의 특정 바이트부터 특정 길이만큼 읽기
	public static byte[] readBytes(String filePath, int offset, int length) throws IOException {
		 
	    RandomAccessFile randomFile = null;
	    try {
	        randomFile = new RandomAccessFile(filePath, "r"); 
	        randomFile.seek(offset);
	         
	        byte[] dataBytes = new byte[length];
	        randomFile.readFully(dataBytes);
	         
	        return dataBytes;
	         
	    } finally {
	        if (randomFile != null) try { randomFile.close(); } catch (Exception ex) { /* Do Nothing */ }
	    }
	}
	
}
