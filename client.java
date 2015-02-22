package sockets;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.Key;
import java.security.PublicKey;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class client {
	private static byte[] EncryptSecretKey (SecretKey d, Key x)
	{
	    Cipher cipher = null;
	    byte[] key = null;

	    try
	    {
	        // initialize the cipher with the user's public key
	        cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	        cipher.init(Cipher.ENCRYPT_MODE, x);
	        key = cipher.doFinal(d.getEncoded());
	    }
	    catch(Exception e )
	    {
	        System.out.println ( "exception encoding key: " + e.getMessage() );
	        e.printStackTrace();
	    }
	    return key;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int attempt=0;
		String ss;
		PrintStream ps;
		String tt;
		String correct="correct";
		String encryptedText;
		String encrptedText1;
		
		try{
			
		Socket b= new Socket("localhost", 444);		
		////aes
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(128);
		SecretKey secretKey = keyGenerator.generateKey();
		//////aes
		//get public key
		InputStream is=b.getInputStream();
		ObjectInputStream ois=new ObjectInputStream(is);
		PublicKey pub1=(PublicKey)ois.readObject();
		System.out.println("public key received :"+pub1);
		//get public key
		
		//sending encrypted version
		//http://stackoverflow.com/questions/2878867/how-to-send-an-array-of-bytes-over-a-tcp-connection-java-programming
		Key pk=pub1;
		byte[] work=EncryptSecretKey(secretKey, pk);
		OutputStream out=b.getOutputStream();
		DataOutputStream dos=new DataOutputStream(out);
		dos.writeInt(work.length);
		dos.write(work,0,work.length);
		System.out.println("sent work:"+work);
		System.out.println("work length:"+work.length);
		//sending encrypted version
		
		
		
		/*//send aes
		OutputStream okk = b.getOutputStream();
		ObjectOutputStream obk =new ObjectOutputStream(okk);
		System.out.println("working");
		obk.writeObject(secretKey);
		//obk.close();
		okk.flush();
		//send aes*/
		int t=0;
		
		
		//okk.close();
		System.out.println("done working    "+secretKey);
		//byte[] secretKey=secretKey.getEncoded();
		
		 /* ps = new PrintStream(b.getOutputStream());
			ps.println(secretKey.toString());
			ps.flush();
			System.out.println("going correct");
			System.out.println("secret key  "+secretKey);*/
		
		for(t=0;t<=2;t++)
		{
			if(t==0){
		System.out.println("enter username :");
		Scanner s=new Scanner(System.in);
	    ss=s.nextLine();
	    System.out.println("ss is:"+ss);
	    encryptedText = aes.encrypt(ss,secretKey);
	    System.out.println("ss now became:   "+encryptedText);
	    ps = new PrintStream(b.getOutputStream());
		ps.println(encryptedText);
		ps.flush();
		System.out.println("message sent");
			}
			if(t==1){
				System.out.println("enter password u want to send to server :");
				Scanner s=new Scanner(System.in);
			    ss=s.nextLine();
			    encrptedText1=aes.encrypt(ss, secretKey);
			    ps = new PrintStream(b.getOutputStream());
				ps.println(encrptedText1);
				ps.flush();
				System.out.println("message sent");
			}
			
			if(t==2){
				System.out.println("entered t==2");
				InputStreamReader ir = new InputStreamReader(b.getInputStream());
				BufferedReader br = new BufferedReader(ir);
				tt=br.readLine();
				if(tt.equals(correct))
				{
					System.out.println("success");
				}
				else
				{
					t=-1;
					attempt++;
					if(attempt==3){
						System.out.println("you have been blocked for 10sec");
						Thread.sleep(10000);
						System.out.println("you can enter again");
						attempt=0;
					}
				}
				System.out.println("executed t==2  "+tt);
				
			}
		}
		
		
		
	
		
			//ServerSocket c=new ServerSocket(555);
		//Socket d=c.accept();
		  //InputStreamReader ir=new InputStreamReader(d.getInputStream());
		    //BufferedReader br=new BufferedReader(ir);
		    //String message = br.readLine();
		    //System.out.println("in client"+message);
		}catch(Exception e){
			System.out.println("2nd try statement");
		}
				}

}