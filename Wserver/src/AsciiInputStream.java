import java.io.IOException;
import java.io.InputStream;
import java.io.FilterInputStream;

public class AsciiInputStream extends FilterInputStream {
	
	private boolean asci=false;
	public AsciiInputStream(InputStream in) {
		super(in);
	}
		public int read() throws IOException
		{
			int caracter = super.read();
			int control=-2;
			
			if (caracter=='<'){
        		asci=true;
        		control=-2;}
        
        	if(!asci){
        		control=caracter;
        	}
        
        	if (caracter=='>'){
        		asci=false;
        		control=-2;}
			
			return control;
		}
    
 }        
        	

