package coneforest.psi;

abstract public class PsiComplexNumeric
	extends PsiObject
	implements PsiAtomic, PsiArithmetic<PsiComplexNumeric>
{
	abstract public PsiNumeric re();

	abstract public PsiNumeric im();

	abstract public PsiNumeric arg();

	abstract public PsiComplexNumeric conjugate();

	//@Override
	abstract public PsiComplexNumeric neg();
	
	@Override
	abstract public PsiComplexNumeric add(final PsiComplexNumeric cn);
	
	@Override
	abstract public PsiComplexNumeric sub(final PsiComplexNumeric numeric);

	@Override
	abstract public PsiComplexNumeric mul(final PsiComplexNumeric numeric);

	@Override
	abstract public PsiComplexNumeric div(final PsiComplexNumeric numeric);

	@Override
	abstract public PsiNumeric abs();

	@Override
	abstract public PsiComplexNumeric signum();

	//abstract public PsiComplexNumeric sqrt();
	
	/*
	public PsiReal cbrt()
	{
		return new PsiReal(Math.cbrt(getValue().doubleValue()));
	}
	
	public PsiReal log()
	{
		return new PsiReal(Math.log(getValue().doubleValue()));
	}
	*/
	
	abstract public PsiComplexNumeric exp();

	/*
	public PsiReal cos()
	{
		return new PsiReal(Math.cos(getValue().doubleValue()));
	}

	public PsiReal sin()
	{
		return new PsiReal(Math.sin(getValue().doubleValue()));
	}

	public PsiReal tan()
	{
		return new PsiReal(Math.tan(getValue().doubleValue()));
	}
	*/

	abstract public PsiComplexNumeric cosh();
	
	abstract public PsiComplexNumeric sinh();

	/*
	public PsiReal tanh()
	{
		return new PsiReal(Math.tanh(getValue().doubleValue()));
	}
	*/

	//abstract public PsiNumeric floor();
	
	//abstract public PsiNumeric ceiling();

	public PsiBoolean eq(final PsiObject obj)
	{
		return new PsiBoolean(obj instanceof PsiComplexNumeric
				&& re().eq(((PsiComplexNumeric)obj).re()).getValue()
				&& im().eq(((PsiComplexNumeric)obj).im()).getValue());
	}
}
