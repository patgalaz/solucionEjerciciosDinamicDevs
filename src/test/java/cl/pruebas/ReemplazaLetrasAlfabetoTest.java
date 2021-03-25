package cl.pruebas;

import org.junit.Assert;
import org.junit.Test;

public class ReemplazaLetrasAlfabetoTest {
	
	@Test
	public void testReplaceOne() {
		Assert.assertEquals("123 bcde*3", ReemplazaLetrasAlfabeto.findAndReplaceAllLettersMatches("123 abcd*3"));
	}
	
	@Test
	public void testReplaceTwo() {
		Assert.assertEquals("**Dbtb 52", ReemplazaLetrasAlfabeto.findAndReplaceAllLettersMatches("**Casa 52"));
	}
	
	@Test
	public void testReplaceThree() {
		Assert.assertEquals("**Ipma 52Ab", ReemplazaLetrasAlfabeto.findAndReplaceAllLettersMatches("**Holz 52Za"));
	}
	
	@Test
	public void testReplaceFour() {
		Assert.assertEquals("_ aAaAcCdDeE123", ReemplazaLetrasAlfabeto.findAndReplaceAllLettersMatches("_ zZzZbBcCdD123"));
	}

}
