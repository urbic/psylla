package coneforest.psylla;
import coneforest.psylla.core.*;

/**
*	An interpreter’s dictionary stack.
*/
public class DictStack
	extends Stack<PsyFormalDict<PsyObject>>
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
		var oSystemDict=new PsySystemDict();
		var oUserDict=new PsyDict();
		push(oSystemDict);
		push(oUserDict);
		oSystemDict.put("systemdict", oSystemDict);
		oSystemDict.put("userdict", oUserDict);
	}

	@Override
	public DictStack clone()
	{
		var cloned=(DictStack)super.clone();
		for(int i=0; i<cloned.size(); i++)
			cloned.set(i, (PsyFormalDict<PsyObject>)cloned.get(i).psyClone());
		return cloned;
	}

	public <T extends PsyObject> T load(final String key)
		throws PsyException
	{
		final var oDict=where(key);
		if(oDict!=null)
			return (T)oDict.get(key);
		else
			throw new PsyUndefinedException();
	}

	public <T extends PsyObject> T load(final PsyTextual oKey)
		throws PsyException
	{
		return this.<T>load(oKey.stringValue());
	}

	public PsyFormalDict where(final String key)
	{
		for(int i=size()-1; i>=0; i--)
		{
			final var oDict=get(i);
			if(oDict.known(key))
				return oDict;
		}
		return null;
	}

	public PsyFormalDict where(final PsyTextual oKey)
	{
		return where(oKey.stringValue());
	}

	public PsyNamespace currentNamespace()
	{
		for(int i=size()-1; i>=0; i--)
		{
			final var oDict=get(i);
			if(oDict instanceof PsyNamespace)
				return (PsyNamespace)oDict;
		}
		return null; // TODO
	}

	public void store(final String key, final PsyObject oValue)
	{
		var oDict=where(key);
		if(oDict==null)
			oDict=peek();
		oDict.put(key, oValue);
	}

	public void psyStore(final PsyTextual oKey, final PsyObject oValue)
	{
		store(oKey.stringValue(), oValue);
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
