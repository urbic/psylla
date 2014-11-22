package coneforest.psi;

/**
 *	A representation of Ψ <code class="type">real</code> object.
 */
public class PsiReal
	extends PsiNumeric
{
	public PsiReal(final double value)
	{
		this.value=value;
	}

	@Override
	public String getTypeName() { return "real"; }

	@Override
	public int intValue()
	{
		return (int)value;
	}

	@Override
	public long longValue()
	{
		return (long)value;
	}

	@Override
	public double doubleValue()
	{
		return value;
	}

	@Override
	public PsiReal psiRe()
	{
		return new PsiReal(value);
	}

	@Override
	public PsiReal psiIm()
	{
		return new PsiReal(0.D);
	}

	@Override
	public PsiNumeric psiConjugate()
	{
		return new PsiReal(value);
	}

	@Override
	public PsiReal psiNeg()
	{
		return new PsiReal(-value);
	}

	@Override
	public PsiReal psiAbs()
	{
		return new PsiReal(Math.abs(value));
	}

	@Override
	public PsiReal psiSignum()
	{
		return new PsiReal(Math.signum(value));
	}

	@Override
	public PsiComplexNumeric psiAdd(final PsiComplexNumeric cn)
	{
		if(cn instanceof PsiNumeric)
			return new PsiReal(value+((PsiNumeric)cn).doubleValue());
		return super.psiAdd(cn);
	}

	@Override
	public PsiComplexNumeric psiSub(final PsiComplexNumeric cn)
	{
		if(cn instanceof PsiNumeric)
			return new PsiReal(value-((PsiNumeric)cn).doubleValue());
		return super.psiSub(cn);
	}

	@Override
	public PsiComplexNumeric psiMul(final PsiComplexNumeric cn)
	{
		if(cn instanceof PsiNumeric)
			return new PsiReal(value*((PsiNumeric)cn).doubleValue());
		return super.psiMul(cn);
	}

	/*
	@Override
	public PsiReal psiPow(final PsiInteger integer)
	{
		return new PsiReal(Math.pow(value, integer.doubleValue()));
	}

	@Override
	public PsiReal psiPow(final PsiReal real)
	{
		return new PsiReal(Math.pow(value, real.doubleValue()));
	}
	*/

	@Override
	public PsiReal psiFloor()
	{
		return new PsiReal(Math.floor(value));
	}

	@Override
	public PsiReal psiCeiling()
	{
		return new PsiReal(Math.ceil(value));
	}

	@Override
	public String toString()
	{
		return String.valueOf(value);
	}
	
	@Override
	public int hashCode()
	{
		return (int)value;
	}

	@Override
	public boolean equals(Object object)
	{
		return object instanceof PsiReal
				&& psiEq((PsiReal)object).booleanValue();
	}

	private final double value;
}
