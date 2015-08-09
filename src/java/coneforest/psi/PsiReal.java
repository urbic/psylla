package coneforest.psi;

/**
 *	A representation of Ψ-<code class="type">real</code> object.
 */
public class PsiReal
	implements PsiNumeric
{
	public PsiReal(final double value)
	{
		this.value=value;
	}

	/**
	 *	@return a string <code class="constant">"real"</code>.
	 */
	@Override
	public String getTypeName()
	{
		return "real";
	}

	@Override
	public PsiBoolean psiIsZero()
	{
		return PsiBoolean.valueOf(value==0.D);
	}

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
	public PsiReal psiIm()
	{
		return new PsiReal(0.D);
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
	public PsiComplexNumeric psiAdd(final PsiNumeric numeric)
	{
		return new PsiReal(value+numeric.doubleValue());
	}

	@Override
	public PsiComplexNumeric psiSub(final PsiNumeric numeric)
	{
		return new PsiReal(value-numeric.doubleValue());
	}

	@Override
	public PsiComplexNumeric psiMul(final PsiNumeric numeric)
	{
		return new PsiReal(value*numeric.doubleValue());
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
	public PsiInteger psiRound()
	{
		return PsiInteger.valueOf(Math.round(value));
	}

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
	public PsiInteger psiCmp(final PsiNumeric numeric)
	{
		final int result=Double.compare(value, numeric.doubleValue());
		return result<0? PsiInteger.MINUS_ONE:
			result>0? PsiInteger.ONE: PsiInteger.ZERO;
	}

	@Override
	public String toSyntaxString()
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

	public static final PsiReal
		ZERO=new PsiReal(0.D),
		ONE=new PsiReal(1.D),
		TWO=new PsiReal(2.D),
		PI=new PsiReal(Math.PI),
		E=new PsiReal(Math.E),
		MAX_VALUE=new PsiReal(Double.MAX_VALUE),
		MIN_VALUE=new PsiReal(Double.MIN_VALUE),
		NAN=new PsiReal(Double.NaN);

	private final double value;
}
