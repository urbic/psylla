package coneforest.psylla.core;

import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

public class PsyEnvironment
	implements PsyFormalDict<PsyString>
{
	private Map<String, String> env;

	public PsyEnvironment(final Map<String, String> env)
	{
		this.env=env;
	}

	private PsyEnvironment()
	{
	}

	@Override
	public PsyEnvironment psySlice(final PsyIterable<PsyString> oKeys) // TODO
		throws PsyUndefinedException
	{
		final var oEnvironment=new PsyEnvironment();
		for(final var oKey: oKeys)
			oEnvironment.psyPut(oKey, psyGet(oKey));
		return oEnvironment;
	}

	//@Override
	public Stream<String> keys()
	{
		return env.keySet().stream();
	}

	@Override
	public boolean known(final String key)
	{
		return env.containsKey(key);
	}

	@Override
	public PsyString get(final String key)
		throws PsyUndefinedException
	{
		final var value=env.get(key);
		if(value!=null)
			return new PsyString(env.get(key));
		throw new PsyUndefinedException();
	}

	@Override
	public void put(final String key, final PsyString oName)
	{
		env.put(key, oName.stringValue());
	}

	@Override
	public void undef(final String key)
	{
		env.remove(key);
	}

	@Override
	public int length()
	{
		return env.size();
	}

	@Override
	public Iterator<PsyString> iterator()
	{
		return new Iterator<PsyString>()
			{
				private final Iterator<String> envIterator=env.values().iterator();

				@Override
				public boolean hasNext()
				{
					return envIterator.hasNext();
				}

				@Override
				public PsyString next()
				{
					return new PsyString(envIterator.next());
				}
			};
	}
}
