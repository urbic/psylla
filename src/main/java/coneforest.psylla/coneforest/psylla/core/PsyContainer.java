package coneforest.psylla.core;

import coneforest.psylla.runtime.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

@Type("container")
public interface PsyContainer<T extends PsyObject>
	extends
		PsyClearable,
		PsyIterable<T>,
		PsyLengthy
{

	@SuppressWarnings("unchecked")
	public default PsyContainer<T> psyNewEmpty()
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

	public default String toSyntaxStringHelper(final Set<PsyContainer<? extends PsyObject>> processed)
	{
		return toSyntaxString();
	}

}
