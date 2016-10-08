package coneforest.psi.core;

/**
*	A representation of Ψ-{@code complex} object.
*/
public class PsiComplex
	implements
		PsiNumeric
{
	public PsiComplex(final double re, final double im)
	{
		this.re=re;
		this.im=im;
	}

	public PsiComplex(final double re)
	{
		this(re, 0.D);
	}

	public PsiComplex(final PsiRealNumeric oRealPart, final PsiRealNumeric oImagPart)
	{
		this(oRealPart.doubleValue(), oImagPart.doubleValue());
	}

	public PsiComplex(final PsiRealNumeric oNumeric)
	{
		this(oNumeric.doubleValue());
	}

	/**
	*	@return a string {@code "complex"}.
	*/
	@Override
	public String typeName()
	{
		return "complex";
	}

	@Override
	public double realValue()
	{
		return re;
	}

	@Override
	public double imagValue()
	{
		return im;
	}

	@Override
	public PsiBoolean psiIsZero()
	{
		return PsiBoolean.valueOf(re==0.D && im==0.D);
	}

	@Override
	public PsiReal psiAbs()
	{
		return new PsiReal(Math.hypot(re, im));
	}

	@Override
	public String toSyntaxString()
	{
		return "|complex="+re+","+im+"|";
	}

	/**
	 *	Returns a Ψ-{@code real} real part of this object.
	 *
	 *	@return a Ψ-{@code real} real part.
	 */
	public PsiReal psiRealPart()
	{
		return new PsiReal(re);
	}

	/**
	 *	Returns a Ψ-{@code real} imaginary part of this object.
	 *
	 *	@return a Ψ-{@code real} imaginary part.
	 */

	public PsiReal psiImagPart()
	{
		return new PsiReal(im);
	}

	/**
	 *	Returns a Ψ-{@code real} representing the complex argument of this
	 *	object. The argument belongs to the range (−π; π].
	 *
	 *	@return a Ψ-{@code real} argument.
	 *	@throws PsiUndefinedResultException when this object represents a zero
	 *	number.
	 */
	public PsiReal psiArg()
		throws PsiUndefinedResultException
	{
		if(re==0.D && im==0.D)
			throw new PsiUndefinedResultException();
		return new PsiReal(Math.atan2(im, re));
	}

	/**
	 *	Returns a Ψ-{@code complex} representing the complex conjugate of this
	 *	object.
	 *
	 *	@return a Ψ-{@code complex} conjugate of this number.
	 */
	public PsiComplex psiConjugate()
	{
		return new PsiComplex(re, -im);
	}

	@Override
	public PsiComplex psiNeg()
	{
		return new PsiComplex(-re, -im);
	}

	@Override
	public PsiComplex psiAdd(final PsiNumeric oNumeric)
	{
		return new PsiComplex(re+oNumeric.realValue(), im+oNumeric.imagValue());
	}

	@Override
	public PsiComplex psiSub(final PsiNumeric oNumeric)
	{
		return new PsiComplex(re-oNumeric.realValue(), im-oNumeric.imagValue());
	}

	@Override
	public PsiComplex psiMul(final PsiNumeric oNumeric)
	{
		final double x=oNumeric.realValue();
		final double y=oNumeric.imagValue();
		return new PsiComplex(re*x-im*y, im*x+re*y);
	}

	@Override
	public PsiComplex psiDiv(final PsiNumeric oNumeric)
	{
		final double x=oNumeric.realValue();
		final double y=oNumeric.imagValue();
		if(Math.abs(x)<Math.abs(y))
		{
			final double q=x/y;
			final double d=x*q+y;
			return new PsiComplex((re*q+im)/d, (im*q-re)/d);
		}
		else
		{
			final double q=y/x;
			final double d=y*q+x;
			return new PsiComplex((im*q+re)/d, (im-re*q)/d);
		}
	}

	@Override
	public PsiComplex psiExp()
	{
		final double reExp=Math.exp(re);
		return new PsiComplex(reExp*Math.cos(im), reExp*Math.sin(im));
	}

	@Override
	public PsiComplex psiCos()
	{
		return new PsiComplex(Math.cos(re)*Math.cosh(im), Math.sin(re)*Math.sinh(im));
	}

	@Override
	public PsiComplex psiSin()
	{
		return new PsiComplex(Math.sin(re)*Math.cosh(im), -Math.cos(re)*Math.sinh(im));
	}

	@Override
	public PsiComplex psiLog()
		throws PsiException
	{
		return new PsiComplex((PsiReal)psiAbs().psiLog(), psiArg());
	}

	@Override
	public PsiComplex psiAcos()
		throws PsiException
	{
		return psiAdd(ONE.psiSub(psiMul(this)).psiSqrt().psiMul(I)).psiLog().psiMul(MINUS_I);
	}

	@Override
	public PsiComplex psiAsin()
		throws PsiException
	{
		return new PsiComplex(Math.PI/2.D).psiSub(psiAcos());
	}

	@Override
	public PsiComplex psiAtan()
		throws PsiException
	{
		return (I.psiAdd(this).psiDiv(I.psiSub(this))).psiLog().psiMul(I).psiDiv(TWO);
	}

	@Override
	public PsiComplex psiSqrt()
	{
		return psiFromPolar(Math.sqrt(Math.hypot(re, im)), Math.atan2(im, re)/2.D);
	}

	@Override
	public PsiComplex psiCbrt()
	{
		return psiFromPolar(Math.cbrt(Math.hypot(re, im)), Math.atan2(im, re)/3.D);
	}

	@Override
	public PsiComplex psiCosh()
	{
		final PsiComplex tmpExp=psiExp();
		return tmpExp.psiAdd(ONE.psiDiv(tmpExp)).psiDiv(TWO);
	}

	@Override
	public PsiComplex psiTan()
	{
		return psiSin().psiDiv(psiCos());
	}

	@Override
	public PsiComplex psiSinh()
	{
		final PsiComplex oExp=psiExp();
		return oExp.psiSub(ONE.psiDiv(oExp)).psiDiv(TWO);
	}

	@Override
	public PsiComplex psiTanh()
	{
		return psiSinh().psiDiv(psiCosh());
	}

	public static PsiComplex psiFromPolar(final PsiRealNumeric oAbs, final PsiRealNumeric oArg)
	{
		return psiFromPolar(oAbs.doubleValue(), oArg.doubleValue());
	}

	public static PsiComplex psiFromPolar(final double abs, final double arg)
	{
		return new PsiComplex(abs*Math.cos(arg), abs*Math.sin(arg));
	}

	public static final PsiComplex
		ZERO=new PsiComplex(0.D, 0.D),
		ONE=new PsiComplex(1.D, 0.D),
		MINUS_ONE=new PsiComplex(-1.D, 0.D),
		TWO=new PsiComplex(2.D, 0.D),
		I=new PsiComplex(0.D, 1.D),
		MINUS_I=new PsiComplex(0.D, -1.D);

	private final double re, im;
}
