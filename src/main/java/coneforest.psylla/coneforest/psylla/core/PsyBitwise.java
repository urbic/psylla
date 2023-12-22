package coneforest.psylla.core;

import coneforest.psylla.*;

/**
*	The representation of {@code bitwise}, a type of object that is an operand of bitwise operation.
*	This interface declares methods for setting, clearing, testing of certain bits and bitwise
*	shift.
*
*	@param <T> a type of the second operand at binary operations.
*/
@Type("bitwise")
public interface PsyBitwise<T extends PsyBitwise>
	extends PsyLogical<T>
{

	/**
	*	Returns a {@code bitwise} whose value is equivalent to this value with the designated bit
	*	cleared.
	*
	*	@param oBit the index of bit to clear.
	*	@return a {@code bitwise} whose value is equivalent to this value with
	*	the designated bit cleared.
	*	@throws PsyRangeCheckException if the index is out of range.
	*/
	public PsyBitwise psyClearBit(final PsyInteger oBit)
		throws PsyRangeCheckException;

	/**
	*	Returns a {@code bitwise} whose value is equivalent to this value with the designated bit
	*	flipped.
	*
	*	@param oBit the index of bit to flip.
	*	@return a {@code bitwise} whose value is equivalent to this value with
	*	the designated bit flipped.
	*	@throws PsyRangeCheckException if the index is out of range.
	*/
	public PsyBitwise psyFlipBit(final PsyInteger oBit)
		throws PsyRangeCheckException;

	/**
	*	Returns a {@code bitwise} whose value is equivalent to this value with the designated bit
	*	set.
	*
	*	@param oBit the index of bit to set.
	*	@return a {@code bitwise} whose value is equivalent to this value with
	*	the designated bit set.
	*	@throws PsyRangeCheckException if the index is out of range.
	*/
	public PsyBitwise psySetBit(final PsyInteger oBit)
		throws PsyRangeCheckException;

	/**
	*	Returns a {@code boolean} indicating if the designated bit is set.
	*
	*	@param oBit the index of bit to test.
	*	@return a {@code boolean} indicating if the designated bit set.
	*	@throws PsyRangeCheckException if the index is out of range.
	*/
	public PsyBoolean psyTestBit(final PsyInteger oBit)
		throws PsyRangeCheckException;

	/**
	*	Returns a {@code bitwise} whose value is equivalent to this value with bits shifted at the
	*	given distance. If the distance is negative, the left shift is preformed. If the distance is
	*	positive, the right shift is preformed. If the distance is zero, returns this.
	*
	*	@param oShift the shift distance.
	*	@return a {@code bitwise} whose value is equivalent to this value with
	*	bits shifted at the given distance.
	*/
	public PsyBitwise psyBitShift(final PsyInteger oShift);

	/**
	*	Context action of the {@code bitshift} operator.
	*/
	@OperatorType("bitshift")
	public static final ContextAction PSY_BITSHIFT
		=ContextAction.<PsyBitwise, PsyInteger>ofBiFunction(PsyBitwise::psyBitShift);

	/**
	*	Context action of the {@code clearbit} operator.
	*/
	@OperatorType("clearbit")
	public static final ContextAction PSY_CLEARBIT
		=ContextAction.<PsyBitwise, PsyInteger>ofBiFunction(PsyBitwise::psyClearBit);

	/**
	*	Context action of the {@code flipbit} operator.
	*/
	@OperatorType("flipbit")
	public static final ContextAction PSY_FLIPBIT
		=ContextAction.<PsyBitwise, PsyInteger>ofBiFunction(PsyBitwise::psyFlipBit);

	/**
	*	Context action of the {@code setbit} operator.
	*/
	@OperatorType("setbit")
	public static final ContextAction PSY_SETBIT
		=ContextAction.<PsyBitwise, PsyInteger>ofBiFunction(PsyBitwise::psySetBit);

	/**
	*	Context action of the {@code testbit} operator.
	*/
	@OperatorType("testbit")
	public static final ContextAction PSY_TESTBIT
		=ContextAction.<PsyBitwise, PsyInteger>ofBiFunction(PsyBitwise::psyTestBit);
}
