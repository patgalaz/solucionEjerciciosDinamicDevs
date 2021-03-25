package cl.pruebas;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Solución del ejercicio DDATM-ES-0001-E
 * 
 * @author Patricio Galaz Beltrán
 *
 */
public class ReemplazaLetrasAlfabeto {

	public static final String FIND_ALL_LETTERS_PATTERN = "([A-Za-z])";
	public static final int UPPER_A_CODEPOINT = 65;
	public static final int LOWER_A_CODEPOINT = 97;
	public static final int UPPER_Z_CODEPOINT = 90;
	public static final int LOWER_Z_CODEPOINT = 122;

	/**
	 * Recibe una cadena de texto y reemplaza todas las letras del abecedario que
	 * encuentre por su letra sucesora (de la "a" hasta la "z", tanto mayúsculas
	 * como minúsculas). Solo en caso de las letras "z" y "Z", son reemplazadas por
	 * las letras "a" y "A" respectivamente. Devuelve la cadena de texto modificada.
	 * 
	 * @param textInput cadena de texto cuyas letras se quiere reemplazar
	 * @return cadena de texto con los cambios en las letras aplicados.
	 */
	public static String findAndReplaceAllLettersMatches(String textInput) {
		System.out.print(String.format("Input = %s \t", textInput));
		Pattern findAllLetters = Pattern.compile(FIND_ALL_LETTERS_PATTERN);
		Matcher allLettersFound = findAllLetters.matcher(textInput);
		String letterFound = null;
		String newLetter = null;
		int startIndex = 0;
		int endIndex = 0;
		int inputLastIndex = 0;
		StringBuilder textOutput = new StringBuilder();

		while (allLettersFound.find()) {
			letterFound = allLettersFound.group(1);
			startIndex = allLettersFound.start();
			endIndex = allLettersFound.end();

			// Obtenemos el código Ascii decimal de la letra
			int codePoint = letterFound.codePointAt(0);

			// Validamos si la letra es "Z" o "z". En ese caso, la reemplazamos por "A" o
			// "a", respectivamente. Sino, incrementamos en 1 el código ascii contenido en
			// codePoint.
			switch (codePoint) {
			case UPPER_Z_CODEPOINT:
				codePoint = UPPER_A_CODEPOINT;
				break;
			case LOWER_Z_CODEPOINT:
				codePoint = LOWER_A_CODEPOINT;
				break;
			default:
				codePoint++;
				break;
			}

			// Obtenemos la letra que sigue en el código ascii
			newLetter = new String(Character.toChars(codePoint));
			textOutput.append(textInput, inputLastIndex, startIndex);
			textOutput.append(newLetter);
			inputLastIndex = endIndex;
		}

		// Si al terminar de iterar las letras encontradas aún queda texto en el input,
		// lo insertamos al final del output.
		if (endIndex < textInput.length()) {
			textOutput.append(textInput, endIndex, textInput.length());
		}
		String output = textOutput.toString();
		printOutput(output);
		return output;

	}

	public static void printOutput(String output) {
		System.out.print(String.format("Output = %s \n", output));
	}

	public static void main(String[] args) {

		findAndReplaceAllLettersMatches("123 abcd*3");
		findAndReplaceAllLettersMatches("**Casa 52");
		findAndReplaceAllLettersMatches("**Holz 52Za");
		findAndReplaceAllLettersMatches("_ zZzZbBcCdD123");

	}

}
