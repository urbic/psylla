package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of the {@code limitcheck} error thrown when the system limits exceeded.
*/
@ErrorType("limitcheck")
public class PsyLimitCheckException
	extends PsyErrorException
{
}
