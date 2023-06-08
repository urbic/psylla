package coneforest.psylla.core.types;

import coneforest.psylla.Type;
import coneforest.psylla.core.errors.PsyError;
import coneforest.psylla.core.errors.PsyUnsupported;
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
		throws PsyError
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
			throw new PsyUnsupported();
		}
	}

	default public String toSyntaxStringHelper(final Set<PsyContainer<T>> processed)
	{
		return toSyntaxString();
	}
}
