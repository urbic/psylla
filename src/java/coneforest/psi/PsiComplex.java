package coneforest.psi;

/**
 *	A representation of Ψ-{@code complex} object.
 */
public class PsiComplex
	implements
		PsiAtomic,
		PsiComplexNumeric
{
	public PsiComplex(final double reValue, final double imValue)
	{
		this.re=reValue;
		this.im=imValue;
	}

	public PsiComplex(final double reValue)
	{
		this(reValue, 0.D);
	}

	public PsiComplex(final PsiNumeric re, final PsiNumeric im)
	{
		this(re.doubleValue(), im.doubleValue());
	}

	public PsiComplex(final PsiNumeric re)
	{
		this(re, PsiReal.ZERO);
	}

	public PsiComplex(final PsiComplexNumeric cn)
	{
		this(cn.psiRe(), cn.psiIm());
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
	public PsiReal psiRe()
	{
		return new PsiReal(re);
	}

	@Override
	public PsiReal psiIm()
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
		return new PsiComplex(re+cn.psiRe().doubleValue(), im+cn.psiIm().doubleValue());
	}

	@Override
	public PsiComplex psiSub(final PsiComplexNumeric cn)
	{
		return new PsiComplex(re-cn.psiRe().doubleValue(), im-cn.psiIm().doubleValue());
	}

	@Override
	public PsiComplex psiMul(final PsiComplexNumeric cn)
	{
		final double cnRe=cn.psiRe().doubleValue();
		final double cnIm=cn.psiIm().doubleValue();
		return new PsiComplex(re*cnRe-im*cnIm, im*cnRe+re*cnIm);
	}

	@Override
	public PsiComplex psiDiv(final PsiComplexNumeric cn)
	{
		final double cnRe=cn.psiRe().doubleValue();
		final double cnIm=cn.psiIm().doubleValue();
		final double denom=cnRe*cnRe+cnIm*cnIm;
		// TODO overflow
		return new PsiComplex((re*cnRe+im*cnIm)/denom, (im*cnRe-re*cnIm)/denom);
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
	public PsiComplex psiAtan()
		throws PsiUndefinedResultException
	{
		final PsiComplex temp=psiMul(PsiComplex.I);
		return PsiComplex.ONE.psiAdd(temp)
				.psiDiv(PsiComplex.ONE.psiSub(temp)).psiLog()
				.psiDiv(PsiComplex.TWO).psiDiv(PsiComplex.I);
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
		return tmpExp.psiAdd(PsiComplex.ONE.psiDiv(tmpExp)).psiDiv(PsiComplex.TWO);
	}

	@Override
	public PsiComplex psiTan()
	{
		return psiSin().psiDiv(psiCos());
	}

	@Override
	public PsiComplex psiSinh()
	{
		final PsiComplex tmpExp=psiExp();
		return tmpExp.psiSub(PsiComplex.ONE.psiDiv(tmpExp)).psiDiv(PsiComplex.TWO);
	}

	@Override
	public PsiComplex psiTanh()
	{
		return psiSinh().psiDiv(psiCosh());
	}

	public static PsiComplex psiFromPolar(PsiNumeric abs, PsiNumeric arg)
	{
		return psiFromPolar(abs.doubleValue(), arg.doubleValue());
	}

	public static PsiComplex psiFromPolar(double absValue, double argValue)
	{
		return new PsiComplex(absValue*Math.cos(argValue), absValue*Math.sin(argValue));
	}

	public static final PsiComplex
		ZERO=new PsiComplex(0.D, 0.D),
		ONE=new PsiComplex(1.D, 0.D),
		TWO=new PsiComplex(2.D, 0.D),
		I=new PsiComplex(0.D, 1.D);

	private final double re, im;
}
