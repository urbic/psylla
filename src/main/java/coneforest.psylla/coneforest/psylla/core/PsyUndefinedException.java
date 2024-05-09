package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of the {@code undefined} error thrown by the operator accessing the
*	dictionary by the absent key or by the interpreter failing to resolve the command name against
*	the dictionary stack.
*/
@ErrorType("undefined")
public class PsyUndefinedException
	extends PsyErrorException
{
}
