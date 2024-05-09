package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of the {@code stackunderflow} error thrown when the operand stack contains
*	fewer operands than the operator expects.
*/
@ErrorType("stackunderflow")
public class PsyStackUnderflowException
	extends PsyErrorException
{
}
