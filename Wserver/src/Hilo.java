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
			String respuestaError = "HTTP/1.1 404 Not Found\n\n" + "<!DOCTYPE><HTML><HEAD>Recurso: "
					+ filename + " no encontrado</HEAD></HTML>"; 

			
			System.out.println("\nRequest recibida: " + bufferRequest);

			String filename = bufferRequest.split("/")[1].split(" HTTP")[0].split("\\?")[0];
			
			String opciones = "";
			try {
				opciones = bufferRequest.split("\\?")[1].split(" HTTP")[0];

				System.out.println("\n\n antes del parseo de URL \n\n");
			} catch (ArrayIndexOutOfBoundsException e) {
				e.printStackTrace();
			}

			System.out.println("\n\n antes del parseo de URL \n\n");

			Boolean ASC = false;
			Boolean ZIP = false;
			Boolean GZIP = false;
			Boolean PNG = false;
			Boolean HTML = false;
			Boolean PDF = false;
			Boolean JPEG = false;
			Boolean GIF = false;
			Boolean ICO = false;
			
			// parsear opciones
			// parsear opciones
			// parsear opciones
			// parsear opciones

			try {

				Pattern patronASC = Pattern.compile("asc=true");
				Pattern patronZIP = Pattern.compile("(?<=&|^)zip=true"); 	// FINALLY: probando opciones de la api ?<=X busca X con positive lookbehind
																			// y ^ es start of line. Operador | como en cualquier otro sitio es un OR
				Pattern patronGZIP = Pattern.compile("gzip=true");
				Pattern patronPNG = Pattern.compile(".png");
				Pattern patronHTML = Pattern.compile(".html");
				Pattern patronPDF = Pattern.compile(".pdf");
				Pattern patronJPEG = Pattern.compile(".jpeg");
				Pattern patronGIF = Pattern.compile(".gif");
				Pattern patronICO = Pattern.compile(".ico");
				
				Matcher matcherASC = patronASC.matcher(opciones);
				Matcher matcherZIP = patronZIP.matcher(opciones);
				Matcher matcherGZIP = patronGZIP.matcher(opciones);
				Matcher matcherPNG = patronPNG.matcher(filename);
				Matcher matcherHTML = patronHTML.matcher(filename);
				Matcher matcherPDF = patronPDF.matcher(filename);
				Matcher matcherJPEG = patronJPEG.matcher(filename);
				Matcher matcherGIF = patronGIF.matcher(filename);
				Matcher matcherICO = patronICO.matcher(filename);

				if (matcherASC.find()) {
					ASC = true;
					System.out.println("\nFiletype: ASC \n");

				}
				if (matcherZIP.find() ) {
					ZIP = true;
					System.out.println("\nFiletype: ZIP \n");
					}
				
				if (matcherGZIP.find()) {
					GZIP = true;
					System.out.println("\nFiletype: GZIP \n");
					}
				
				if (matcherPNG.find()) {
					PNG =true;
					System.out.println("\nFiletype: PNG \n");
					}
				
				if (matcherHTML.find()) {
					HTML = true;
					System.out.println("\nFiletype: HTML \n");
					}
				
				if (matcherPDF.find()) {
					PDF =true;
					System.out.println("\nFiletype: PDF");
					}
				
				if (matcherJPEG.find()) {
					JPEG =true;
					System.out.println("\nFiletype: JPEG");
					}
				
				if (matcherGIF.find()) {
					GIF =true;
					System.out.println("\nFiletype: GIF");
					}
				
				if (matcherICO.find()) {
					ICO =true;
					System.out.println("\nFiletype: ICO");
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
			
			String sirviendo = "Sending " + filename;
			String consoleLogASC = sirviendo + " with option ASC";
			String consoleLogZIP = sirviendo + " as ZIP";
			String consoleLogGZIP = sirviendo + " as GZIP";
			String consoleLogPNG = sirviendo + " as PNG";
			String consoleLogHTML = sirviendo + " as HTML";
			String consoleLogPDF = sirviendo + " as PDF";
			String consoleLogJPEG = sirviendo + " as JPEG";
			String consoleLogGIF = sirviendo + " as GIF";
			String consoleLogICO = sirviendo + " as ICO";
			

			
			String ok = "Sent " + filename + "as: ";
			String okASC = ok + "ASC";
			String okZIP = ok + "ZIP";
			String okGZIP = ok + "GZIP";
			String okPNG = ok + "PNG";
			String okHTML = ok + "HTML";
			String okPDF = ok + "PDF";
			String okJPEG = ok + "JPEG";
			String okGIF = ok + "GIF";
			String okICO = ok + "ICO";

			
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

			String cabeceraZIP = cabeceraOK + "Content-Type: application/zip\n" + "Content-Disposition: attachment; filename=\""
					+ filename + ".zip\"\n\n";

			String cabeceraGZIP = cabeceraOK + "Content-Type: application/gzip\n" + "Content-Disposition: attachment; filename=\""
					+ filename + ".gz\"\n\n";
			
			String cabeceraZIPGZIP = cabeceraOK + "Content-Type: application/gzip\n" + "Content-Disposition: attachment; filename=\""
					+ filename + ".zip.gz\"\n\n";
			
			String cabeceraHTML = cabeceraOK + "Content-Type: text/html\n\n";
			
			String cabeceraHTMLASC = cabeceraOK + "Content-Type: text/html\n"  + "Content-Disposition: filename=\""
					+ filename + ".asc\"\n\n";
						
			String cabeceraPNG = cabeceraOK + "Content-Type: image/png\n\n";
			
			String cabeceraPDF = cabeceraOK + "Content-Type: application/pdf\n\n";
			
			String cabeceraJPEG = cabeceraOK + "Content-Type: image/jpeg\n" + "Content-Disposition: attachment; filename=\""
					+ filename + "\"\n\n";
			
			String cabeceraGIF = cabeceraOK + "Content-Type: image/gif\n" + "Content-Disposition: attachment; filename=\""
					+ filename + "\"\n\n";
			
			String cabeceraICO = cabeceraOK + "Content-Type: image/x-icon\n" + "Content-Disposition: attachment; filename=\""
					+ filename + "\"\n\n";
						
			String cabeceraFinal = "";

			// Cabeceras
			// Cabeceras
			// Cabeceras
			// Cabeceras
			// Cabeceras

			// filename= "index.html";

			System.out.println("Index of getete fk: " + bufferRequest.indexOf("GET"));

			if ((bufferRequest.indexOf("GET") != -1)) {
				System.out.println("Nos han pedido el archivo con filename: " + filename);
				try {
					
					is = new FileInputStream(filename);
					
					if (PNG){
						cabeceraFinal = cabeceraPNG;
					}
					
					if (PDF){
						cabeceraFinal = cabeceraPDF;						
					}
					
					if (JPEG){
						cabeceraFinal = cabeceraJPEG;						
					}
					
					if (GIF){
						cabeceraFinal = cabeceraGIF;						
					}
					
					if (ICO){
						cabeceraFinal = cabeceraICO;						
					}
					
					if (HTML){
						cabeceraFinal = cabeceraHTML;
						if (ASC){
							cabeceraFinal = cabeceraHTMLASC;
							is = new AsciiInputStream(is);
						}						
					}
					
					if (GZIP){
						if (ZIP){
							cabeceraFinal = cabeceraZIPGZIP;
							os.write(cabeceraFinal.getBytes());
							os.flush();
							os = new GZIPOutputStream(os);
							os = new ZipOutputStream(os);
							zip = new ZipEntry(filename);
							((ZipOutputStream) os ).putNextEntry(zip);
						} else {
							cabeceraFinal = cabeceraGZIP;
							os.write(cabeceraFinal.getBytes());
							os.flush();
							os = new GZIPOutputStream(os);	
						}
						
					}
					
					if (ZIP && !GZIP){
						cabeceraFinal = cabeceraZIP;
						os.write(cabeceraFinal.getBytes());
						os.flush();
						os = new ZipOutputStream(os);
						zip = new ZipEntry(filename);
						((ZipOutputStream) os ).putNextEntry(zip);
					}
					
					if (!GZIP && !ZIP){
						os.write(cabeceraFinal.getBytes());
					}
					
					os.flush();
					int caracter;
					while ((caracter= is.read()) !=-1)
			    	{
						if (caracter != -2){
							os.write(caracter);
						}
			    	}			
						
				} catch (FileNotFoundException excep) {
					os.write(respuestaError.getBytes());
					excep.printStackTrace();
			}
		}
			
		os.close();
		is.close();
		clientSocket.close();
			
	} catch (IOException excep) {
		excep.printStackTrace();
	}
		
		System.out.println("\nDONE\n");
	}
}