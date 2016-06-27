import java.io.*;
import java.net.*;
import java.util.regex.*;
import java.util.zip.*;

public class Hilo extends Thread {

	ServerSocket serverSocket;
	Socket clientSocket;
	InputStream is;
	OutputStream os, os2;
	ZipEntry zip;
	
	String bufferRequest = null, filename;

	static int BUFFER = 2048;

	int caracter;

	public BufferedReader bufferLectura;
	


	public Hilo(Socket client) throws IOException {
		this.clientSocket = client;
	}

	public void run() {


		System.out.println("Estamos ejecutando desde un thread!");

		try {

			os = clientSocket.getOutputStream();
			os2 = clientSocket.getOutputStream();
			is = clientSocket.getInputStream();
			bufferLectura = new BufferedReader(new InputStreamReader(is));
			bufferRequest = bufferLectura.readLine();

			
			System.out.println("\nRequest recibida: " + bufferRequest);

			String filename = bufferRequest.split("/")[1].split(" HTTP")[0].split("\\?")[0];
			
			is = new FileInputStream(filename);

			String opciones = "";
			try {
				opciones = bufferRequest.split("\\?")[1].split(" HTTP")[0];
			} catch (ArrayIndexOutOfBoundsException e) {
				e.printStackTrace();
			}

			System.out.println("\n\n antes del parseo de URL \n\n");

			Boolean ASC = false;
			Boolean ZIP = false;
			Boolean GZIP = false;
			Boolean PNG = false;
			Boolean HTML = false;

			// parsear opciones
			// parsear opciones
			// parsear opciones
			// parsear opciones

			try {

				Pattern patronASC = Pattern.compile("asc=true");
				Pattern patronZIP = Pattern.compile("[^g]zip=true");
				Pattern patronGZIP = Pattern.compile("gzip=true");
				Pattern patronPNG = Pattern.compile(".png");
				Pattern patronHTML = Pattern.compile(".html");
				
				Matcher matcherASC = patronASC.matcher(opciones);
				Matcher matcherZIP = patronZIP.matcher(opciones);
				Matcher matcherGZIP = patronGZIP.matcher(opciones);
				Matcher matcherPNG = patronPNG.matcher(filename);
				Matcher matcherHTML = patronHTML.matcher(filename);

				if (matcherASC.find()) {
					ASC = true;
					System.out.println("\nOpción ASC habilitada\n");

				}
				if (matcherZIP.find() ) {
					ZIP = true;
					System.out.println("\nOpción ZIP habilitada\n");

				}
				if (matcherGZIP.find()) {
					GZIP = true;
					System.out.println("\nOpción GZIP habilitada\n");

				}
				if (matcherPNG.find()) {
					PNG =true;
					System.out.println("\nOpción PNG habilitada\n");

				}
				if (matcherHTML.find()) {
					HTML = true;
					System.out.println("\nOpción HTML habilitada\n");

				}
				
			} catch (PatternSyntaxException patternex) {
				System.out.println("Error al parsear las opciones del HTTP request!!!\n");
			}
			
			// parsear opciones
			// parsear opciones
			// parsear opciones
			// parsear opciones

			//Mensajes de consola para comprobar estados
			//Mensajes de consola para comprobar estados
			//Mensajes de consola para comprobar estados
			//Mensajes de consola para comprobar estados
			//Mensajes de consola para comprobar estados
			
			String sirviendo = "Sirviendo " + filename;
			String consoleLogASC = sirviendo + " con opción ASC";
			String consoleLogZIP = sirviendo + " con opción ZIP";
			String consoleLogGZIP = sirviendo + " con opción GZIP";
			String consoleLogPNG = sirviendo + " como PNG";
			String consoleLogHTML = sirviendo + " como HTML";

			
			String ok = "servido: ";
			String okASC = ok + "ASC";
			String okZIP = ok + "ZIP";
			String okGZIP = ok + "GZIP";
			String okPNG = ok + "PNG";
			String okHTML = ok + "HTML";

			
			//Mensajes de consola para comprobar estados
			//Mensajes de consola para comprobar estados
			//Mensajes de consola para comprobar estados
			//Mensajes de consola para comprobar estados
			//Mensajes de consola para comprobar estados

			// Cabeceras
			// Cabeceras
			// Cabeceras
			// Cabeceras
			// Cabeceras
			
			String cabeceraOK = "HTTP/1.1 200 OK\n";
			
			String cabeceraPNG = cabeceraOK + "Content-Type: image/png\n" + "Content-Disposition: attachment; filename=\""
					+ filename + ".png\"\n\n";

			String cabeceraZIP = cabeceraOK + "Content-Type: application/zip\n" + "Content-Disposition: attachment; filename=\""
					+ filename + ".zip\"\n\n";

			String cabeceraGZIP = cabeceraOK + "Content-Type: application/gzip\n" + "Content-Disposition: attachment; filename=\""
					+ filename + ".gz\"\n\n";
			
			String cabeceraZIPGZIP = cabeceraOK + "Content-Type: application/gzip\n" + "Content-Disposition: attachment; filename=\""
					+ filename + ".zip.gz\"\n\n";
			
			String cabeceraHTML = cabeceraOK + "Content-Type: text/html\n"  + "Content-Disposition: filename=\""
					+ filename + ".html\"\n\n";
			
			String respuestaError = "HTTP/1.1 404 Not Found\n\n" + "<!DOCTYPE><HTML><HEAD>Recurso: "
					+ filename + " no encontrado</HEAD></HTML>"; 
			
			String cabeceraFinal = "";

			// Cabeceras
			// Cabeceras
			// Cabeceras
			// Cabeceras
			// Cabeceras

			// filename= "index.html";

			System.out.println("Index of get: " + bufferRequest.indexOf("GET"));

			if ((bufferRequest.indexOf("GET") != -1)) {
				System.out.println("Nos han pedido el archivo con filename: " + filename);
				try {
					
					if (HTML){
						cabeceraFinal = cabeceraHTML;
						if (ASC){
							is = new AsciiInputStream(is);
						}						
					}
					
					if (PNG){
						cabeceraFinal = cabeceraPNG;
					}
					
					boolean controlZIPGZIP = false;
					
					if (ZIP){
						if (!GZIP){
							cabeceraFinal = cabeceraZIP;
							os.write(cabeceraFinal.getBytes());
							os = new ZipOutputStream(os);
							zip = new ZipEntry(filename);
							((ZipOutputStream) os ).putNextEntry(zip);
							}
							//os.flush();
						if (GZIP){							
							controlZIPGZIP = true;
							cabeceraFinal = cabeceraZIPGZIP;
							os.write(cabeceraFinal.getBytes());
							os = new ZipOutputStream(os);
							zip = new ZipEntry(filename);
							((ZipOutputStream) os ).putNextEntry(zip);
							os = new GZIPOutputStream(os);
							}
					}
					
					if (GZIP && !controlZIPGZIP){	
						
						cabeceraFinal = cabeceraGZIP;
						os.write(cabeceraFinal.getBytes());
						os = new GZIPOutputStream(os);
					}
					
					//os.write(cabeceraFinal.getBytes());
					os.flush();
					
					int carac;
					while ((carac= is.read()) !=-1)
			    	{
						if (carac != -2){
							os.write(carac);
						}
			    	}
					
					/* ??? como cierro el zipoutputstream dentro del gzip?
					if (GZIP || ZIP){
						((ZipOutputStream) os).finish();
					}
					*/
					os.flush();						
						
				} catch (FileNotFoundException excep) {
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
		
		System.out.println("\nDONE\n");
	}
}