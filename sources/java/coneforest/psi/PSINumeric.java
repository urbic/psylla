package coneforest.psi;

abstract public class PSINumeric extends PSIObject
{
	abstract public byte getType();

	public Number getValue()
	{
		if(this instanceof PSIInteger)
			return ((PSIInteger)this).getValue();
		else
			return ((PSIReal)this).getValue();
	}

	public static PSINumeric negate(final PSINumeric x)
	{
		if(x instanceof PSIInteger)
			return new PSIInteger(-((PSIInteger)x).getValue().longValue());
		else
			return new PSIReal(-((PSIReal)x).getValue().doubleValue());
	}

	public static PSINumeric sum(final PSINumeric x, final PSINumeric y)
	{
		double result=x.getValue().doubleValue()+y.getValue().doubleValue();
		if(x instanceof PSIInteger && y instanceof PSIInteger
				&& result>=Long.MIN_VALUE && result<=Long.MAX_VALUE)
			return new PSIInteger(((Double)result).longValue());
		else
			return new PSIReal(result);
	}

	public static PSINumeric difference(final PSINumeric x, final PSINumeric y)
	{
		double result=x.getValue().doubleValue()-y.getValue().doubleValue();
		if(x instanceof PSIInteger && y instanceof PSIInteger
				&& result>=Long.MIN_VALUE && result<=Long.MAX_VALUE)
			return new PSIInteger(((Double)result).longValue());
		else
			return new PSIReal(result);
	}

	public static PSINumeric product(final PSINumeric x, final PSINumeric y)
	{
		double result=x.getValue().doubleValue()*y.getValue().doubleValue();
		if(x instanceof PSIInteger && y instanceof PSIInteger
				&& result>=Long.MIN_VALUE && result<=Long.MAX_VALUE)
			return new PSIInteger(((Double)result).longValue());
		else
			return new PSIReal(result);
	}

	public static PSIReal ratio(final PSINumeric x, final PSINumeric y)
	{
		return new PSIReal(x.getValue().doubleValue()/y.getValue().doubleValue());
	}

	public static PSIReal exp(final PSINumeric x)
	{
		return new PSIReal(Math.exp(x.getValue().doubleValue()));
	}

	public static PSIReal sin(final PSINumeric x)
	{
		return new PSIReal(Math.sin(x.getValue().doubleValue()));
	}

	public static PSIReal cos(final PSINumeric x)
	{
		return new PSIReal(Math.cos(x.getValue().doubleValue()));
	}
	
	public static PSIReal log(final PSINumeric x)
	{
		double xValue=x.getValue().doubleValue();
		return xValue>0.? new PSIReal(Math.log(xValue)): null;
	}

	public static PSINumeric abs(final PSINumeric x)
	{
		if(x instanceof PSIInteger)
			return new PSIInteger((long)Math.abs(((PSIInteger)x).getValue()));
		else
			return new PSIReal(Math.abs(((PSIReal)x).getValue().doubleValue()));
	}

	public static PSINumeric floor(final PSINumeric x)
	{
		if(x instanceof PSIInteger)
			return new PSIInteger((long)Math.floor(((PSIInteger)x).getValue()));
		else
			return new PSIReal(Math.floor(((PSIReal)x).getValue().doubleValue()));
	}

	public static PSINumeric ceiling(final PSINumeric x)
	{
		if(x instanceof PSIInteger)
			return new PSIInteger((long)Math.floor(((PSIInteger)x).getValue()));
		else
			return new PSIReal(Math.ceil(((PSIReal)x).getValue().doubleValue()));
	}

	public static PSIBoolean eq(final PSINumeric x, final PSINumeric y)
	{
		return new PSIBoolean(x.getValue().doubleValue()==y.getValue().doubleValue());
	}

	public static PSIBoolean ne(final PSINumeric x, final PSINumeric y)
	{
		return new PSIBoolean(x.getValue().doubleValue()!=y.getValue().doubleValue());
	}

	public static PSIBoolean lt(final PSINumeric x, final PSINumeric y)
	{
		return new PSIBoolean(x.getValue().doubleValue()<y.getValue().doubleValue());
	}

	public static PSIBoolean le(final PSINumeric x, final PSINumeric y)
	{
		return new PSIBoolean(x.getValue().doubleValue()<=y.getValue().doubleValue());
	}

	public static PSIBoolean gt(final PSINumeric x, final PSINumeric y)
	{
		return new PSIBoolean(x.getValue().doubleValue()>y.getValue().doubleValue());
	}

	public static PSIBoolean ge(final PSINumeric x, final PSINumeric y)
	{
		return new PSIBoolean(x.getValue().doubleValue()>=y.getValue().doubleValue());
	}

}
