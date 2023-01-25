package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of {@code bitwise}, a type of object that is an operand of
*	bitwise operation. This interface declares methods for setting, clearing,
*	testing of certain bits and bitwise shift.
*/
@Type("bitwise")
public interface PsyBitwise<T extends PsyBitwise>
	extends PsyLogical<T>
{

	/**
	*	Returns a {@code bitwise} whose value is equivalent to this value with
	*	the designated bit cleared.
	*
	*	@param oBit the index of bit to clear.
	*	@return a {@code bitwise} whose value is equivalent to this value with
	*	the designated bit cleared.
	*/
	public PsyBitwise psyClearBit(final PsyInteger oBit)
		throws PsyErrorException;

	/**
	*	Returns a {@code bitwise} whose value is equivalent to this value with
	*	the designated bit flipped.
	*
	*	@param oBit the index of bit to flip.
	*	@return a {@code bitwise} whose value is equivalent to this value with
	*	the designated bit flipped.
	*/
	public PsyBitwise psyFlipBit(final PsyInteger oBit)
		throws PsyErrorException;

	/**
	*	Returns a {@code bitwise} whose value is equivalent to this value with
	*	the designated bit set.
	*
	*	@param oBit the index of bit to set.
	*	@return a {@code bitwise} whose value is equivalent to this value with
	*	the designated bit set.
	*/
	public PsyBitwise psySetBit(final PsyInteger oBit)
		throws PsyErrorException;

	/**
	*	Returns a {@code boolean} indicating if the designated bit is set.
	*
	*	@param oBit the index of bit to test.
	*	@return a {@code boolean} indicating if the designated bit set.
	*/
	public PsyBoolean psyTestBit(final PsyInteger oBit)
		throws PsyErrorException;

	/**
	*	Returns a {@code bitwise} whose value is equivalent to this value with
	*	bits shifted at the given distance. If the distance is negative, the
	*	left shift is preformed. If the distance is positive, the right shift
	*	is preformed. If the distance is zero, returns this.
	*
	*	@param oShift the shift distance.
	*	@return a {@code bitwise} whose value is equivalent to this value with
	*	bits shifted at the given distance.
	*/
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
