package coneforest.psi.core;

/**
 *	A representation of Ψ-{@code complex} object.
 */
public class PsiComplex
	implements
		PsiComplexNumeric
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

	public PsiComplex(final PsiNumeric re, final PsiNumeric im)
	{
		this(re.doubleValue(), im.doubleValue());
	}

	public PsiComplex(final PsiNumeric oNumeric)
	{
		this(oNumeric.doubleValue());
	}

	public PsiComplex(final PsiComplexNumeric oNumber)
	{
		this(oNumber.psiRealPart(), oNumber.psiImagPart());
	}

	/**
	 *	@return a string {@code "complex"}.
	 */
	@Override
	public String getTypeName()
	{
		return "complex";
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
	public PsiComplex psiSignum()
	{
		return (re==0.D && im==0.D)? ZERO: psiDiv(psiAbs());
	}

	@Override
	public String toSyntaxString()
	{
		return "-complex:"+re+","+im+"-";
	}

	@Override
	public PsiReal psiRealPart()
	{
		return new PsiReal(re);
	}

	@Override
	public PsiReal psiImagPart()
	{
		return new PsiReal(im);
	}

	@Override
	public PsiReal psiArg()
		throws PsiUndefinedResultException
	{
		if(re==0.D && im==0.D)
			throw new PsiUndefinedResultException();
		return new PsiReal(Math.atan2(im, re));
	}

	@Override
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
	public PsiComplex psiAdd(final PsiComplexNumeric cn)
	{
		return new PsiComplex(re+cn.psiRealPart().doubleValue(), im+cn.psiImagPart().doubleValue());
	}

	@Override
	public PsiComplex psiSub(final PsiComplexNumeric cn)
	{
		return new PsiComplex(re-cn.psiRealPart().doubleValue(), im-cn.psiImagPart().doubleValue());
	}

	@Override
	public PsiComplex psiMul(final PsiComplexNumeric cn)
	{
		final double x=cn.psiRealPart().doubleValue();
		final double y=cn.psiImagPart().doubleValue();
		return new PsiComplex(re*x-im*y, im*x+re*y);
	}

	@Override
	public PsiComplex psiDiv(final PsiComplexNumeric cn)
	{
		final double x=cn.psiRealPart().doubleValue();
		final double y=cn.psiImagPart().doubleValue();
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
		throws PsiUndefinedResultException
	{
		return new PsiComplex((PsiReal)psiAbs().psiLog(), psiArg());
	}

	@Override
	public PsiComplex psiAcos()
		throws PsiUndefinedResultException
	{
		return psiAdd(ONE.psiSub(psiMul(this)).psiSqrt().psiMul(I)).psiLog().psiMul(MINUS_I);
	}

	@Override
	public PsiComplex psiAsin()
		throws PsiUndefinedResultException
	{
		return new PsiComplex(Math.PI/2.D).psiSub(psiAcos());
	}

	@Override
	public PsiComplex psiAtan()
		throws PsiUndefinedResultException
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

	public static PsiComplex psiFromPolar(final PsiNumeric oAbs, final PsiNumeric oArg)
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
