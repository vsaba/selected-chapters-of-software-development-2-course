package hr.fer.zemris.math;

/**
 * An representation of a polynomial that is consisted of complex roots
 * @author Vito Sabalic
 *
 */
public class ComplexRootedPolynomial {
	
	/**
	 * The constant of the polynomial
	 */
	private Complex constant;
	/**
	 * The roots of the polynomial
	 */
	private Complex[] roots;

	
	
	/**
	 * A constructor which assigns the provided values to the current values
	 * @param constant The provided constant
	 * @param roots The provided roots
	 */
	public ComplexRootedPolynomial(Complex constant, Complex... roots) {
		this.constant = constant;
		this.roots = roots;
	}
	
	/**
	 * Applies the provided complex number to the current polynomial
	 * @param z The provided complex number
	 * @return Returns a new complex number which contains the newly calculated values
	 */
	public Complex apply(Complex z) {
		Complex func = new Complex();
		
		func = func.multiply(constant);
		
		for(int i = 0; i < roots.length; i++) {
			
			func = func.multiply(z.sub(roots[i]));
		
		}
		
		return func;
	}
	
	/**
	 * Transforms the current polynomial into a {@link ComplexPolynomial}
	 * @return Returns a new {@link ComplexPolynomial} which contains the newly calculated values
	 */
	public ComplexPolynomial toComplexPolynom() {
			
		ComplexPolynomial polynom = new ComplexPolynomial(roots[0].negate(), Complex.ONE);
		
		for(int i = 1; i < roots.length; i++) {
			
			polynom = polynom.multiply(new ComplexPolynomial(roots[i].negate(), Complex.ONE));
		}
		
		
		polynom = polynom.multiply(new ComplexPolynomial(constant));
		
		
		return polynom;
	}
	
	
	/**
	 * Finds the index of the root which is the closest to the provided complex number
	 * @param z The provided complex number
	 * @param threshold The maximum distance of a root from the provided complex number
	 * @return If it exists, returns the index of the closest root, otherwise, returns -1
	 */
	public int indexOfClosestRootFor(Complex z, double threshold) {
		
		
		double dif = roots[0].sub(z).module();
		int index = 0;
		
		
		for(int i = 1; i < roots.length; i++) {
			
			double pom = roots[i].sub(z).module();
			
			if(pom < dif) {
				dif = pom;
				index = i;
			}
		}
		
		return dif < threshold ? index : -1;
	}
	
	
	/**
	 * A getter for the constant
	 * @return Returns the constant
	 */
	public Complex getConstant() {
		return constant;
	}
	
	/**
	 * A getter for the roots
	 * @return Returns the roots
	 */
	public Complex[] getRoots() {
		return roots;
	}
	
	@Override
	public String toString() {
		String s = "(" + constant.toString() + ")*";
		
		for(int i = 0; i < roots.length - 1; i++) {
			
			s += "(z-(" + roots[i].toString() + "))*";
			
		}
		
		return s + "(z-(" + roots[roots.length - 1] + "))";
	}

}
