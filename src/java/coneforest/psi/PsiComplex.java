package coneforest.psi;

public class PsiComplex
	extends PsiComplexNumeric
	implements PsiAtomic
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
		this(re.getValue().doubleValue(), im.getValue().doubleValue());
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
		return new PsiComplex(re+cn.psiRe().getValue().doubleValue(), im+cn.psiIm().getValue().doubleValue());
		//return new PsiComplex(re+cn.re, im+cn.im);
	}

	@Override
	public PsiComplex psiSub(final PsiComplexNumeric cn)
	{
		return new PsiComplex(re-cn.psiRe().getValue().doubleValue(), im-cn.psiIm().getValue().doubleValue());
	}

	@Override
	public PsiComplex psiMul(final PsiComplexNumeric cn)
	{
		double cnRe=cn.psiRe().getValue().doubleValue();
		double cnIm=cn.psiIm().getValue().doubleValue();
		return new PsiComplex(re*cnRe-im*cnIm, im*cnRe+re*cnIm);
	}

	@Override
	public PsiComplex psiDiv(final PsiComplexNumeric cn)
	{
		double cnRe=cn.psiRe().getValue().doubleValue();
		double cnIm=cn.psiIm().getValue().doubleValue();
		double denom=cnRe*cnRe-cnIm*cnIm;
		// TODO overflow
		return new PsiComplex((re*cnRe+im*cnIm)/denom, (im*cnRe-re*cnIm)/denom);
	}

	//@Override
	//public PsiComplex sqrt(final PsiComplexNumeric cn)
	//{
	//	return new PsiReal(Math.sqrt(getValue().doubleValue()));
	//}

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

	@Override
	public PsiComplex psiExp()
	{
		PsiReal tmpIm=psiIm();
		return psiRe().psiExp().psiMul(new PsiComplex(tmpIm.cos(), tmpIm.sin()));
	}

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

	@Override
	public PsiComplex psiCosh()
	{
		PsiComplex tmpExp=psiExp();
		return tmpExp.psiAdd(new PsiComplex(1.D).psiDiv(tmpExp)).psiDiv(new PsiReal(2.D));
	}

	@Override
	public PsiComplex psiSinh()
	{
		PsiComplex tmpExp=psiExp();
		return tmpExp.psiSub(new PsiComplex(1.D).psiDiv(tmpExp)).psiDiv(new PsiReal(2.D));
	}

	/*
	public PsiReal tanh()
	{
		return new PsiReal(Math.tanh(getValue().doubleValue()));
	}
	*/

	private final double re, im;
}
