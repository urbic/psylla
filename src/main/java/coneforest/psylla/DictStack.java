package coneforest.psylla;
import coneforest.psylla.core.*;

/**
*	An interpreter’s dictionary stack.
*/
public class DictStack
	extends Stack<PsyDictlike<PsyObject>>
{
	/**
	*	Creates a new dictionary stack with two dictionaries in it (system and
	*	user dictionaries).
	*
	*	@throws PsyException when the error occur.
	*/
	public DictStack()
		throws PsyException
	{
		final PsyDict oSystemDict=new PsySystemDict();
		push(oSystemDict);
		push((PsyDictlike)oSystemDict.get("userdict"));
	}

	public <T extends PsyObject> T load(final String key)
		throws PsyException
	{
		final PsyDictlike oDict=where(key);
		if(oDict!=null)
			return (T)oDict.get(key);
		else
			throw new PsyUndefinedException();
	}

	public <T extends PsyObject> T load(final PsyStringy oKey)
		throws PsyException
	{
		return this.<T>load(oKey.stringValue());
	}

	public PsyDictlike where(final String key)
	{
		for(int i=size()-1; i>=0; i--)
		{
			final PsyDictlike oDict=get(i);
			if(oDict.known(key))
				return oDict;
		}
		return null;
	}

	public PsyDictlike where(final PsyStringy oKey)
	{
		return where(oKey.stringValue());
	}

	public PsyNamespace currentNamespace()
	{
		for(int i=size()-1; i>=0; i--)
		{
			final PsyDictlike oDict=get(i);
			if(oDict instanceof PsyNamespace)
				return (PsyNamespace)oDict;
		}
		return null; // TODO
	}

	public void store(final String key, final PsyObject oValue)
	{
		PsyDictlike oDict=where(key);
		if(oDict==null)
			oDict=peek();
		oDict.put(key, oValue);
	}

	public void psyStore(final PsyStringy oKey, final PsyObject oValue)
	{
		store(oKey.stringValue(), oValue);
	}

	public void psyBegin(final PsyDictlike oDict)
	{
		push(oDict);
	}

	public void psyEnd()
		throws PsyDictStackUnderflowException
	{
		if(size()<=2)
			throw new PsyDictStackUnderflowException();
		pop();
	}
}
