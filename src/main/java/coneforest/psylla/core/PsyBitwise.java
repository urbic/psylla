package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of Ψ-{@code bitwise}, a type of object that is an operand
*	of bitwise operation. This interface declares methods for setting,
*	clearing, testing of certain bits and bitwise shift.
*/
@Type("bitwise")
public interface PsyBitwise<T extends PsyBitwise>
	extends PsyLogical<T>
{

	public PsyBitwise psyClearBit(final PsyInteger oBit)
		throws PsyException;

	public PsyBitwise psyFlipBit(final PsyInteger oBit)
		throws PsyException;

	public PsyBitwise psySetBit(final PsyInteger oBit)
		throws PsyException;

	public PsyBoolean psyTestBit(final PsyInteger oBit)
		throws PsyException;

	public PsyBitwise psyBitShift(final PsyInteger oShift);

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity21<PsyBitwise, PsyInteger>
				("bitshift", PsyBitwise::psyBitShift),
			new PsyOperator.Arity21<PsyBitwise, PsyInteger>
				("clearbit", PsyBitwise::psyClearBit),
			new PsyOperator.Arity21<PsyBitwise, PsyInteger>
				("flipbit", PsyBitwise::psyFlipBit),
			new PsyOperator.Arity21<PsyBitwise, PsyInteger>
				("setbit", PsyBitwise::psySetBit),
			new PsyOperator.Arity21<PsyBitwise, PsyInteger>
				("testbit", PsyBitwise::psyTestBit),
		};
}
