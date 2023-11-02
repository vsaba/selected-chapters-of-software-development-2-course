package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

public class ComplexTest {
	
	@Test
	public void addTests(){
		Complex num = new Complex(1, 1);
		Complex num1 = new Complex(-1, -1);
		
		Complex num2 = num.add(num1);
		
		assertEquals(0, num2.getReal());
		assertEquals(0, num2.getImaginary());
	}
	
	@Test
	public void subTests() {
		Complex num = new Complex(1, 1);
		Complex num1 = new Complex(-1, -1);
		
		Complex num2 = num.sub(num1);
		
		assertEquals(2, num2.getReal());
		assertEquals(2, num2.getImaginary());
		
	}
	
	@Test
	public void mulTests() {
		
		Complex num = new Complex(2, -1);
		Complex num1 = new Complex(3, 4);
		
		Complex num2 = num.multiply(num1);
		
		assertEquals(10, num2.getReal());
		assertEquals(5, num2.getImaginary());
		
	}
	
	@Test
	public void divTests() {
		Complex num = new Complex(2, 0);
		Complex num1 = new Complex(1, 1);
		
		Complex num2 = num.divide(num1);

		
		assertEquals(1, num2.getReal());
		assertEquals(-1, Math.round(num2.getImaginary()));
		
	}
	
	@Test
	public void powerTest() {
		Complex num = new Complex(0, 1);
		
		num = num.power(2);
		
		assertEquals(-1, num.getReal());
		assertEquals(0, Math.round(num.getImaginary()));
		
		
	}
	
	@Test
	public void rootTest() {
		Complex num = new Complex(0, 2);
		
		List<Complex> expected = num.root(2);
		
		assertEquals(1, Math.round(expected.get(0).getReal() * 100) / 100);
		assertEquals(1, Math.round(expected.get(0).getImaginary() * 100) / 100);
		
		assertEquals(-1, Math.round(expected.get(1).getReal() * 100) / 100);
		assertEquals(-1, Math.round(expected.get(1).getImaginary() * 100) / 100);
		

	}

}
