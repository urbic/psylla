package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of Ψ-{@code integral} object.
*/
@Type("integral")
public interface PsyIntegral
	extends
		//PsyBitwise<PsyIntegral>, TODO
		PsyRealNumeric
{
	public PsyIntegral psyIdiv(final PsyIntegral oInteger)
		throws PsyException;

	public PsyIntegral psyMod(final PsyIntegral oInteger)
		throws PsyException;

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity21<PsyIntegral, PsyIntegral>
				("idiv", PsyIntegral::psyIdiv),
			new PsyOperator.Arity21<PsyIntegral, PsyIntegral>
				("mod", PsyIntegral::psyMod),
		};

}
