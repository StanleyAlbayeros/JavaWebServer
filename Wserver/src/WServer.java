import java.io.*;
import java.net.*;
//main
public class WServer {


	public static void main(String[] args){
	
		ServerSocket serverSocket;
		Socket clientSocket;
		Hilo thread;
		int port = 8206;
	
		System.out.println("Server starting on port:" + port);
		
		try{
			//creamos serversocket 
			
			serverSocket = new ServerSocket( port );
			}catch (IOException e){
				//pillamos errores
				e.printStackTrace ();
				return; // salimos si hay error al crear socket
		}
		
		System.out.println("Waiting for connection");
		while (true)
		{
			try{
				clientSocket = serverSocket.accept();
				System.out.println("Connection established");
				thread= new Hilo(clientSocket);
				thread.start();
			}catch (IOException e){
				//pillamos errores
				e.printStackTrace ();
				return; // salimos si hay error
			}

		}
		
	}
}