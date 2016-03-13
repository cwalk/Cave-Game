package caveGame;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class FileHandler {
	
	private final int iterate = 23333;
	
	private final String saltString = "UTF SALT IS NOT WORKING FOR ME QAQ";
	private final String pass = "There is no password!";
	private final String cMode = "AES/ECB/PKCS5Padding";
	private final String kMode = "PBKDF2WithHmacSHA1";
	private SecretKeySpec key;	 
	
	public FileHandler() {
		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA");
			digest.update(pass.getBytes());
			key = new SecretKeySpec(digest.digest(), 0, 16, "AES");
			byte[] salt = saltString.getBytes();
			SecretKeyFactory factory = SecretKeyFactory.getInstance(kMode);
			SecretKey tmp = factory.generateSecret(new PBEKeySpec(pass.toCharArray(), salt, iterate, 128));
			key = new SecretKeySpec(tmp.getEncoded(), "AES");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("deprecation")
	public ArrayList<byte[]> encrypt(String inString, String outString, String byteString) {
		ArrayList<byte[]> ecrp = new ArrayList<byte[]>();
		
		try {
			DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(new File(inString))));
			DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File(outString))));
			DataOutputStream dos2 = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File(byteString))));
			
			while (dis.available() != 0) {				
				String s = dis.readLine();
				Cipher cipher = Cipher.getInstance(cMode);
				cipher.init(Cipher.ENCRYPT_MODE, key);
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				CipherOutputStream cos = new CipherOutputStream(bos, cipher);
				cos.write(s.getBytes());
				cos.flush();
				cos.close();
				byte[] temp = bos.toByteArray();
				dos2.writeInt(temp.length);
				ecrp.add(temp);
				dos.writeUTF(new String(temp));	
				System.out.println(temp.length);
				for (int i = 0; i < temp.length; i++) {
					System.out.print(temp[i] + " ");
					dos2.writeInt(temp[i]);
				}
				System.out.println();
			}
			
			dis.close();
			dos.close();
			dos2.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return ecrp;
	}
	
	public void encrypt(ArrayList<String> list, String outString) {
		
		try {
			DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File(outString))));
			
			for (String s : list) {				
				Cipher cipher = Cipher.getInstance(cMode);
				cipher.init(Cipher.ENCRYPT_MODE, key);
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				CipherOutputStream cos = new CipherOutputStream(bos, cipher);
				cos.write(s.getBytes());
				cos.flush();
				cos.close();
				byte[] temp = bos.toByteArray();
				dos.writeInt(temp.length);
				System.out.println(temp.length);
				for (int i = 0; i < temp.length; i++) {
					System.out.print(temp[i] + " ");
					dos.writeInt(temp[i]);
				}
				System.out.println();
			}			
			dos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	@SuppressWarnings("resource")
	public void decrypt(String inString, String outString) {
		
		try {
			DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(new File(inString))));
			DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File(outString))));
			
			while (dis.available() != 0) {		
				int size = dis.readInt();
				byte[] b = new byte[size];
				for (int i = 0; i < size; i++) {
					b[i] = (byte) dis.readInt();
				}
				Cipher dcry = Cipher.getInstance(cMode);
				dcry.init(Cipher.DECRYPT_MODE, key);
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ByteArrayInputStream bis = new ByteArrayInputStream(b);
				CipherInputStream cis = new CipherInputStream(bis, dcry);
				byte[] buf = new byte[1024];
				int bytesRead;
				while ((bytesRead = cis.read(buf)) >= 0) {
					bos.write(buf, 0, bytesRead);
				}
				String s = new String(bos.toByteArray());				
				dos.writeBytes(s+"\n");
			}			
			dis.close();
			dos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("resource")
	public ArrayList<String> decrypt(String inString) {
		ArrayList<String> dcrp = new ArrayList<String>();
		
		try {
			DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(new File(inString))));
			
			while (dis.available() != 0) {		
				int size = dis.readInt();
				byte[] b = new byte[size];
				for (int i = 0; i < size; i++) {
					b[i] = (byte) dis.readInt();
				}
				Cipher dcry = Cipher.getInstance(cMode);
				dcry.init(Cipher.DECRYPT_MODE, key);
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ByteArrayInputStream bis = new ByteArrayInputStream(b);
				CipherInputStream cis = new CipherInputStream(bis, dcry);
				byte[] buf = new byte[1024];
				int bytesRead;
				while ((bytesRead = cis.read(buf)) >= 0) {
					bos.write(buf, 0, bytesRead);
				}
				String s = new String(bos.toByteArray());
				dcrp.add(s);
			}			
			dis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return dcrp;
	}

}
