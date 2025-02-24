package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of {@code complex}, a complex number.
*/
@Type("complex")
public final class PsyComplex
	implements PsyNumeric
{
	/**
	*	Context action of the {@code arg} operator.
	*/
	@OperatorType("arg")
	public static final ContextAction PSY_ARG
		=ContextAction.<PsyComplex>ofFunction(PsyComplex::psyArg);

	/**
	*	Context action of the {@code complex} operator.
	*/
	@OperatorType("complex")
	public static final ContextAction PSY_COMPLEX
		=ContextAction.<PsyRealNumeric, PsyRealNumeric>ofBiFunction(PsyComplex::new);

	/**
	*	Context action of the {@code complexpolar} operator.
	*/
	@OperatorType("complexpolar")
	public static final ContextAction PSY_COMPLEXPOLAR
		=ContextAction.<PsyRealNumeric, PsyRealNumeric>ofBiFunction(PsyComplex::psyFromPolar);

	/**
	*	Context action of the {@code conjugate} operator.
	*/
	@OperatorType("conjugate")
	public static final ContextAction PSY_CONJUGATE
		=ContextAction.<PsyComplex>ofFunction(PsyComplex::psyConjugate);

	/**
	*	Context action of the {@code imagpart} operator.
	*/
	@OperatorType("imagpart")
	public static final ContextAction PSY_IMAGPART
		=ContextAction.<PsyComplex>ofFunction(PsyComplex::psyImagPart);

	/**
	*	Context action of the {@code realpart} operator.
	*/
	@OperatorType("realpart")
	public static final ContextAction PSY_REALPART
		=ContextAction.<PsyComplex>ofFunction(PsyComplex::psyRealPart);
	/**
	*	Imaginary unit.
	*/
	public static final PsyComplex I=new PsyComplex(0.D, 1.D);

	/**
	*	Munus imaginary unit.
	*/
	public static final PsyComplex MINUS_I=new PsyComplex(0.D, -1.D);

	private final double re, im;

	public PsyComplex(final double re, final double im)
	{
		this.re=re;
		this.im=im;
	}

	public PsyComplex(final double re)
	{
		this(re, 0.D);
	}

	public PsyComplex(final PsyRealNumeric oRealPart, final PsyRealNumeric oImagPart)
	{
		this(oRealPart.doubleValue(), oImagPart.doubleValue());
	}

	public PsyComplex(final PsyRealNumeric oNumeric)
	{
		this(oNumeric.doubleValue());
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
	public boolean isZero()
	{
		return re==0.D && im==0.D;
	}

	@Override
	public PsyReal psyAbs()
	{
		return new PsyReal(Math.hypot(re, im));
	}

	@Override
	public PsyComplex psySignum()
	{
		if(isZero())
			return this;
		return psyDiv(psyAbs());
	}

	@Override
	public String toSyntaxString()
	{
		final var sb=new StringBuilder();
		sb.append(Double.isInfinite(re)? (re==Double.NEGATIVE_INFINITY? "-∞": "∞"): String.valueOf(re));
		if(Double.compare(im, 0.D)>=0)
			sb.append('+');
		sb.append(Double.isInfinite(im)? (im==Double.NEGATIVE_INFINITY? "-∞": "∞"): String.valueOf(im));
		sb.append('i');
		return sb.toString();
	}

	/**
	*	{@return a {@code real} real part of this object}
	*/
	public PsyReal psyRealPart()
	{
		return new PsyReal(re);
	}

	/**
	*	{@return a {@code real} imaginary part of this object}
	*/
	public PsyReal psyImagPart()
	{
		return new PsyReal(im);
	}

	/**
	*	{@return a {@code real} representing the complex argument of this object} The argument
	*		belongs to the range (−π; π].
	*/
	public PsyReal psyArg()
	{
		return new PsyReal(Math.atan2(im, re));
	}

	/**
	*	{@return a {@code complex} representing the complex conjugate of this object}
	*/
	public PsyComplex psyConjugate()
	{
		return new PsyComplex(re, -im);
	}

	@Override
	public PsyComplex psyNeg()
	{
		return new PsyComplex(-re, -im);
	}

	@Override
	public PsyComplex psyAdd(final PsyNumeric oNumeric)
	{
		return new PsyComplex(re+oNumeric.realValue(), im+oNumeric.imagValue());
	}

	@Override
	public PsyComplex psySub(final PsyNumeric oNumeric)
	{
		return new PsyComplex(re-oNumeric.realValue(), im-oNumeric.imagValue());
	}

	@Override
	public PsyComplex psyReciprocal()
	{
		if(Double.compare(re, -0.D)==0)
			if(Double.compare(im, -0.D)==0)
				return new PsyComplex(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
			else if(Double.compare(im, 0.D)==0)
				return new PsyComplex(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
		if(Double.compare(re, 0.D)==0)
			if(Double.compare(im, -0.D)==0)
				return new PsyComplex(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
			else if(Double.compare(im, 0.D)==0)
				return new PsyComplex(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY);
		final var d=re*re+im*im;
		return new PsyComplex(re/d, -im/d);
	}

	@Override
	public PsyComplex psyMul(final PsyNumeric oNumeric)
	{
		final var x=oNumeric.realValue();
		final var y=oNumeric.imagValue();
		final var t1=re*x;
		final var t2=im*y;
		return new PsyComplex(t1-t2, (re+im)*(x+y)-t1-t2);
	}

	@Override
	public PsyComplex psyDiv(final PsyNumeric oNumeric)
	{
		final var x=oNumeric.realValue();
		final var y=oNumeric.imagValue();
		if(Math.abs(x)<Math.abs(y))
		{
			final var q=x/y;
			final var d=x*q+y;
			return new PsyComplex((re*q+im)/d, (im*q-re)/d);
		}
		else
		{
			final var q=y/x;
			final var d=y*q+x;
			return new PsyComplex((im*q+re)/d, (im-re*q)/d);
		}
	}

	@Override
	public PsyNumeric psyPow(final PsyNumeric oNumeric)
	{
		if(isZero() && !oNumeric.isZero())
			return this;
		return psyLog().psyMul(oNumeric).psyExp();
	}

	@Override
	public PsyComplex psyExp()
	{
		final var reExp=Math.exp(re);
		return new PsyComplex(reExp*Math.cos(im), reExp*Math.sin(im));
	}

	@Override
	public PsyComplex psyCos()
	{
		return new PsyComplex(Math.cos(re)*Math.cosh(im), -Math.sin(re)*Math.sinh(im));
	}

	@Override
	public PsyComplex psySin()
	{
		return new PsyComplex(Math.sin(re)*Math.cosh(im), Math.cos(re)*Math.sinh(im));
	}

	@Override
	public PsyComplex psyLog()
	{
		return new PsyComplex(Math.log(Math.hypot(re, im)), Math.atan2(im, re));
	}

	@Override
	public PsyComplex psyAcos()
	{
		return psyAdd(PsyReal.ONE.psySub(psyMul(this)).psySqrt().psyMul(I)).psyLog().psyMul(MINUS_I);
	}

	@Override
	public PsyComplex psyAsin()
	{
		return new PsyComplex(Math.PI/2.D, 0.D).psySub(psyAcos());
	}

	@Override
	public PsyComplex psyAtan()
	{
		return (I.psyAdd(this).psyDiv(I.psySub(this))).psyLog().psyMul(I).psyDiv(PsyReal.TWO);
	}

	@Override
	public PsyComplex psySqrt()
	{
		return fromPolar(Math.sqrt(Math.hypot(re, im)), Math.atan2(im, re)/2.D);
	}

	@Override
	public PsyComplex psyCbrt()
	{
		return fromPolar(Math.cbrt(Math.hypot(re, im)), Math.atan2(im, re)/3.D);
	}

	@Override
	public PsyComplex psyCosh()
	{
		final var oExp=psyExp();
		return oExp.psyAdd(oExp.psyReciprocal()).psyDiv(PsyReal.TWO);
	}

	@Override
	public PsyComplex psyTan()
	{
		return psySin().psyDiv(psyCos());
	}

	@Override
	public PsyComplex psySinh()
	{
		final var oExp=psyExp();
		return oExp.psySub(oExp.psyReciprocal()).psyDiv(PsyReal.TWO);
	}

	@Override
	public PsyComplex psyTanh()
	{
		return psySinh().psyDiv(psyCosh());
	}

	public static PsyComplex psyFromPolar(final PsyRealNumeric oAbs, final PsyRealNumeric oArg)
	{
		return fromPolar(oAbs.doubleValue(), oArg.doubleValue());
	}

	public static PsyComplex fromPolar(final double abs, final double arg)
	{
		return new PsyComplex(abs*Math.cos(arg), abs*Math.sin(arg));
	}
}
