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
	public PsiReal abs()
	{
		return new PsiReal(Math.hypot(re, im));
	}
	
	@Override
	public PsiComplex signum()
	{
		return (re==0.D && im==0.D)? new PsiComplex(0.D, 0.D): div(abs());
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
	public PsiReal re()
	{
		return new PsiReal(re);
	}

	@Override
	public PsiReal im()
	{
		return new PsiReal(im);
	}

	@Override
	public PsiReal arg()
	{
		return new PsiReal(Math.atan2(im, re));
	}

	@Override
	public PsiComplex conjugate()
	{
		return new PsiComplex(re, -im);
	}

	@Override
	public PsiComplex neg()
	{
		return new PsiComplex(-re, -im);
	}
	
	@Override
	public PsiComplex add(final PsiComplexNumeric cn)
	{
		return new PsiComplex(re+cn.re().getValue().doubleValue(), im+cn.im().getValue().doubleValue());
	}
	
	@Override
	public PsiComplex sub(final PsiComplexNumeric cn)
	{
		return new PsiComplex(re-cn.re().getValue().doubleValue(), im-cn.im().getValue().doubleValue());
	}

	@Override
	public PsiComplex mul(final PsiComplexNumeric cn)
	{
		double cnRe=cn.re().getValue().doubleValue();
		double cnIm=cn.im().getValue().doubleValue();
		return new PsiComplex(re*cnRe-im*cnIm, im*cnRe+re*cnIm);
	}

	@Override
	public PsiComplex div(final PsiComplexNumeric cn)
	{
		double cnRe=cn.re().getValue().doubleValue();
		double cnIm=cn.im().getValue().doubleValue();
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
	public PsiComplex exp()
	{
		PsiReal tmp=im();
		return re().exp().mul(new PsiComplex(tmp.cos(), tmp.sin()));
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
	public PsiComplex cosh()
	{
		PsiComplex tmp=exp();
		return tmp.add(new PsiComplex(1.D).div(tmp)).div(new PsiReal(2.D));
	}

	@Override
	public PsiComplex sinh()
	{
		PsiComplex tmp=exp();
		return tmp.sub(new PsiComplex(1.D).div(tmp)).div(new PsiReal(2.D));
	}

	/*
	public PsiReal tanh()
	{
		return new PsiReal(Math.tanh(getValue().doubleValue()));
	}
	*/

	private final double re, im;
}
