package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import query.Pathway;

public class PathwayTest {
	public static void main(String[] args) {
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(System.getProperty("user.dir") 
												+ "\\Pathways\\hrev1.owl");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Pathway hrev1 = new Pathway("REACT_8.2", "hrev1");
		System.out.println("Create instance Pathway");
		
		try {
			hrev1.generateModel(inputStream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Generate model\n"
							+ "Level: " + hrev1.getLevel());		
	}
}
