package coneforest.psylla.core;

import coneforest.psylla.*;

/**
*	The representation of the {@code limitcheck} error thrown when the system limits exceeded.
*/
@ErrorType("limitcheck")
public class PsyLimitCheckException
	extends PsyErrorException
{
}
