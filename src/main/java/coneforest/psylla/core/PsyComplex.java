package coneforest.psylla.core;

/**
*	A representation of Ψ-{@code complex} object.
*/
@coneforest.psylla.Type("complex")
public class PsyComplex
	implements
		PsyNumeric
{
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
	public PsyBoolean psyIsZero()
	{
		return PsyBoolean.valueOf(re==0.D && im==0.D);
	}

	@Override
	public PsyReal psyAbs()
	{
		return new PsyReal(Math.hypot(re, im));
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
	public PsyReal psyRealPart()
	{
		return new PsyReal(re);
	}

	/**
	*	Returns a Ψ-{@code real} imaginary part of this object.
	*
	*	@return a Ψ-{@code real} imaginary part.
	*/

	public PsyReal psyImagPart()
	{
		return new PsyReal(im);
	}

	/**
	*	Returns a Ψ-{@code real} representing the complex argument of this
	*	object. The argument belongs to the range (−π; π].
	*
	*	@return a Ψ-{@code real} argument.
	*/
	public PsyReal psyArg()
	{
		return new PsyReal(Math.atan2(im, re));
	}

	/**
	*	Returns a Ψ-{@code complex} representing the complex conjugate of this
	*	object.
	*
	*	@return a Ψ-{@code complex} conjugate of this number.
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
	public PsyComplex psyMul(final PsyNumeric oNumeric)
	{
		final var x=oNumeric.realValue();
		final var y=oNumeric.imagValue();
		return new PsyComplex(re*x-im*y, im*x+re*y);
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
		throws PsyException
	{
		if(psyIsZero().booleanValue() && oNumeric.psyNotZero().booleanValue())
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
		return new PsyComplex(Math.cos(re)*Math.cosh(im), Math.sin(re)*Math.sinh(im));
	}

	@Override
	public PsyComplex psySin()
	{
		return new PsyComplex(Math.sin(re)*Math.cosh(im), -Math.cos(re)*Math.sinh(im));
	}

	@Override
	public PsyComplex psyLog()
	{
		return new PsyComplex((PsyReal)psyAbs().psyLog(), psyArg());
	}

	@Override
	public PsyComplex psyAcos()
	{
		return psyAdd(ONE.psySub(psyMul(this)).psySqrt().psyMul(I)).psyLog().psyMul(MINUS_I);
	}

	@Override
	public PsyComplex psyAsin()
	{
		return new PsyComplex(Math.PI/2.D).psySub(psyAcos());
	}

	@Override
	public PsyComplex psyAtan()
	{
		return (I.psyAdd(this).psyDiv(I.psySub(this))).psyLog().psyMul(I).psyDiv(TWO);
	}

	@Override
	public PsyComplex psySqrt()
	{
		return psyFromPolar(Math.sqrt(Math.hypot(re, im)), Math.atan2(im, re)/2.D);
	}

	@Override
	public PsyComplex psyCbrt()
	{
		return psyFromPolar(Math.cbrt(Math.hypot(re, im)), Math.atan2(im, re)/3.D);
	}

	@Override
	public PsyComplex psyCosh()
	{
		final var tmpExp=psyExp();
		return tmpExp.psyAdd(ONE.psyDiv(tmpExp)).psyDiv(TWO);
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
		return oExp.psySub(ONE.psyDiv(oExp)).psyDiv(TWO);
	}

	@Override
	public PsyComplex psyTanh()
	{
		return psySinh().psyDiv(psyCosh());
	}

	public static PsyComplex psyFromPolar(final PsyRealNumeric oAbs, final PsyRealNumeric oArg)
	{
		return psyFromPolar(oAbs.doubleValue(), oArg.doubleValue());
	}

	public static PsyComplex psyFromPolar(final double abs, final double arg)
	{
		return new PsyComplex(abs*Math.cos(arg), abs*Math.sin(arg));
	}

	public static final PsyComplex
		ZERO=new PsyComplex(0.D, 0.D),
		ONE=new PsyComplex(1.D, 0.D),
		MINUS_ONE=new PsyComplex(-1.D, 0.D),
		TWO=new PsyComplex(2.D, 0.D),
		I=new PsyComplex(0.D, 1.D),
		MINUS_I=new PsyComplex(0.D, -1.D);

	private final double re, im;
}
