package coneforest.psylla;

import coneforest.psylla.core.*;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
*	An interpreter’s dictionary stack.
*/
public class DictStack
	extends Stack<PsyFormalDict<PsyObject>>
{
	/**
	*	Creates a new dictionary stack with two dictionaries in it (system and user dictionaries).
	*
	*	@throws PsyUndefinedException when TODO.
	*/
	public DictStack()
		throws PsyUndefinedException
	{
		var oSystemDict=new PsySystemDict();
		var oUserDict=new PsyDict();
		push(oSystemDict);
		push(oUserDict);
		oSystemDict.put("systemdict", oSystemDict);
		oSystemDict.put("userdict", oUserDict);
	}

	@Override
	public Object clone()
	{
		var cloned=(DictStack)super.clone();
		for(int i=0; i<cloned.size(); i++)
			cloned.set(i, (PsyFormalDict<PsyObject>)cloned.get(i).psyClone());
		return cloned;
	}

	public <T extends PsyObject> T load(final String key)
		throws PsyUndefinedException
	{
		try
		{
			return (T)where(key).orElseThrow().get(key);
		}
		catch(final NoSuchElementException ex)
		{
			throw new PsyUndefinedException();
		}
	}

	public <T extends PsyObject> T load(final PsyTextual oKey)
		throws PsyUndefinedException
	{
		return this.<T>load(oKey.stringValue());
	}

	public Optional<PsyFormalDict> where(final String key)
	{
		for(int i=size()-1; i>=0; i--)
		{
			final var oDict=get(i);
			if(oDict.known(key))
				return Optional.<PsyFormalDict>of(oDict);
		}
		return Optional.<PsyFormalDict>empty();
	}

	public Optional<PsyFormalDict> where(final PsyTextual oKey)
	{
		return where(oKey.stringValue());
	}

	public PsyNamespace currentNamespace()
	{
		for(int i=size()-1; i>=0; i--)
		{
			final var oDict=get(i);
			if(oDict instanceof PsyNamespace oNamespace)
				return oNamespace;
		}
		return null; // TODO
	}

	public void store(final PsyTextual oKey, final PsyObject oValue)
	{
		var key=oKey.stringValue();
		where(key).orElse(peek()).put(key, oValue);
	}

	public void begin(final PsyFormalDict oDict)
	{
		push(oDict);
	}

	public void end()
		throws PsyDictStackUnderflowException
	{
		if(size()<=2)
			throw new PsyDictStackUnderflowException();
		pop();
	}
}
