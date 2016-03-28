import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class AsciiInputStream extends FilterInputStream {
	
	private int caracter;
	private boolean paso=false;
	
	protected AsciiInputStream (InputStream in)
	{
		super(in);
	}
	
	public int read() throws IOException { 	

		// metodo a heredar. tiramos la excepción con throws 
		// de la clase IOException que se encarga de notificar de problemas de i/o
		
		caracter= in.read();
		if (caracter=='<')
		{
			paso=true;
		}
		if(!paso)
		{
			return caracter;
		}
		else
		{
			while (caracter!='>')
			{
					caracter= in.read();
			}
			paso=false;
			return read();
		}
	}

}