package coneforest.psylla.core;
import coneforest.psylla.*;

@Type("container")
public interface PsyContainer<T extends PsyObject>
	extends
		PsyClearable,
		PsyIterable<T>,
		PsyLengthy
{

	default public PsyContainer<T> psyNewEmpty()
		throws PsyException
	{
		try
		{
			return getClass().getConstructor().newInstance();
		}
		catch(final InstantiationException
				|IllegalAccessException
				|NoSuchMethodException
				|java.lang.reflect.InvocationTargetException e)
		{
			throw new PsyUnsupportedException();
		}
	}

	default public String toSyntaxStringHelper(final java.util.Set<PsyContainer<T>> processed)
	{
		return toSyntaxString();
	}
}
