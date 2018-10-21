package com.bo.calculator;

import com.bo.data.DataProvider;
import com.bo.data.Reporter;
import com.bo.operation.DefaultOperatorFactory;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CalculatorTest
{
    private Calculator calculator;

    @Before
    public void setUp() throws Exception 
    {
        var operatorFactory = new DefaultOperatorFactory();
        this.calculator = new Calculator(operatorFactory);
    }

    @Test
    public void test1()
    {
        var inputs = new String[]{"5 2"};
        var outputs = new String[]{"5 2"};
        testValue(inputs, outputs);
    }

    @Test
    public void test2()
    {
        var inputs = new String[]{"2 sqrt", "clear    9 sqrt"};
        var outputs = new String[]{"1.4142135623", "3"};
        testValue(inputs, outputs);
    }

    @Test
    public void test3()
    {
        var inputs = new String[]{"5 2  -", "3 - ", "clear"};
        var outputs = new String[]{"3", "0", ""};
        testValue(inputs, outputs);
    }

    @Test
    public void test4()
    {
        var inputs = new String[]{"5 4 3 2", "undo undo *","5 *","undo "};
        var outputs = new String[]{"5 4 3 2","20", "100", "20 5"};
        testValue(inputs, outputs);
    }

    @Test
    public void test5()
    {
        var inputs = new String[]{"7 12 2 /", "*","4 /"};
        var outputs = new String[]{"7 6","42", "10.5"};
        testValue(inputs, outputs);
    }

    @Test
    public void test6()
    {
        var inputs = new String[]{"1 2 3 4 5", "*","clear 3 4 - "};
        var outputs = new String[]{"1 2 3 4 5", "1 2 3 20", "-1"};
        testValue(inputs, outputs);
    }

    @Test
    public void test7()
    {
        var inputs = new String[]{"1 2 3 4 5", "* * * *"};
        var outputs = new String[]{"1 2 3 4 5", "120"};
        testValue(inputs, outputs);
    }

    @Test
    public void test8()
    {
        String[] expected = new String[]
        {
            "operator * (position: 15): insucient parameters",
            getExpected("11")
        };
        test(new String[]{"1 2 3 * 5 + * * 6 5"}, expected);
    }

    private void testValue(String[] inputs, String[] values)
    {
        for (int i = 0; i < values.length; i++) 
        {
            values[i] = getExpected(values[i]);
        }
        test(inputs, values);
    }

    private void test(String[] inputs, String[] expected)
    {
        var in = new ByteArrayInputStream(String.join("\n", inputs).getBytes());
        var out = new ByteArrayOutputStream();
        var dataProvider = new DataProvider(in);
        var reporter = new Reporter(new PrintStream(out));
        
        this.calculator.calculate(dataProvider, reporter);

        var actual = out.toString().split("\n");

        assertArrayEquals(expected, actual);
    }

    private static String getExpected(String value)
    {
        return "stack: "+value;
    }
}