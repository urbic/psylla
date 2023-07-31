package coneforest.psylla.core;

import coneforest.psylla.*;

/**
*	The representation of the {@code invalidexit} error thrown by the {@code exit} operator when
*	invoked outside the cyclic context.
*/
@ErrorType("invalidexit")
public class PsyInvalidExitException
	extends PsyErrorException
{
}
