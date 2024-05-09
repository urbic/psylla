package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	 The representation of {@code concurrentmodification} error thrown when concurrent modification
*	 detected.
*/
@ErrorType("concurrentmodification")
public class PsyConcurrentModificationException
	extends PsyErrorException
{
}
