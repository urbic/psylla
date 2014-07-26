package coneforest.psi;

abstract public class PsiComplexNumeric
	extends PsiObject
	implements PsiAtomic, PsiArithmetic<PsiComplexNumeric>
{
	abstract public PsiNumeric re();

	abstract public PsiNumeric im();

	abstract public PsiNumeric arg();

	abstract public PsiComplexNumeric conjugate();

	abstract public PsiComplexNumeric neg();
	
	abstract public PsiComplexNumeric add(final PsiComplexNumeric numeric);
	
	abstract public PsiComplexNumeric sub(final PsiComplexNumeric numeric);

	abstract public PsiComplexNumeric mul(final PsiComplexNumeric numeric);

	abstract public PsiComplexNumeric div(final PsiComplexNumeric numeric);

	public PsiNumeric abs()
	{
		return new PsiReal(Math.hypot(re().getValue().doubleValue(), im().getValue().doubleValue()));
	}

	/*public PsiReal sqrt()
	{
		return new PsiReal(Math.sqrt(getValue().doubleValue()));
	}
	
	public PsiReal cbrt()
	{
		return new PsiReal(Math.cbrt(getValue().doubleValue()));
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

	public PsiReal tan()
	{
		return new PsiReal(Math.tan(getValue().doubleValue()));
	}

	public PsiReal cosh()
	{
		return new PsiReal(Math.cosh(getValue().doubleValue()));
	}

	public PsiReal sinh()
	{
		return new PsiReal(Math.sinh(getValue().doubleValue()));
	}

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
