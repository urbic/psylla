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
	*	Creates a new dictionary stack with two permanent dictionaries in it (system and user
	*	dictionaries).
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

	/**
	*	Performs in-depth search for the given key in this stack and returns the associated value.
	*
	*	@param <T> the type of the value.
	*	@param key the key.
	*	@return the associated value.
	*	@throws PsyUndefinedException if the key is not found.
	*/
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

	/**
	*	Performs in-depth search for the given {@code textual} key in this stack and returns the
	*	associated value.
	*
	*	@param <T> the type of the value.
	*	@param oKey the {@code textual} key.
	*	@return the associated value.
	*	@throws PsyUndefinedException if the key is not found.
	*/
	public <T extends PsyObject> T load(final PsyTextual oKey)
		throws PsyUndefinedException
	{
		return this.<T>load(oKey.stringValue());
	}

	/**
	*	Performs in-depth search for the dictionary containing the given key in this stack and
	*	returns an {@link Optional} contating the dictionary found or empty {@link Optional} if not
	*	found.
	*
	*	@param key the key.
	*	@return a {@link Optional} containing the dictionary found.
	*/
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

	/**
	*	Performs in-depth search for the dictionary containing the given {@code textual} key in this
	*	stack and returns an {@link Optional} contating the dictionary found or empty {@link
	*	Optional} if not found.
	*
	*	@param oKey the {@code textual} key.
	*	@return an {@link Optional} containing the dictionary found.
	*/
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

	/**
	*	Pushes the dictionary to this stack.
	*
	*	@param oDict the {@code formaldict} dictionary.
	*/
	public void begin(final PsyFormalDict oDict)
	{
		push(oDict);
	}

	/**
	*	Pops a non-permanent dictionary from this stack.
	*
	*	@throws PsyDictStackUnderflowException if this stack does not contain non-permanent
	*	dictionaries.
	*/
	public void end()
		throws PsyDictStackUnderflowException
	{
		if(size()<=2)
			throw new PsyDictStackUnderflowException();
		pop();
	}
}
