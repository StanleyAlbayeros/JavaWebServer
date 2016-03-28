import java.io.*;
import java.net.*;
//main
public class WServer {


	public static void main(String[] args){
	
		ServerSocket serverSocket;
		Socket clientSocket;
		Hilo thread;
	
		try
		{
			serverSocket = new ServerSocket( 8206 );
			
			while (true)
			{
				clientSocket = serverSocket.accept();
				thread= new Hilo(clientSocket);
				thread.start();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace ();
		}
	}
}