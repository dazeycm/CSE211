import org.junit.Rule;
import org.junit.Test;
import org.junit.Before;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class ComplexTest {
    Complex c;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        c = new Complex();
    }

    @Test
    public void testDefaultCtor() {
        assertEquals(0, c.getReal(), .001);
        assertEquals(0, c.getComplex(), .001);
    }

    @Test
    public void testComplexCtorWrap() {
        Complex c = new Complex(5, 5);
        assertEquals(5, c.getReal(), .001);
        assertEquals(5, c.getComplex(), .001);
    }

    @Test
    public void testAddZero() {
        c = c.add(new Complex(0, 0));
        assertEquals(0, c.getReal(), .001);
        assertEquals(0, c.getComplex(), .001);
    }

    @Test
    public void testAddOne() {
        c = c.add(new Complex(1, 1));
        assertEquals(1, c.getReal(), .001);
        assertEquals(1, c.getComplex(), .001);
    }

    @Test
    public void testAddBigNum() {
        c = c.add(new Complex(Integer.MAX_VALUE, Integer.MAX_VALUE));
        assertEquals(Integer.MAX_VALUE, c.getReal(), .001);
        assertEquals(Integer.MAX_VALUE, c.getComplex(), .001);
    }

    @Test
    public void testAddDiffNums() {
        c = c.add(new Complex(3, 5));
        assertEquals(3, c.getReal(), .001);
        assertEquals(5, c.getComplex(), .001);

        c = new Complex(3, 5);
        c = c.add(new Complex(3, 5));
        assertEquals(6, c.getReal(), .001);
        assertEquals(10, c.getComplex(), .001);
    }

    @Test
    public void testSubtractZero() {
        c = c.subtract(new Complex(0, 0));
        assertEquals(0, c.getReal(), .001);
        assertEquals(0, c.getComplex(), .001);
    }

    @Test
    public void testSubtractOne() {
        c = c.subtract(new Complex(1, 1));
        assertEquals(-1, c.getReal(), .001);
        assertEquals(-1, c.getComplex(), .001);
    }

    @Test
    public void testSubtractBigNum() {
        c = c.subtract(new Complex(Integer.MAX_VALUE, Integer.MAX_VALUE));
        assertEquals(-Integer.MAX_VALUE, c.getReal(), .001);
        assertEquals(-Integer.MAX_VALUE, c.getComplex(), .001);
    }

    @Test
    public void testSubtractDiffNums() {
        c = c.subtract(new Complex(3, 5));
        assertEquals(-3, c.getReal(), .001);
        assertEquals(-5, c.getComplex(), .001);

        c = new Complex(-3, -5);
        c = c.subtract(new Complex(3, 5));
        assertEquals(-6, c.getReal(), .001);
        assertEquals(-10, c.getComplex(), .001);
    }

    @Test
    public void testMultiplyZero() {
        c = new Complex(4, 5);
        c = c.multiply(new Complex(0, 0));
        assertEquals(0, c.getReal(), .001);
        assertEquals(0, c.getComplex(), .001);
    }

    @Test
    public void testMultiplyOne() {
        c = new Complex(2, 3);
        c = c.multiply(new Complex(1, 1));
        assertEquals(-1, c.getReal(), .001);
        assertEquals(5, c.getComplex(), .001);
    }

    @Test
    public void testMultiplyBigNums() {
        c = new Complex(2, 3);
        c = c.multiply(new Complex(1000000, 1000000));
        assertEquals(-1000000, c.getReal(), .001);
        assertEquals(5000000, c.getComplex(), .001);
    }

    @Test
    public void testMultiplyDiffNums() {
        c = new Complex(2, 3);
        c = c.multiply(new Complex(4, 5));
        assertEquals(-7, c.getReal(), .001);
        assertEquals(22, c.getComplex(), .001);
    }

    @Test
    public void testDivideByZeroThrowsIllegalArgumentException() {
        c = new Complex(4, 5);
        exception.expect(IllegalArgumentException.class);
        c = c.divide(new Complex(0, 0));
    }

    @Test
    public void testDivideOne() {
        c = new Complex(2, 3);
        c = c.divide(new Complex(1, 1));
        assertEquals(2.5, c.getReal(), .001);
        assertEquals(0.5, c.getComplex(), .001);
    }

    @Test
    public void testDivideBigNums() {
        c = new Complex(1000000, 1000000);
        c = c.divide(new Complex(2, 3));
        assertEquals(384615.384, c.getReal(), .001);
        assertEquals(-76923.076, c.getComplex(), .001);
    }

    @Test
    public void testDivideDiffNums() {
        c = new Complex(2, 3);
        c = c.divide(new Complex(2, -7));
        assertEquals(-0.320, c.getReal(), .001);
        assertEquals(0.377, c.getComplex(), .001);
    }

    @Test
    public void testZeroToString() {
        c = new Complex(0, 0);
        assertEquals("0.000+0.000i", c.toString());
    }

    @Test
    public void testPosToString() {
        c = new Complex(1, 1);
        assertEquals("1.000+1.000i", c.toString());
    }

    @Test
    public void testNegToString() {
        c = new Complex( -1, -1);
        assertEquals("-1.000-1.000i", c.toString());
    }
}