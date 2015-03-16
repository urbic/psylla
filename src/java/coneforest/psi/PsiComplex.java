package coneforest.psi;

/**
 *	A representation of Ψ-<code class="type">complex</code> object.
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
		return (re==0.D && im==0.D)? new PsiComplex(0.D, 0.D): psiDiv(psiAbs());
	}

	/**
	 *	@return a string <code class="constant">"complex"</code>.
	 */
	@Override
	public String getTypeName()
	{
		return "complex";
	}

	@Override
	public String toString()
	{
		//return re+(im>=0.D? "+": "")+im+"i";
		//return "%C%"+re+":"+im;
		return re+" "+im+" complex";
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
		throws PsiException
	{
		if(im==0 && re==0)
			throw new PsiException("undefinedresult");
		double argValue=Math.atan2(im, re);
		if(argValue<0)
			argValue+=2.D*Math.PI;
		return new PsiReal(argValue);
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
		if(re==0 && im==0)
			return new PsiComplex(0, 0);
		//double theta=Math.atan2(im, re)/2;
		//double rho=Math.sqrt(Math.hypot(re, im));
		return psiFromPolar(Math.sqrt(Math.hypot(re, im)), Math.atan2(im, re)/2);
	}

	@Override
	public PsiComplex psiCbrt()
	{
		//return psiFromPolar((PsiReal)psiAbs().psiCbrt(), psiArg().psiDiv(new PsiReal(3.D)));
		if(re==0 && im==0)
			return new PsiComplex(0, 0);
		//double theta=Math.atan2(im, re)/2;
		//double rho=Math.sqrt(Math.hypot(re, im));
		return psiFromPolar(Math.cbrt(Math.hypot(re, im)), Math.atan2(im, re)/3);
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
