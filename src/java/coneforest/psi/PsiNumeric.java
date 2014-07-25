package coneforest.psi;

abstract public class PsiNumeric
	extends PsiObject
	implements PsiAtomic, PsiArithmetic<PsiNumeric>, PsiScalar<PsiNumeric>
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
