package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of the {@code invalidstate} error thrown if the execution of the operator is
*	not possible with the current state of the operand or environment.
*/
@ErrorType("invalidstate")
public class PsyInvalidStateException
	extends PsyErrorException
{
}
