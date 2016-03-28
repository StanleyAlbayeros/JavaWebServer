import java.io.*;
import java.net.*;
import java.util.regex.*;
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

			String filename = br.split("/")[1].split("\\?")[0];
			
			String opciones = br.split("\\?")[1].split(" HTTP")[0];
			
			Boolean ASC = false;
			Boolean ZIP = false;
			Boolean GZIP = false;
			Boolean PNG = false;
			
			//parsear opciones
			//parsear opciones
			//parsear opciones
			//parsear opciones
			
			try {
					Pattern patronASC = Pattern.compile("asc=([^&]+)");
					Pattern patronZIP = Pattern.compile("zip=([^&]+)");
					Pattern patronGZIP = Pattern.compile("gzip=([^&]+)");
					Pattern patronPNG = Pattern.compile("png=([^&]+)");
					
					Matcher ma = patronASC.matcher(opciones);
					Matcher mz = patronZIP.matcher(opciones);
					Matcher mg = patronGZIP.matcher(opciones);
					Matcher mp = patronPNG.matcher(opciones);
					
					if (ma.find()){
						ASC = Boolean.valueOf(ma.group(1));
						System.out.println("\nOpción ASC habilitada\n");
						
					}
					if (mz.find()){
						ZIP = Boolean.valueOf(mz.group(1));
						System.out.println("\nOpción ZIP habilitada\n");

					}
					if (mg.find()){
						GZIP = Boolean.valueOf(mg.group(1));
						System.out.println("\nOpción GZIP habilitada\n");

					}
					if (mp.find()){
						PNG = Boolean.valueOf(mp.group(1));
						System.out.println("\nOpción PNG habilitada\n");

					}
				} catch (PatternSyntaxException patternex){
				System.out.println("Error al parsear las opciones del HTTP request!!!\n");
			}
			//parsear opciones
			//parsear opciones
			//parsear opciones
			//parsear opciones
			String consoleLogASC = "Sirviendo " + filename + " con opción ASC";
			
			
			String cabeceraZip = cabeceraOK + "Content-Type: application/zip\n"
					+ "Content-Disposition: filename=\"" +filename+ ".zip\"n\n";
			
			String cabeceraHTML = cabeceraOK + "Content-Type: text/html\n" + "\n";
			
			
			
			// filename= "index.html";
			
			System.out.println("Index of get: " + br.indexOf("GET"));
			
			if ((br.indexOf("GET") != -1)) {
				System.out.println("Nos han pedido el archivo con filename: " + filename);
				try {
					
					if (ASC){
						
						System.out.println("Opcion ASC activa, sirviendo archivo en texto! " + filename);

						System.out.println(consoleLogASC);
						is2 = new FileInputStream(filename);					
						is = new AsciiInputStream(is2);
						os.write(cabeceraHTML.getBytes());
						while ((caracter = is.read()) != -1) {
							os.write(caracter);
						}
					}
						
//					os2 = clientSocket.getOutputStream();					
//					os.write(cabeceraZip.getBytes());//					
//					os = new ZipOutputStream(os2);//
//					ZipEntry zipEntry = new ZipEntry(filename);
//					((ZipOutputStream) os).putNextEntry(zipEntry);
					

				}catch (FileNotFoundException excep) {
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