package coneforest.psylla.core;

import coneforest.psylla.runtime.*;
import java.util.stream.StreamSupport;

/**
*	The representation of {@code iterable}, a type of an object that can be iterated over.
*
*	@param <T> a type of elements returned by the iterator.
*/
@Type("iterable")
public interface PsyIterable<T extends PsyObject>
	extends
		PsyStreamable<T>,
		Iterable<T>
{

	public default PsyArray psyToArray()
		throws PsyErrorException
	{
		final var oArray=new PsyArray();
		for(final T o: this)
			oArray.psyAppend(o);
		return oArray;
	}

	@Override
	public default PsyFormalStream<T> psyStream()
	{
		//return new PsyStream(StreamSupport.<T>stream(spliterator(), false));
		return PsyFormalStream.<T>of(StreamSupport.<T>stream(spliterator(), false));
	}

	public default PsyStringBuffer psyUnite(final PsyTextual oSeparator)
		throws PsyErrorException
	{
		final var separator=oSeparator.stringValue();
		final var sb=new StringBuilder();
		final var iterator=iterator();
		try
		{
			while(iterator.hasNext())
			{
				sb.append(((PsyTextual)iterator.next()).stringValue());
				if(iterator.hasNext())
					sb.append(separator);
			}
		}
		catch(final ClassCastException ex)
		{
			throw new PsyTypeCheckException();
		}
		return new PsyStringBuffer(sb);
	}
}
