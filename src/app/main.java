package app;

import java.security.spec.KeySpec;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class main {
	private static Cifrador cifrador;

	public static void main(String[] args) {
		// cifrador es el que va a encargarse de generar la llave primaria apartir de la constraseña
		cifrador = new Cifrador();
		
		 Scanner in = new Scanner(System.in); 
		 System.out.println("porfavor introdusca la clave a utilizar");
		 String contrasena= in.nextLine();
		  cifrador.hash(contrasena);
		 
		 
		 
		
		
		
		
		// Codiog para cifrar utilizando AES
		String mode = "1";
	      try {
	         byte[] theKey = null;
	         byte[] theMsg = null; 
	         if (mode.equals("1")) { 
	        	 
	        	 theKey = hexToBytes("0101010101010101");
	             theMsg = hexToBytes("8000000000000000");
	             
	             
	          } else if (mode.equals("2")) { 
	             theKey = hexToBytes("38627974656B6579"); // "8bytekey"
	             theMsg = hexToBytes("6D6573736167652E"); // "message."
	         } else {
	            System.out.println("Usage:");
	            System.out.println("java JceSunDesTest 1/2");
	            return;
	         }	
	         KeySpec ks = new DESKeySpec(theKey);
	         SecretKeyFactory kf 
	            = SecretKeyFactory.getInstance("DES");
	         SecretKey ky = kf.generateSecret(ks);
	         Cipher cf = Cipher.getInstance("DES/ECB/NoPadding");
	         cf.init(Cipher.ENCRYPT_MODE,ky);
	         byte[] theCph = cf.doFinal(theMsg);
//	         System.out.println("Key     : "+bytesToHex(theKey));
//	         System.out.println("Message : "+bytesToHex(theMsg));
//	         System.out.println("Cipher  : "+bytesToHex(theCph));
//	         System.out.println("Expected: "+bytesToHex(theExp));
	      } catch (Exception e) {
	         e.printStackTrace();
	         return;
	      }
	}
	public static byte[] hexToBytes(String str) {
	      if (str==null) {
	         return null;
	      } else if (str.length() < 2) {
	         return null;
	      } else {
	         int len = str.length() / 2;
	         byte[] buffer = new byte[len];
	         for (int i=0; i<len; i++) {
	             buffer[i] = (byte) Integer.parseInt(
	                str.substring(i*2,i*2+2),16);
	         }
	         return buffer;
	      }

	   }
	   public static String bytesToHex(byte[] data) {
	      if (data==null) {
	         return null;
	      } else {
	         int len = data.length;
	         String str = "";
	         for (int i=0; i<len; i++) {
	            if ((data[i]&0xFF)<16) str = str + "0" 
	               + java.lang.Integer.toHexString(data[i]&0xFF);
	            else str = str
	               + java.lang.Integer.toHexString(data[i]&0xFF);
	         }
	         return str.toUpperCase();
	      }
	   }

}
