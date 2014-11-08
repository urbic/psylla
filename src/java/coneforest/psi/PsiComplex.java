package coneforest.psi;

/**
 *	A representation of Ψ complex object.
 */
public class PsiComplex
	extends PsiComplexNumeric
	implements PsiAtomic
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
		this(re, new PsiReal(0.D));
	}

	@Override
	public PsiReal psiAbs()
	{
		return new PsiReal(Math.hypot(re, im));
	}

	@Override
	public PsiComplex psiSignum()
	{
		return (re==0.D && im==0.D)? new PsiComplex(0.D, 0.D): psiDiv(psiAbs());
	}

	@Override
	public String getTypeName()
	{
		return "complex";
	}

	@Override
	public String toString()
	{
		return re+(im>=0.D? "+": "")+im+"i";
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
	{
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
		double cnRe=cn.psiRe().doubleValue();
		double cnIm=cn.psiIm().doubleValue();
		return new PsiComplex(re*cnRe-im*cnIm, im*cnRe+re*cnIm);
	}

	@Override
	public PsiComplex psiDiv(final PsiComplexNumeric cn)
	{
		double cnRe=cn.psiRe().doubleValue();
		double cnIm=cn.psiIm().doubleValue();
		double denom=cnRe*cnRe-cnIm*cnIm;
		// TODO overflow
		return new PsiComplex((re*cnRe+im*cnIm)/denom, (im*cnRe-re*cnIm)/denom);
	}

	@Override
	public PsiComplex psiExp()
	{
		double reExp=Math.exp(re);
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
	public PsiComplex psiSqrt()
	{
		return psiFromPolar((PsiReal)psiAbs().psiSqrt(), psiArg().psiDiv(new PsiReal(2.D)));
	}

	@Override
	public PsiComplex psiCbrt()
	{
		return psiFromPolar((PsiReal)psiAbs().psiCbrt(), psiArg().psiDiv(new PsiReal(3.D)));
	}

	@Override
	public PsiComplex psiCosh()
	{
		PsiComplex tmpExp=psiExp();
		return tmpExp.psiAdd(new PsiComplex(1.D).psiDiv(tmpExp)).psiDiv(new PsiReal(2.D));
	}

	@Override
	public PsiComplex psiTan()
	{
		return psiSin().psiDiv(psiCos());
	}

	@Override
	public PsiComplex psiSinh()
	{
		PsiComplex tmpExp=psiExp();
		return tmpExp.psiSub(new PsiComplex(1.D).psiDiv(tmpExp)).psiDiv(new PsiReal(2.D));
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


	private final double re, im;
}
