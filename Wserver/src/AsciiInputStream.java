import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class AsciiInputStream extends FilterInputStream {

<<<<<<< HEAD
	private int caracter;
	private boolean paso = false;
=======
	
	
//	private boolean paso = false;
>>>>>>> parent of 698c758... reworked?

	protected AsciiInputStream(InputStream in) {
		super(in);
	}

	public int read() throws IOException {
		//
		// metodo a heredar. tiramos la excepcion con throws
		// de la clase IOException que se encarga de notificar de problemas de
		// i/o
<<<<<<< HEAD

=======
		
///
		int caracter;
		while ( ( caracter = this.in.read() ) == '<'){
			
			while ( ( caracter = this.in.read() ) != '>'){
			};
			
		}
		return caracter;
///
/*
>>>>>>> parent of 698c758... reworked?
		caracter = in.read();

		if (caracter == '<') {
			paso = true;
		}
		if (!paso) {
			return caracter;
		} else {
			while (caracter != '>') {
				caracter = in.read();
			}
			paso = false;
			return read();
		}
<<<<<<< HEAD
=======
		
*/
>>>>>>> parent of 698c758... reworked?
	}

}