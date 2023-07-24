package coneforest.psylla.core;

import coneforest.psylla.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

@Type("container")
public interface PsyContainer<T extends PsyObject>
	extends
		PsyClearable,
		PsyIterable<T>,
		PsyLengthy
{

	default public PsyContainer<T> psyNewEmpty()
		throws PsyUnsupportedException
	{
		try
		{
			return getClass().getConstructor().newInstance();
		}
		catch(final InstantiationException
				|IllegalAccessException
				|NoSuchMethodException
				|InvocationTargetException ex)
		{
			throw new PsyUnsupportedException();
		}
	}

	default public String toSyntaxStringHelper(final Set<PsyContainer<T>> processed)
	{
		return toSyntaxString();
	}
}
