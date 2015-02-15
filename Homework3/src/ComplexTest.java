import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class ComplexTest {
    Complex c;

    @Before
    public void setUp() throws Exception {
        c = new Complex();
    }

    @Test
    public void testDefaultCtor() throws Exception  {
        assertEquals(0, c.getReal(), .001);
        assertEquals(0, c.getComplex(), .001);
    }

    @Test
    public void testComplexCtorWrap() throws Exception  {
        Complex c = new Complex(5, 5);
        assertEquals(5, c.getReal(), .001);
        assertEquals(5, c.getComplex(), .001);
    }

    @Test
    public void testAddZero() throws Exception {
        c.add(new Complex(0, 0));
        assertEquals(0, c.getReal(), .001);
        assertEquals(0, c.getComplex(), .001);
    }

    @Test
    public void testAddOne() throws Exception {
        c = c.add(new Complex(1, 1));
        assertEquals(1, c.getReal(), .001);
        assertEquals(1, c.getComplex(), .001);
    }

    @Test
    public void testAddBigNum() throws Exception    {
        c = c.add(new Complex(Integer.MAX_VALUE, Integer.MAX_VALUE));
        assertEquals(Integer.MAX_VALUE, c.getReal(), .001);
        assertEquals(Integer.MAX_VALUE, c.getComplex(), .001);
    }

    @Test
    public void testAddDiffNums() throws Exception  {
        c = c.add(new Complex(3, 5));
        assertEquals(3, c.getReal(), .001);
        assertEquals(5, c.getComplex(), .001);

        c = new Complex(3, 5);
        c = c.add(new Complex(3, 5));
        assertEquals(6, c.getReal(), .001);
        assertEquals(10, c.getComplex(), .001);
    }

    @Test
    public void testSubtractZero() throws Exception {
        c = c.subtract(new Complex(0, 0));
        assertEquals(0, c.getReal(), .001);
        assertEquals(0, c.getComplex(), .001);
    }

    @Test
    public void testSubtractOne() throws Exception {
        c = c.subtract(new Complex(1, 1));
        assertEquals(-1, c.getReal(), .001);
        assertEquals(-1, c.getComplex(), .001);
    }

    @Test
    public void testSubtractBigNum() throws Exception {
        c = c.subtract(new Complex(Integer.MAX_VALUE, Integer.MAX_VALUE));
        assertEquals(-Integer.MAX_VALUE, c.getReal(), .001);
        assertEquals(-Integer.MAX_VALUE, c.getComplex(), .001);
    }

    @Test
    public void testSubtractDiffNums() throws Exception  {
        c = c.subtract(new Complex(3, 5));
        assertEquals(-3, c.getReal(), .001);
        assertEquals(-5, c.getComplex(), .001);

        c = new Complex(-3, -5);
        c = c.subtract(new Complex(3, 5));
        assertEquals(-6, c.getReal(), .001);
        assertEquals(-10, c.getComplex(), .001);
    }

    @Test
    public void testMultiply() throws Exception {

    }

    @Test
    public void testDivide() throws Exception {
    }

    @Test
    public void testZeroToString() throws Exception {
        c = new Complex(0, 0);
        assertEquals("0.0+0.0i", c.toString());
    }

    @Test
    public void testPosToString() throws Exception {
        c = new Complex(1, 1);
        assertEquals("1.0+1.0i", c.toString());
    }

    @Test
    public void testNegToString() throws Exception  {
        c = new Complex( -1, -1);
        assertEquals("-1.0-1.0i", c.toString());
    }
}