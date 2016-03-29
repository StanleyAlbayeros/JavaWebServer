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
	
	static int BUFFER = 1024;

	
	
	
	String cabeceraPNG = cabeceraOK + "Content-Type: image/png\n" ; 

	int caracter;

	public BufferedReader bufferLectura;

	public Hilo(Socket client) throws IOException {
		this.clientSocket = client;
	}

	public void run() {

		InputStream is, is2, is3;;
		OutputStream os, os2;
		System.out.println("Estamos ejecutando desde un thread!");

		try {

			os = clientSocket.getOutputStream();
			os2 = clientSocket.getOutputStream();
			is = clientSocket.getInputStream();
			BufferedReader bufferLectura = new BufferedReader(new InputStreamReader(is));
			br = bufferLectura.readLine();
			BufferedOutputStream bos = new BufferedOutputStream(os2);
			ZipOutputStream zos = new ZipOutputStream(bos);
			System.out.println("Request recibida: " + br);

			String filename = br.split("/")[1].split(" HTTP")[0].split("\\?")[0];
			
			
			
			String opciones = "";
			try {
				opciones = br.split("\\?")[1].split(" HTTP")[0];
			} catch (ArrayIndexOutOfBoundsException e) {
				e.printStackTrace();
			}
			
			System.out.println("\n\n heeeeeeeeeey \n\n");		
			
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
						System.out.println("\nOpci�n ASC habilitada\n");
						
					}
					if (mz.find()){
						ZIP = Boolean.valueOf(mz.group(1));
						System.out.println("\nOpci�n ZIP habilitada\n");

					}
					if (mg.find()){
						GZIP = Boolean.valueOf(mg.group(1));
						System.out.println("\nOpci�n GZIP habilitada\n");

					}
					if (mp.find()){
						PNG = Boolean.valueOf(mp.group(1));
						System.out.println("\nOpci�n PNG habilitada\n");

					}
				} catch (PatternSyntaxException patternex){
				System.out.println("Error al parsear las opciones del HTTP request!!!\n");
			}
			//parsear opciones
			//parsear opciones
			//parsear opciones
			//parsear opciones
			String sirviendo = "Sirviendo " + filename;
			String consoleLogASC = sirviendo  + " con opci�n ASC";
			String consoleLogZIP = sirviendo  + " con opci�n ZIP";
			String consoleLogGZIP = sirviendo  + " con opci�n GZIP";
			String consoleLogPNG = sirviendo  + " con opci�n PNG";
			
			String ok = "servido!";
			
			String okASC = ok + "ASC";
			String okZIP = ok + "ZIP";
			String okGZIP = ok + "GZIP";
			String okPNG = ok + "PNG";


			//Cabeceras
			//Cabeceras
			//Cabeceras
			//Cabeceras
			//Cabeceras
			
			String cabeceraZip = cabeceraOK + "Content-Type: application/zip\n"
					+ "Content-Disposition: filename=\"" +filename+ ".zip\"\n\n";
			
			String cabeceraHTML = cabeceraOK + "Content-Type: text/html\n" + "\n";
			
			//Cabeceras
			//Cabeceras
			//Cabeceras
			//Cabeceras
			//Cabeceras
			
			
			
			// filename= "index.html";
			
			System.out.println("Index of get: " + br.indexOf("GET"));
			
			if ((br.indexOf("GET") != -1)) {
				System.out.println("Nos han pedido el archivo con filename: " + filename);
				try {
					
					
					if (ASC){
						
						System.out.println(consoleLogASC);
						is3 = new FileInputStream(filename);					
						is = new AsciiInputStream(is3);
						os.write(cabeceraHTML.getBytes());
						while ((caracter = is.read()) != -1) {
							os.write(caracter);
						}
						
						os.write("\r\n".getBytes());
						//os.write(System.getProperty("line.separator").getBytes());


						System.out.println(okASC);
						//os.flush();
					} 
					
					if (ZIP){
						
						System.out.println(consoleLogZIP);
						/////////////////
						is2 = new FileInputStream(filename);
						//os2 = clientSocket.getOutputStream();
						
						
						System.out.print(filename);
						
						
						os2.write(cabeceraZip.getBytes());
						zos.putNextEntry(new ZipEntry(filename));
						byte buffer[] = new byte[BUFFER];
						int length;
						while ((length = is2.read(buffer)) >0){
							zos.write(buffer,  0,  length);
						}
						os.write("\n\n".getBytes());
//						zos.flush();
						zos.closeEntry();

//						zos.finish();
//						zos.close();
						
						
						
						System.out.println(okZIP);
					}
					
					
					if (!ASC) {
						
						System.out.println("Sirviendo " + filename + " tal cual");
						is3 = new FileInputStream(filename);
						os2.write(cabeceraHTML.getBytes());
						while ((caracter = is3.read()) != -1) {
							os2.write(caracter);
						}
					}
					


				}catch (FileNotFoundException excep) {
					os.write(respuestaError.getBytes());
					excep.printStackTrace();
				}
			}
			
			is.close();
//			zos.closeEntry();
//			zos.close();
//			clientSocket.close();

		} catch (IOException excep) {
			excep.printStackTrace();
		}

		System.out.println("\nDONE\n");
	}
}