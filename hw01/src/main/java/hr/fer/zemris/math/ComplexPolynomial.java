package hr.fer.zemris.math;

/**
 * An implementation of polynomial which is populated by complex number
 * @author Vito Sabalic
 *
 */
public class ComplexPolynomial {
	
	
	/**
	 * The factors of the polynomial
	 */
	private Complex[] factors;
	

	/**
	 * A constructor which assigns the provided factors to the current factors
	 * @param factors The provided factors
	 */
	public ComplexPolynomial(Complex... factors) {
		
		this.factors = factors;
		
	}
	

	/**
	 * @return Returns the order of the polynomial
	 */
	public short order() {
		
		return (short) (factors.length - 1);
	}
	

	/**
	 * Multiplies the current polynomial with the provided polynomial
	 * @param p The provided polynomial
	 * @return Returns a new {@link ComplexPolynomial} which contains the newly calculated values
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {

		
		Complex[] newPolynom = new Complex[factors.length + p.order()];
		
		for(int i = 0; i < newPolynom.length; i++) {
			newPolynom[i] = Complex.ZERO;
		}
		
		
		for(int i = 0; i < this.factors.length; i++) {
			
			for(int j = 0; j < p.getFactors().length; j++) {
				
				newPolynom[i + j] = factors[i].multiply(p.getFactors()[j]).add(newPolynom[i + j]);
			
			}
		}
		
		return new ComplexPolynomial(newPolynom);
	}
	

	/**
	 * Calculates a derivation of the current polynomial
	 * @return Returns a new {@link ComplexPolynomial} which contains the newly calculated values
	 */
	public ComplexPolynomial derive() {
		
		Complex[] newFactors = new Complex[factors.length - 1];
		
		for(int i = 0; i < newFactors.length; i++) {
			newFactors[i] = factors[i + 1].multiply(new Complex(i + 1, 0));
		}
		
		return new ComplexPolynomial(newFactors);
		
	}
	
	
	
	/**
	 * Applies the provided complex number to the current polynomial
	 * @param z The provided complex number
	 * @return Returns a new complex number which contains the newly calculated values
	 */
	public Complex apply(Complex z) {
		
		Complex func = new Complex();
		
		for(int i = 0; i < factors.length; i++) {
			
			func = func.add(factors[i].multiply(z.power(i)));
		}
		
		return func;
	}
	
	/**
	 * A getter for the factors of the polynomial
	 * @return Returns the factors of the current polynomial
	 */
	public Complex[] getFactors() {
		return factors;
	}
	
	@Override
	public String toString() {
		String s = new String();
		
		for(int i = factors.length - 1; i > 0; i-- ) {
			s += "(" + factors[i].toString() + ")*z^" + String.valueOf(i) + "+";
		}
		
		return s + "(" + factors[0].toString() + ")";
	}

}
