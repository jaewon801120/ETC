package com.edu.test;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

public class FileExample {

	// 파일삭제
	// 파일이동,이름바꾸기
	// 파일시간 가져오기

	public static void main(String[] args) {
		
		String[] s = new String[] {};

		System.out.println(s.length);

		File[] files = FileExample.listDir(new File("."));
		for (File file : files) {
			System.out.println(file.getPath());
		}

		File from = new File("./TestFile.txt");
		try {
			PrintWriter pw = new PrintWriter(from);
			pw.println("TestFile1234");
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		File to = new File("./bin/TestFile2.txt");
		if (from.exists())
			from.renameTo(to);

		try {
			BufferedReader br = new BufferedReader(new FileReader(to));
			String line = null;
			while ((line = br.readLine()) != null)
				System.out.println(line);
		} catch (IOException e) {
			e.printStackTrace();
		}

		copyFile(from, new File("./bin/TestFile3.txt"));

		for (File file : FileExample.listDir(new File("./bin"))) {
			System.out.println(file.getPath());
		}

		System.out.println("bef:" + from.exists());
		try {
			Files.deleteIfExists(from.toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("aft:" + from.exists());
	}

	public static File[] listDir(File dir) {
		ArrayList<File> list = new ArrayList<>();

		File[] fileList = dir.listFiles();

		for (File file : fileList) {
			if (file.isDirectory()) {
				list.add(file);
				list.addAll(Arrays.asList(listDir(file)));
			} else {
				list.add(file);
			}
		}

		return list.toArray(new File[] {});
	}

	public static void copyFile(File fromFile, File toFile) {

		try {
			FileInputStream in = new FileInputStream(fromFile);
			FileOutputStream out = new FileOutputStream(toFile);

			byte[] b = new byte[4096];
			int cnt = 0;
			while ((cnt = in.read(b)) != -1) {
				out.write(b, 0, cnt);
			}

			out.close();
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}