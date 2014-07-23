package coneforest.psi;

abstract public class PsiNumeric
	extends PsiObject
{
	public Number getValue()
	{
		if(this instanceof PsiInteger)
			return ((PsiInteger)this).getValue();
		else
			return ((PsiReal)this).getValue();
	}

	public static PsiNumeric negate(final PsiNumeric x)
	{
		if(x instanceof PsiInteger)
			return new PsiInteger(-((PsiInteger)x).getValue().longValue());
		else
			return new PsiReal(-((PsiReal)x).getValue().doubleValue());
	}

	public static PsiNumeric sum(final PsiNumeric x, final PsiNumeric y)
	{
		double result=x.getValue().doubleValue()+y.getValue().doubleValue();
		if(x instanceof PsiInteger && y instanceof PsiInteger
				&& result>=Long.MIN_VALUE && result<=Long.MAX_VALUE)
			return new PsiInteger(((Double)result).longValue());
		else
			return new PsiReal(result);
	}

	public static PsiNumeric difference(final PsiNumeric x, final PsiNumeric y)
	{
		double result=x.getValue().doubleValue()-y.getValue().doubleValue();
		if(x instanceof PsiInteger && y instanceof PsiInteger
				&& result>=Long.MIN_VALUE && result<=Long.MAX_VALUE)
			return new PsiInteger(((Double)result).longValue());
		else
			return new PsiReal(result);
	}

	public static PsiNumeric product(final PsiNumeric x, final PsiNumeric y)
	{
		double result=x.getValue().doubleValue()*y.getValue().doubleValue();
		if(x instanceof PsiInteger && y instanceof PsiInteger
				&& result>=Long.MIN_VALUE && result<=Long.MAX_VALUE)
			return new PsiInteger(((Double)result).longValue());
		else
			return new PsiReal(result);
	}

	public static PsiReal ratio(final PsiNumeric x, final PsiNumeric y)
	{
		return new PsiReal(x.getValue().doubleValue()/y.getValue().doubleValue());
	}

	public static PsiReal exp(final PsiNumeric x)
	{
		return new PsiReal(Math.exp(x.getValue().doubleValue()));
	}

	public static PsiReal sin(final PsiNumeric x)
	{
		return new PsiReal(Math.sin(x.getValue().doubleValue()));
	}

	public static PsiReal cos(final PsiNumeric x)
	{
		return new PsiReal(Math.cos(x.getValue().doubleValue()));
	}
	
	public static PsiReal log(final PsiNumeric x)
	{
		return new PsiReal(Math.log(x.getValue().doubleValue()));
	}
	
	public static PsiReal sqrt(final PsiNumeric x)
	{
		return new PsiReal(Math.sqrt(x.getValue().doubleValue()));
	}

	public static PsiNumeric abs(final PsiNumeric x)
	{
		if(x instanceof PsiInteger)
			return new PsiInteger((long)Math.abs(((PsiInteger)x).getValue()));
		else
			return new PsiReal(Math.abs(((PsiReal)x).getValue().doubleValue()));
	}

	public static PsiNumeric floor(final PsiNumeric x)
	{
		if(x instanceof PsiInteger)
			return new PsiInteger((long)Math.floor(((PsiInteger)x).getValue()));
		else
			return new PsiReal(Math.floor(((PsiReal)x).getValue().doubleValue()));
	}

	public static PsiNumeric ceiling(final PsiNumeric x)
	{
		if(x instanceof PsiInteger)
			return new PsiInteger((long)Math.floor(((PsiInteger)x).getValue()));
		else
			return new PsiReal(Math.ceil(((PsiReal)x).getValue().doubleValue()));
	}

	public static PsiBoolean eq(final PsiNumeric x, final PsiNumeric y)
	{
		return new PsiBoolean(x.getValue().doubleValue()==y.getValue().doubleValue());
	}

	public static PsiBoolean ne(final PsiNumeric x, final PsiNumeric y)
	{
		return new PsiBoolean(x.getValue().doubleValue()!=y.getValue().doubleValue());
	}

	public static PsiBoolean lt(final PsiNumeric x, final PsiNumeric y)
	{
		return new PsiBoolean(x.getValue().doubleValue()<y.getValue().doubleValue());
	}

	public static PsiBoolean le(final PsiNumeric x, final PsiNumeric y)
	{
		return new PsiBoolean(x.getValue().doubleValue()<=y.getValue().doubleValue());
	}

	public static PsiBoolean gt(final PsiNumeric x, final PsiNumeric y)
	{
		return new PsiBoolean(x.getValue().doubleValue()>y.getValue().doubleValue());
	}

	public static PsiBoolean ge(final PsiNumeric x, final PsiNumeric y)
	{
		return new PsiBoolean(x.getValue().doubleValue()>=y.getValue().doubleValue());
	}

}
