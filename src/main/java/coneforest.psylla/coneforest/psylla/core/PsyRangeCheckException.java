package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of the {@code rangecheck} error thrown when the operand has the value outside
*	the range acceptable for the operator.
*/
@ErrorType("rangecheck")
public class PsyRangeCheckException
	extends PsyErrorException
{
}
