package coneforest.psi;

abstract public class PsiNumeric
	extends PsiObject
	implements PsiArithmetic<PsiNumeric>, PsiScalar<PsiNumeric>
{
	abstract Number getValue();

	abstract public PsiNumeric neg();
	
	abstract public PsiNumeric add(final PsiNumeric numeric);
	
	abstract public PsiNumeric sub(final PsiNumeric numeric);

	abstract public PsiNumeric mul(final PsiNumeric numeric);

	public PsiReal div(final PsiNumeric numeric)
	{
		return new PsiReal(getValue().doubleValue()/numeric.getValue().doubleValue());
	}


	/*
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
	*/

	public abstract PsiNumeric abs();

	public PsiReal sqrt()
	{
		return new PsiReal(Math.sqrt(getValue().doubleValue()));
	}
	
	public PsiReal log()
	{
		return new PsiReal(Math.log(getValue().doubleValue()));
	}
	
	public PsiReal exp()
	{
		return new PsiReal(Math.exp(getValue().doubleValue()));
	}

	public PsiReal cos()
	{
		return new PsiReal(Math.cos(getValue().doubleValue()));
	}

	public PsiReal sin()
	{
		return new PsiReal(Math.sin(getValue().doubleValue()));
	}

	abstract public PsiNumeric floor();
	
	abstract public PsiNumeric ceiling();

	public PsiBoolean eq(final PsiObject obj)
	{
		if(obj instanceof PsiNumeric)
			return new PsiBoolean(getValue().doubleValue()==((PsiNumeric)obj).getValue().doubleValue());
		else
			return new PsiBoolean(false);
	}
	
	public PsiBoolean lt(final PsiNumeric numeric)
	{
		return new PsiBoolean(getValue().doubleValue()<numeric.getValue().doubleValue());
	}

	public PsiBoolean le(final PsiNumeric numeric)
	{
		return new PsiBoolean(getValue().doubleValue()<=numeric.getValue().doubleValue());
	}

	public PsiBoolean gt(final PsiNumeric numeric)
	{
		return new PsiBoolean(getValue().doubleValue()>numeric.getValue().doubleValue());
	}

	public PsiBoolean ge(final PsiNumeric numeric)
	{
		return new PsiBoolean(getValue().doubleValue()>=numeric.getValue().doubleValue());
	}

}
