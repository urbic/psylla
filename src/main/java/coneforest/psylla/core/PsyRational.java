package coneforest.psylla.core;
import coneforest.psylla.*;
import java.math.BigInteger;

@Type("rational")
public interface PsyRational
	extends PsyRealNumeric
{
	public BigInteger bigIntegerValue();

	public PsyIntegral psyNumerator();

	public PsyIntegral psyDenominator();

	public static PsyRational of(final PsyIntegral oNumerator, final PsyIntegral oDenominator)
		throws PsyUndefinedResultException
	{
		if(oDenominator.psyIsZero().booleanValue())
			throw new PsyUndefinedResultException();
		try
		{
			if(oNumerator.psyMod(oDenominator.psyAbs()).psyIsZero().booleanValue())
				return oNumerator.psyIdiv(oDenominator);
		}
		catch(final PsyRangeCheckException e)
		{
			// NOP
		}
		if(oNumerator instanceof PsyInteger && oDenominator instanceof PsyInteger)
			return PsyFractional.of(oNumerator.longValue(), oDenominator.longValue());
		else
			return PsyBigFractional.of(oNumerator.bigIntegerValue(), oDenominator.bigIntegerValue());
	}

	@Override
	default public int cmp(final PsyRealNumeric oNumeric)
	{
		if(oNumeric instanceof PsyRational)
			return psyNumerator().psyMul(((PsyRational)oNumeric).psyDenominator())
				.cmp(psyDenominator().psyMul(((PsyRational)oNumeric).psyNumerator()));
		return Double.compare(doubleValue(), oNumeric.doubleValue());
	}

	public static PsyRational parseLiteral(final String image)
		throws PsySyntaxErrorException, PsyUndefinedResultException
	{
		var colonIndex=image.indexOf(':');
		if(colonIndex==-1)
			return PsyIntegral.parseLiteral(image);
		return of(PsyIntegral.parseLiteral(image.substring(0, colonIndex)),
				PsyIntegral.parseLiteral(image.substring(colonIndex)));
	}

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity11<PsyRational>("numerator", PsyRational::psyNumerator),
			new PsyOperator.Arity11<PsyRational>("denominator", PsyRational::psyDenominator),
		};
}
