package sockets;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.EncryptedPrivateKeyInfo;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class server   {
	private static SecretKey decryptAESKey(byte[] data,PrivateKey dd )
	{
	    SecretKey key = null;
	   // PrivateKey privKey = null;
	    Cipher cipher = null;

	    //System.out.println ( "Data as hex: " + utility.asHex(data) );
	    System.out.println ( "data length: " + data.length );
	    try
	    {
	        // assume this loads our private key
	       // privKey = (PrivateKey)utility.loadLocalKey("private.key", false);

	        cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	        cipher.init(Cipher.DECRYPT_MODE, dd );
	        key = new SecretKeySpec(cipher.doFinal(data), "AES");

	        System.out.println ( "Key decrypted, length is " + key.getEncoded().length );
	       // System.out.println ( "data: " + utility.asHex(key.getEncoded()));
	    }
	    catch(Exception e)
	    {
	        System.out.println ( "exception decrypting the aes key: " + e.getMessage() );
	        e.printStackTrace();
	        return null;
	    }

	    return key;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String username="rahul";
		
		String password=sha.encryptPassword("sunny");
		System.out.println("encyrpted   "+sha.encryptPassword("sunny"));
		System.out.println(password);
		String test;
		String correct="correct";
		String wrong="wrong";
		int us=0,psw=0;
		try{
			//Socket inductions
		ServerSocket s=new ServerSocket(444);
	//////rsa
				KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
				gen.initialize(1024);
				
				KeyPair pair = gen.generateKeyPair();
				PrivateKey priv = pair.getPrivate();
				PublicKey pub = pair.getPublic();
				
				
				/////////
				
			
		InputStreamReader ir;
		BufferedReader br;
		String message;
		System.out.println("this is server");
		Socket a=s.accept();
		//send public key
		OutputStream okk = a.getOutputStream();
		ObjectOutputStream obk =new ObjectOutputStream(okk);
		System.out.println("working");
		obk.writeObject(pub);
		//obk.close();
		okk.flush();
		//send public key
		System.out.println("key pair generated: "+pair);
		System.out.println("public key sent" +pub);
		
		//receive and decrypt
		InputStream in=a.getInputStream();
		DataInputStream dis=new DataInputStream(in);
		
		int length=dis.readInt();
		byte[] work1 = new byte[length];
		dis.readFully(work1);
		System.out.println("received work1:"+work1);
		System.out.println("received work1 length:"+work1.length);
		
		SecretKey secretKey = decryptAESKey(work1, priv);
		//System.out.println("output: "+testing);
		System.out.println("output verify:"+secretKey);
		//receive and decrypt
		
		/*//get aes
		InputStream is=a.getInputStream();
		ObjectInputStream ois=new ObjectInputStream(is);
		SecretKey secretKey=(SecretKey)ois.readObject();
		//get aes*/
		//send aes
		
		
		System.out.println("hope to be done"  +secretKey);
		/* ir=new InputStreamReader(a.getInputStream());	     
	     br=new BufferedReader(ir);	     
	     message = br.readLine();	     
	    System.out.println("coming correct=  "+message);*/
	    
	    int t=0;
	    for(t=0;t<=2;t++)
	    {
	    	if(t==0){
	    	  
	    
	     ir=new InputStreamReader(a.getInputStream());	     
	     br=new BufferedReader(ir);	     
	     message = br.readLine();	
	     String tester =aes.decrypt(message, secretKey);
	    System.out.println("in server message received=  "+tester);
	    if(tester.equals(username))
	     System.out.println("super");
	    if(tester.equals(username)){
	    	us=1;
	    }
	     
	     
	    	}
	    	if(t==1){
	    		   ir=new InputStreamReader(a.getInputStream());	     
	    		     br=new BufferedReader(ir);	     
	    		     message = br.readLine();	     
	    		     String tester1=aes.decrypt(message, secretKey);
	    		    System.out.println("in server message received=  "+message);
	    		    test=sha.encryptPassword(tester1);
	    		   // System.out.println("baby   "+sha.encryptPassword(message));
	    		    System.out.println("hmmmm "+test);
	    		    System.out.println("hmmmm again"+password);
	    		    if(test.equals(password)){
	    		    	psw=1;
	    		    	System.out.println("duper");
	    		    	System.out.println("password is :"+psw);
	    		    }
	    		     
	    	}
	     if(t==2){
	    	 
	    	 	System.out.println("enterd t==2");
	    	 	PrintStream ps= new PrintStream(a.getOutputStream());
	    	 
	    	 	if((us==1) && (psw==1))
	    	 	{
	    	 
	    	 		ps.println(correct);
	    	 		ps.flush();
	    	 	}
	    	 	else
	    	 	{
	    	 		ps.println(wrong);
	    	 		ps.flush();
	    	 		us=0;
	    	 		psw=0;
	    	 		t=-1;
	    	 	}
	    	 System.out.println("exited t==2");
	    	
	    	 
	    	 
	     }
	     
	    
	    }
	    s.close();
	    
	    	
	    
		}catch(Exception e){
			System.out.println("dude there is a mistake in server");
			
		}

	}

}