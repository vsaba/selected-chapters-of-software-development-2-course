package hr.fer.zemris.math;

import java.util.ArrayList;

import java.util.List;

/**
 * An implementation of a complex number
 * @author Vito Sabalic
 *
 */
public class Complex {
	
	/**
	 * The real value
	 */
	private double re;
	/**
	 * The imaginary value
	 */
	private double im;
	
	
	/**
	 * Represents a zero value complex number
	 */
	public static final Complex ZERO = new Complex(0, 0);
	/**
	 * Represents a 1 value complex number
	 */
	public static final Complex ONE = new Complex(1, 0);
	/**
	 * Represents a -1 value complex number
	 */
	public static final Complex ONE_NEG = new Complex(-1, 0);
	/**
	 * Represents a i1 value complex number
	 */
	public static final Complex IM = new Complex(0, 1);
	/**
	 * Represents a -i1 value complex number
	 */
	public static final Complex IM_NEG = new Complex(0, -1);
	
	
	/**
	 * A simple constructor which assigns the variables to their default values
	 */
	public Complex() {
		this.re = 0;
		this.im = 0;
	}
	
	
	/**
	 * A constructor which assigns the variables to their respective provided values
	 * @param re
	 * @param im
	 */
	public Complex(double re, double im) {
		
		this.re = re;
		this.im = im;
		
	}
	
	
	/**
	 * Calculates the module of the complex number
	 * @return Returns the module
	 */
	public double module() {
		
		double mod = Math.sqrt(Math.pow(this.re, 2) + Math.pow(this.im, 2));
		
		return mod;
	}
	
	/**
	 * Multiplies the current complex number with the provided complex number
	 * @param c The provided complex number
	 * @return Returns a new complex number which has the newly calculated values
	 */
	public Complex multiply(Complex c) {
		
		double newRe = (this.re * c.getReal()) - (this.im * c.getImaginary());
		double newIm = (this.re * c.getImaginary()) + (c.getReal() * this.im);
		
		return new Complex(newRe, newIm);
		
	}
	
	/**
	 * Divides the current complex number with the provided complex number
	 * @param c The provided complex number
	 * @return Returns a new complex number which has the newly calculated values
	 */
	public Complex divide(Complex c) {
		
		double newModule = this.module() / c.module();
		double newAngle = getAngle(this) - getAngle(c);
		
		double newRe = newModule * Math.cos(newAngle);
		double newIm = newModule * Math.sin(newAngle);
		
		return new Complex(newRe, newIm);
	}
	
	/**
	 * Adds the current complex number to the provided complex number
	 * @param c The provided complex number
	 * @return Returns a new complex number which has the newly calculated values
	 */
	public Complex add(Complex c) {
		
		double newRe = this.re + c.getReal();
		double newIm = this.im + c.getImaginary();

		return new Complex(newRe, newIm);
	}
	
	/**
	 * Subtracts the provided complex number from the current complex number
	 * @param c The provided complex number
	 * @return Returns a new complex number which has the newly calculated values
	 */
	public Complex sub(Complex c) {
		
		double newRe = this.re - c.getReal();
		double newIm = this.im - c.getImaginary();
		
		return new Complex(newRe, newIm);
	}
	
	/**
	 * Negates the current complex number
	 * @return Returns a new complex number which has the newly calculated values
	 */
	public Complex negate() {
		
		return new Complex(this.re * -1, this.im * -1);
		
	}
	
	/**
	 * Calculates the n-th power of the current complex number
	 * @param n The power to be calculated
	 * @return Returns a new complex number which has the newly calculated values
	 */
	public Complex power(int n) {
		
		double newModule = Math.pow(this.module(), n);
		
		double newRe = newModule * Math.cos(n * getAngle(this));
		double newIm = newModule * Math.sin(n * getAngle(this));
		
		
		return new Complex(newRe, newIm);
	}
	
	/**
	 * Calculates the n-th root of the current complex number
	 * @param n The root to be calculated
	 * @return Returns a new complex number which has the newly calculated values
	 */
	public List<Complex> root(int n){
		
		if(n < 0) {
			throw new IllegalArgumentException("The provided n-th power cannot be negative");
		}
		
		List<Complex> roots = new ArrayList<>();
		
		double newModule = Math.pow(this.module(), 1/(float)n);
		
		double newRe;
		double newIm;
		
		for(int i = 0; i < n; i++) {
			
			newRe = newModule * Math.cos((getAngle(this) + 2 * i * Math.PI) / n);
			newIm = newModule * Math.sin((getAngle(this) + 2 * i * Math.PI) / n);
			
			roots.add(new Complex(newRe, newIm));
			
		}
		
		return roots;
	}
	
	/**
	 * Parses a complex number from the provided string
	 * @param s The provided string
	 * @return Returns a new complex number which has the parsed values
	 */
	public static Complex parse(String s) {
		
		s = s.replaceAll("\\s", "");
		
		String re = "";
		String im = "";
		if((s.contains("+") && s.lastIndexOf("+") > 0) || (s.contains("-") && s.lastIndexOf("-") > 0)) {
			

			
			s = s.replaceAll("i", "");
			
			if(s.lastIndexOf("+") > 0) {
				re = s.substring(0, s.lastIndexOf("+"));
				im = s.substring(s.lastIndexOf("+"), s.length());
				
			}
			
			else if(s.lastIndexOf("-") > 0) {
				re = s.substring(0, s.lastIndexOf("-"));
				im = s.substring(s.lastIndexOf("-"), s.length());
			}
			
			if(im.length() == 1) {
				im = im.concat("1");
			}
			
			double newRe = Double.parseDouble(re);
			double newIm = Double.parseDouble(im);
			
			return new Complex(newRe, newIm);
			
		}
		
		else {
			if(s.contains("i")) {				
				im = s.replaceAll("i", "");
				if(im.length() == 1 && (im.contains("+") || im.contains("-"))) {
					im = im.concat("1");
				}
				else if(im.length() == 0) {
					im = "1";
				}
				
				return new Complex(0, Double.parseDouble(im));
				
			}
			else {
				re = s;
				
				return new Complex(Double.parseDouble(re), 0);
				
			}
		}
	}
	
	/**
	 * A getter for the real value
	 * @return Returns the real value
	 */
	public double getReal() {
		return this.re;
	}
	
	/**
	 * A getter for the imaginary value
	 * @return Returns the imaginary value
	 */
	public double getImaginary() {
		return this.im;
	}
	
	@Override
	public String toString() {
		
		if(im >= 0) {
			return String.valueOf(this.re) + "+i" + String.valueOf(this.im);
		}
		
		return String.valueOf(this.re) + "-i" + String.valueOf(Math.abs(this.im));
	}
	
	private double getAngle(Complex c) {
		double angle = Math.atan2(c.getImaginary(), c.getReal());
		
		if(angle < 0) {
			return angle + 2*Math.PI;
		}
		
		return angle;
	}
		

}
