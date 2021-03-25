package cl.pruebas;

import java.util.Arrays;

/**
 * Solución del ejercicio DDATM-ES-0002-E
 * 
 * @author Patricio Galaz Beltrán
 *
 */
public class OrdenaArregloEnterosPositivos {

	/**
	 * Recibe un arreglo de valores enteros positivos y los ordena ascendentemente.
	 * Luego, verifica que el rango contenido dentro del array contenga todos los
	 * números enteros positivos entre el 1 y el máximo valor contenido en el array.
	 * Si faltan números, completa el array insertando los números faltantes.
	 * Finalmente retorna el array con los cambios aplicados.
	 * 
	 * @param inputArray arreglo con valores enteros positivos
	 * @return arreglo con los valores enteros positivos ordenados en forma
	 *         ascendente y completado con todos los valores enteros entre 1 y el
	 *         valor máximo contenido en el inputArray.
	 * @throws Exception
	 */
	public static int[] orderAndCompleteIntegersArray(int[] inputArray) throws Exception {
		if (inputArray == null || inputArray.length == 0) {
			throw new Exception("Array de enteros es nulo o vacío.  No hay nada que ordenar.");
		}

		printArray(inputArray, "Input", false, false);

		int[] outputArray = Arrays.copyOf(inputArray, inputArray.length);

		// ordenamos el array
		Arrays.sort(outputArray);

		// Ahora completamos los vacíos
		outputArray = fillJumpsBetweenIntegerValues(outputArray, 0);
		printArray(outputArray, "Output", true, true);
		return outputArray;
	}

	public static void printArray(int[] intArray, String arrayName, boolean initialSpace, boolean finalLineBreak) {
		String lineInit = "";
		if (initialSpace) {
			lineInit = "\t";
		}
		System.out.print(String.format("%s%s = [", lineInit, arrayName));
		for (int index = 0; index < intArray.length; index++) {
			System.out.print(intArray[index]);
			if (index < intArray.length - 1) {
				System.out.print(", ");
			}
		}

		if (finalLineBreak) {
			System.out.println("]");
		} else {
			System.out.print("]");
		}
	}

	/**
	 * Verifica que no haya saltos entre 2 números dentro de un array de valores
	 * enteros ordenado ascendentemente.
	 * 
	 * @param originalIntArray array de valores enteros ordenado ascendentemente
	 * @param startIndex       índice de la posición del array desde la cual se
	 *                         empieza a verificar si existen saltos entre los
	 *                         valores que contiene.
	 * @return copia del array original, pero con todos los valores enteros que
	 *         faltaban dentro del rango hasta completar el rango ascendente desde 1
	 *         hasta el valor máximo contenido por el array.
	 * @throws Exception
	 */
	public static int[] fillJumpsBetweenIntegerValues(int[] originalIntArray, int startIndex) throws Exception {
		// Generamos una copia de trabajo del array original
		int[] currentIntArray = Arrays.copyOf(originalIntArray, originalIntArray.length);

		int numberOfPositionsToAdd = 0;
		int[] newIntArray = null;
		int sourceStartIndex = 0;
		int sourceEndIndex = 0;
		int targetStartIndex = 0;
		int intValueToAdd = 1;
		int targetCurrentIndex = 0;

		// primero validamos si el primer entero del array es 1. Sino, se genera un
		// nuevo array que incluya todos los enteros que anteceden al primer entero
		// del array original + el array original
		if (currentIntArray[0] > 1) {
			// Primero llenamos los valores enteros consecutivos que faltan al inicio del
			// array
			numberOfPositionsToAdd = currentIntArray[0] - 1;
			newIntArray = new int[currentIntArray.length + numberOfPositionsToAdd];
			fillWithConsecutiveIntValues(newIntArray, targetCurrentIndex, intValueToAdd, numberOfPositionsToAdd);

			// Luego copiamos los valores del array actual
			sourceStartIndex = 0;
			sourceEndIndex = currentIntArray.length;
			targetStartIndex = numberOfPositionsToAdd;
			copyValuesRangeToTargetArray(currentIntArray, newIntArray, sourceStartIndex, sourceEndIndex,
					targetStartIndex);
			currentIntArray = newIntArray;
		}

		for (int currentIndex = startIndex; currentIndex < currentIntArray.length - 1; currentIndex++) {
			// validamos la diferencia entre el valor de la posición actual y el valor de la
			// posición que le sigue
			int diferenceBetweenValues = currentIntArray[currentIndex + 1] - currentIntArray[currentIndex];

			// Si hay una diferencia superior a uno, definimos un nuevo array con un largo
			// que permita agregar los números faltantes entre los valores comparados.
			if (diferenceBetweenValues > 1) {
				// Creamos el nuevo array con los espacios necesarios
				numberOfPositionsToAdd = diferenceBetweenValues - 1;
				newIntArray = new int[currentIntArray.length + numberOfPositionsToAdd];
				sourceStartIndex = 0;
				sourceEndIndex = currentIndex + 1;
				targetStartIndex = 0;

				// Copiamos los valores en el nuevo array, pero sólo hasta el número que
				// presenta diferencia superior a 1 con su sucesor en el array de origen.
				copyValuesRangeToTargetArray(currentIntArray, newIntArray, sourceStartIndex, sourceEndIndex,
						targetStartIndex);

				// Ahora completamos con los números faltantes
				intValueToAdd = currentIntArray[currentIndex] + 1;
				targetCurrentIndex = currentIndex + 1;
				fillWithConsecutiveIntValues(newIntArray, targetCurrentIndex, intValueToAdd, numberOfPositionsToAdd);

				// finalmente agregamos el resto del array original
				sourceStartIndex = currentIndex + 1;
				sourceEndIndex = currentIntArray.length;
				targetStartIndex = targetCurrentIndex + numberOfPositionsToAdd;
				copyValuesRangeToTargetArray(currentIntArray, newIntArray, sourceStartIndex, sourceEndIndex,
						targetStartIndex);

				// Para seguir completando los números faltantes más adelante en la secuencia,
				// realizamos una llamada recursiva a este mismo método.
				return fillJumpsBetweenIntegerValues(newIntArray, targetStartIndex);
			}
		}
		// Llegados a este punto, quiere decir que no faltaba ningún número en la
		// secuencia de enteros del array
		return currentIntArray;
	}

	/**
	 * Llena un array de enteros con valores enteros consecutivos ascendentes. A
	 * partir de la posición indicada (initialFillingPosition), llena partiendo del
	 * valor inicial de llenado (initialFillValue), el cual va incrementando en 1 la
	 * cantidad de veces que se indica (numberOfPositionsToFill)
	 * 
	 * @param arrayToFill             array que será lleando con valores enteros
	 *                                ascendentes.
	 * @param initialFillingPosition  posición del array en que se inicia el llenado
	 *                                de valores.
	 * @param initialFillValue        valor inicial del llenado de valores, el cual
	 *                                se va incrementando en 1 por cada nueva
	 *                                posición que se llena en el array.
	 * @param numberOfPositionsToFill cantidad de posiciones que deben llenarse
	 *                                dentro del array.
	 */
	public static void fillWithConsecutiveIntValues(int[] arrayToFill, int initialFillingPosition, int initialFillValue,
			int numberOfPositionsToFill) throws Exception {
		if (arrayToFill == null || arrayToFill.length == 0) {
			throw new Exception("Array a llenar es nulo o vacío.  No hay lugar para insertar valores.");
		}

		if (numberOfPositionsToFill > (arrayToFill.length - initialFillingPosition)) {
			throw new Exception(String.format(
					"No se puede llenar %d posiciones, a partir de la posición %d, en un array de largo %d (se excede el largo del array).",
					numberOfPositionsToFill, initialFillValue, arrayToFill.length));

		}

		int currentFillingPosition = initialFillingPosition;
		int currentFillValue = initialFillValue;
		for (int count = 0; count < numberOfPositionsToFill; count++) {
			arrayToFill[currentFillingPosition++] = currentFillValue++;
		}

	}

	/**
	 * Copia un rango de valores de un array de enteros origen hacia un array de
	 * enteros destino
	 * 
	 * @param sourceArray      array de enteros origen desde donde se obtiene el
	 *                         rango de valores a copiar.
	 * @param targetArray      array de enteros de destino hacia el cual se copia el
	 *                         rango de valores.
	 * @param sourceStartIndex índice inicial del rango de valores que serán
	 *                         copiados.
	 * @param sourceEndIndex   índice final del rango, el cual no se incluye en el
	 *                         rango a copiar (se copia hasta la posición anterior a
	 *                         este índice).
	 * @param targetStartIndex índice del array de destino desde el cual se copia el
	 *                         rango de valores.
	 */
	public static void copyValuesRangeToTargetArray(int[] sourceArray, int[] targetArray, int sourceStartIndex,
			int sourceEndIndex, int targetStartIndex) throws Exception {
		if (sourceArray == null || sourceArray.length == 0) {
			throw new Exception("Array de origen es nulo o vacío.  No hay nada que copiar.");
		}

		if (targetArray == null || targetArray.length == 0) {
			throw new Exception("Array de destino es nulo o vacío.  No hay lugar para copiar valores dentro.");
		}

		if (sourceStartIndex >= sourceEndIndex) {
			throw new Exception(String.format(
					"El inicio del rango de valores a copiar (%d) no puede ser mayor o igual que el final del rango de copia (%d)",
					sourceStartIndex, sourceEndIndex));
		}

		int targetIndex = targetStartIndex;
		for (int sourceIndex = sourceStartIndex; sourceIndex < sourceEndIndex; sourceIndex++) {
			targetArray[targetIndex++] = sourceArray[sourceIndex];
		}

	}

	public static void main(String[] args) {
		try {
			int[] intArray = { 1, 2, 3 };
			orderAndCompleteIntegersArray(intArray);

			int[] intArray1 = { 2, 1, 4, 5 };
			orderAndCompleteIntegersArray(intArray1);

			int[] intArray2 = { 4, 2, 9 };
			orderAndCompleteIntegersArray(intArray2);

			int[] intArray3 = { 58, 60, 55 };
			orderAndCompleteIntegersArray(intArray3);
		} catch (Exception ex) {
			System.out.println("\nSe produjo un error y se ha detenido la ejecución del programa.");
			ex.printStackTrace();
		}

	}

}
