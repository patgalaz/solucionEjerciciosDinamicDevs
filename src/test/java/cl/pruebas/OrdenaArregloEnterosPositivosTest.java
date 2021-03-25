package cl.pruebas;

import org.junit.Assert;
import org.junit.Test;


public class OrdenaArregloEnterosPositivosTest {
	@Test
	public void testArrayWithoutChanges() throws Exception {
		int[] intArray = { 1, 2, 3 };
		Assert.assertArrayEquals(intArray, OrdenaArregloEnterosPositivos.orderAndCompleteIntegersArray(intArray));
	}
	
	@Test
	public void testMessyArrayOne() throws Exception {
		int[] intArray = { 2, 1, 4, 5 };
		int[] expectedArray = { 1, 2, 3, 4, 5};
		Assert.assertArrayEquals(expectedArray, OrdenaArregloEnterosPositivos.orderAndCompleteIntegersArray(intArray));
	}
	
	@Test
	public void testMessyArrayTwo() throws Exception {
		int[] intArray = { 4, 2, 9 };
		int[] expectedArray = { 1, 2, 3, 4, 5, 6, 7, 8, 9};
		Assert.assertArrayEquals(expectedArray, OrdenaArregloEnterosPositivos.orderAndCompleteIntegersArray(intArray));
	}

}
