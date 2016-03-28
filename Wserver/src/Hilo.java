import java.io.*;
import java.net.*;
import java.util.zip.*;

public class Hilo extends Thread {

	Socket clientSocket;
	ServerSocket serverSocket;

	FileInputStream is;

	String br = null, filename;
	String indexhtml = "index.html";

	String respuestaError = "HTTP/1.1 404 Not Found\n\n" + "<!DOCTYPE><HTML><HEAD>PAGINA NO ENCONTRADA</HEAD></HTML>";
	
	String cabeceraOK = "HTTP/1.1 200 OK\n";

	
	
	
	String cabeceraPNG = cabeceraOK + "Content-Type: image/png\n" ; 

	int caracter;

	public BufferedReader bufferLectura;

	public Hilo(Socket client) throws IOException {
		this.clientSocket = client;
	}

	public void run() {

		InputStream is, is2;
		OutputStream os, os2;

		try {

			os = clientSocket.getOutputStream();
			is = clientSocket.getInputStream();
			BufferedReader bufferLectura = new BufferedReader(new InputStreamReader(is));
			br = bufferLectura.readLine();
			System.out.println("Request recibida: " + br);

			filename = br.split("/")[1].split(" HTTP")[0];
			
			String cabeceraZip = cabeceraOK + "Content-Type: application/zip\n"
					+ "Content-Disposition: filename=\"" +filename+ ".zip\"n\n";
			
			String cabeceraHTML = cabeceraOK + "Content-Type: text/html\n" + "\n";
			
			// filename= "index.html";

			System.out.println("filename:" + filename);

			if ((br.indexOf("GET") != -1)) {
				try {

					is2 = new FileInputStream(filename);
					is = new AsciiInputStream(is2);

					os2 = clientSocket.getOutputStream();
					
					os.write(cabeceraZip.getBytes());
					
					os = new ZipOutputStream(os2);

					ZipEntry zipEntry = new ZipEntry(filename);
					((ZipOutputStream) os).putNextEntry(zipEntry);
					
					os.write(cabeceraHTML.getBytes());
					while ((caracter = is.read()) != -1) {
						os.write(caracter);
					}

				}

				catch (FileNotFoundException excep) {
					os.write(respuestaError.getBytes());
					excep.printStackTrace();
				}
			}
			is.close();
			os.close();

			clientSocket.close();

		} catch (IOException excep) {
			excep.printStackTrace();
		}

		System.out.println("Estamos ejecutando desde un thread!");
	}
}