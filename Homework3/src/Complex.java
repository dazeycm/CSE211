/**
 * Created by Craig on 2/10/2015.
 */
public class Complex {

    private double real, complex;

    public Complex()    {
        this.real = 0;
        this.complex = 0;
    }

    public Complex(double real, double complex)    {
        this.real = real;
        this.complex = complex;
    }

    public Complex add(Complex other)   {
        Complex c = new Complex();
        c.setReal(this.real + other.real);
        c.setComplex(this.complex + other.complex);
        return c;
    }

    public Complex subtract(Complex other)   {
        Complex c = new Complex();
        c.setReal(this.real - other.real);
        c.setComplex(this.complex - other.complex);
        return c;
    }

    public Complex multiply(Complex other)   {
        Complex c = new Complex();
        return c;
    }

    public Complex divide(Complex other)   {
        Complex c = new Complex();
        return c;
    }

    public double getReal() {
        return real;
    }

    public void setReal(double real) {
        this.real = real;
    }

    public double getComplex() {
        return complex;
    }

    public void setComplex(double complex) {
        this.complex = complex;
    }

    @Override
    public String toString()    {
        if(complex >= 0) {
            return real + "+" + complex + "i";
        } else  {
            return real + "" + complex + "i";
        }
    }
}
