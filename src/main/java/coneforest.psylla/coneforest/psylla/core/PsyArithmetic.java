package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of {@code arithmetic}, a type of object that is an operand of arithmetic
*	operation. This interface declares methods for multiplication, division.
*
*	@param <T> a type of the second operand at binary operation.
*/
@Type("arithmetic")
public interface PsyArithmetic<T extends PsyArithmetic>
	extends
		PsyAdditive<T>,
		PsyMultiplicative<T>
{
}
