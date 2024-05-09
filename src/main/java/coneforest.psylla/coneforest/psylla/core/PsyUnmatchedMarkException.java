package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of the {@code unmatchedmark} error thrown when a mark expected not on the
*	operand stack.
*/
@ErrorType("unmatchedmark")
public class PsyUnmatchedMarkException
	extends PsyErrorException
{
}
