package com.bo.operation.factory;

import com.bo.operation.IOperator;
import com.bo.operation.numeric.PushNumberOperator;

class NumberOperatorCreater implements IOperatorCreater
{
    private String symbol;
    private IOperator operator;
    @Override
    public IOperator createOperator(String symbol) 
    {
        if (symbol != null && (symbol.equals(this.symbol) || this.accept(symbol)))
        {
            return this.operator;
        }
        return null;
    }

    @Override
    public boolean accept(String symbol) 
    {
        try 
        {
            double value = Double.parseDouble(symbol);
            this.operator = new PushNumberOperator(value);
            this.symbol = symbol;
            return true;
        } 
        catch (Exception e) 
        {
            return false;
        }
    }
    
}