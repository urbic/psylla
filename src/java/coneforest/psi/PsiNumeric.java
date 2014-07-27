package coneforest.psi;

abstract public class PsiNumeric
	//extends PsiObject
	extends PsiComplexNumeric
	//implements PsiAtomic, PsiArithmetic<PsiNumeric>, PsiScalar<PsiNumeric>
	implements PsiAtomic, PsiScalar<PsiNumeric>
{
	abstract Number getValue();

	@Override
	public PsiReal arg()
	{
		if(getValue().doubleValue()>0.D)
			return new PsiReal(0.D);
		else if(getValue().doubleValue()<0.D)
			return new PsiReal(Math.PI);
		else
			return new PsiReal(1.D/0.D);
	}

	@Override
	abstract public PsiNumeric neg();
	
	///*
	@Override
	public PsiComplexNumeric add(final PsiComplexNumeric cn)
	{
		return new PsiComplex(this).add(cn);
	}
	//*/
	
	abstract public PsiNumeric sub(final PsiNumeric numeric);

	@Override
	public PsiComplexNumeric sub(final PsiComplexNumeric cn)
	{
		return new PsiComplex(this).sub(cn);
	}

	abstract public PsiNumeric mul(final PsiNumeric numeric);

	@Override
	public PsiComplexNumeric mul(final PsiComplexNumeric cn)
	{
		return new PsiComplex(this).mul(cn);
	}

	public PsiReal div(final PsiNumeric numeric)
	{
		return new PsiReal(getValue().doubleValue()/numeric.getValue().doubleValue());
	}

	@Override
	public PsiComplexNumeric div(final PsiComplexNumeric cn)
	{
		if(cn instanceof PsiNumeric)
			return div((PsiNumeric)cn);
		return new PsiComplex(this).div(cn);
	}

	abstract public PsiNumeric pow(final PsiNumeric numeric);

	public abstract PsiNumeric abs();

	//@Override
	public PsiReal sqrt()
	{
		return new PsiReal(Math.sqrt(getValue().doubleValue()));
	}
	
	//@Override
	public PsiReal cbrt()
	{
		return new PsiReal(Math.cbrt(getValue().doubleValue()));
	}

	//@Override
	public PsiReal log()
	{
		return new PsiReal(Math.log(getValue().doubleValue()));
	}

	@Override
	public PsiReal exp()
	{
		return new PsiReal(Math.exp(getValue().doubleValue()));
	}

	//@Override
	public PsiReal cos()
	{
		return new PsiReal(Math.cos(getValue().doubleValue()));
	}

	//@Override
	public PsiReal sin()
	{
		return new PsiReal(Math.sin(getValue().doubleValue()));
	}

	//@Override
	public PsiReal tan()
	{
		return new PsiReal(Math.tan(getValue().doubleValue()));
	}

	@Override
	public PsiReal cosh()
	{
		return new PsiReal(Math.cosh(getValue().doubleValue()));
	}

	@Override
	public PsiReal sinh()
	{
		return new PsiReal(Math.sinh(getValue().doubleValue()));
	}

	public PsiReal tanh()
	{
		return new PsiReal(Math.tanh(getValue().doubleValue()));
	}

	public PsiReal atan()
	{
		return new PsiReal(Math.atan(getValue().doubleValue()));
	}

	public PsiReal hypot(PsiNumeric numeric)
	{
		return new PsiReal(Math.hypot(getValue().doubleValue(), numeric.getValue().doubleValue()));
	}

	//@Override
	abstract public PsiNumeric floor();
	
	//@Override
	abstract public PsiNumeric ceiling();

	@Override
	public PsiBoolean eq(final PsiObject obj)
	{
		return new PsiBoolean(obj instanceof PsiNumeric
				&& getValue().doubleValue()==((PsiNumeric)obj).getValue().doubleValue());
	}
	
	@Override
	public PsiBoolean lt(final PsiNumeric numeric)
	{
		return new PsiBoolean(getValue().doubleValue()<numeric.getValue().doubleValue());
	}

	@Override
	public PsiBoolean le(final PsiNumeric numeric)
	{
		return new PsiBoolean(getValue().doubleValue()<=numeric.getValue().doubleValue());
	}

	@Override
	public PsiBoolean gt(final PsiNumeric numeric)
	{
		return new PsiBoolean(getValue().doubleValue()>numeric.getValue().doubleValue());
	}

	@Override
	public PsiBoolean ge(final PsiNumeric numeric)
	{
		return new PsiBoolean(getValue().doubleValue()>=numeric.getValue().doubleValue());
	}

}
