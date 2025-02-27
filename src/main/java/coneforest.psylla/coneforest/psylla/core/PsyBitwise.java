package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of {@code bitwise}, a type of object that is an operand of bitwise operation.
*	This interface declares methods for setting, clearing, testing of certain bits and bitwise
*	shift.
*
*	@param <T> a type of the second operand at binary operations.
*/
@Type("bitwise")
public interface PsyBitwise<T extends PsyBitwise<T>>
	extends PsyLogical<T>
{
	/**
	*	Context action of the {@code bitshift} operator.
	*/
	@SuppressWarnings("rawtypes")
	@OperatorType("bitshift")
	public static final ContextAction PSY_BITSHIFT
		=ContextAction.<PsyBitwise, PsyInteger>ofBiFunction(PsyBitwise::psyBitShift);

	/**
	*	Context action of the {@code clearbit} operator.
	*/
	@SuppressWarnings("rawtypes")
	@OperatorType("clearbit")
	public static final ContextAction PSY_CLEARBIT
		=ContextAction.<PsyBitwise, PsyInteger>ofBiFunction(PsyBitwise::psyClearBit);

	/**
	*	Context action of the {@code flipbit} operator.
	*/
	@SuppressWarnings("rawtypes")
	@OperatorType("flipbit")
	public static final ContextAction PSY_FLIPBIT
		=ContextAction.<PsyBitwise, PsyInteger>ofBiFunction(PsyBitwise::psyFlipBit);

	/**
	*	Context action of the {@code setbit} operator.
	*/
	@SuppressWarnings("rawtypes")
	@OperatorType("setbit")
	public static final ContextAction PSY_SETBIT
		=ContextAction.<PsyBitwise, PsyInteger>ofBiFunction(PsyBitwise::psySetBit);

	/**
	*	Context action of the {@code testbit} operator.
	*/
	@SuppressWarnings("rawtypes")
	@OperatorType("testbit")
	public static final ContextAction PSY_TESTBIT
		=ContextAction.<PsyBitwise, PsyInteger>ofBiFunction(PsyBitwise::psyTestBit);

	/**
	*	{@return a {@code bitwise} whose value is equivalent to this value with the designated bit
	*	cleared}
	*
	*	@param oBit the index of bit to clear.
	*	@throws PsyRangeCheckException if the index is out of range.
	*/
	public PsyBitwise<T> psyClearBit(final PsyInteger oBit)
		throws PsyRangeCheckException;

	/**
	*	{@return a {@code bitwise} whose value is equivalent to this value with the designated bit
	*	flipped}
	*
	*	@param oBit the index of bit to flip.
	*	@throws PsyRangeCheckException if the index is out of range.
	*/
	public PsyBitwise<T> psyFlipBit(final PsyInteger oBit)
		throws PsyRangeCheckException;

	/**
	*	{@return a {@code bitwise} whose value is equivalent to this value with the designated bit
	*	set}
	*
	*	@param oBit the index of bit to set.
	*	@throws PsyRangeCheckException if the index is out of range.
	*/
	public PsyBitwise<T> psySetBit(final PsyInteger oBit)
		throws PsyRangeCheckException;

	/**
	*	{@return a {@code boolean} indicating if the designated bit is set}
	*
	*	@param oBit the index of bit to test.
	*	@throws PsyRangeCheckException if the index is out of range.
	*/
	public PsyBoolean psyTestBit(final PsyInteger oBit)
		throws PsyRangeCheckException;

	/**
	*	{@return a {@code bitwise} whose value is equivalent to this value with bits shifted at the
	*	given distance} If the distance is negative, the left shift is preformed. If the distance is
	*	positive, the right shift is preformed. If the distance is zero, returns this.
	*
	*	@param oShift the shift distance.
	*/
	public PsyBitwise<T> psyBitShift(final PsyInteger oShift);
}
