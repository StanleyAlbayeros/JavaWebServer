
import java.io.IOException;
import java.io.InputStream;
import java.io.FilterInputStream;

public class AsciiInputStream extends FilterInputStream {
	
	private boolean asci=false;
	/**
	 * Constructor de la clase.
	 * @param in es el Stream d'entrada que rep els mètodes de la clase pare FilterInputStream.
	 */
	public AsciiInputStream(InputStream in) {
		super(in);
		
		// TODO Auto-generated constructor stub
	}
	/**
	 *  Aquest mètode llegeix els caracters i els compara amb els  caràcters dels tags per saber si ha trobat algun.
	 * @throws IOException Si no es pot llegir el caracter llença l'exepció.
	 * @return retorna el caracter.
	 */

		public int read() throws IOException
		{
			int c = super.read();
			int r=-2;
			
			if (c=='<'){
        		asci=true;
        		r=-2;}
        
        	if(!asci){
        		r=c;
        	}
        
        	if (c=='>'){
        		asci=false;
        		r=-2;}
			
			return r;
		}
    
 }        
        	

