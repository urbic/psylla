package coneforest.psylla.core;

/**
*	A representation of Ψ-{@code bitwise}, a type of object that is an operand
*	of bitwise operation. This interface declares methods for setting,
*	clearing, testing of certain bits and bitwise shift.
*/
@coneforest.psylla.Type("bitwise")
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
}
