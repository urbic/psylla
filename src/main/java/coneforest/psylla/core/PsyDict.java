package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of {@code dict}, a dictionary.
*/
@Type("dict")
public class PsyDict
	implements PsyFormalDict<PsyObject>
{
	/**
	*	Creates a new empty {@code dict}.
	*/
	public PsyDict()
	{
		this(new java.util.HashMap<String, PsyObject>());
	}

	/**
	*	Creates a new {@code dict} wrapped around the given hash map.
	*
	*	@param dict a given hash map.
	*/
	public PsyDict(final java.util.HashMap<String, PsyObject> dict)
	{
		this.dict=dict;
	}

	@Override
	public PsyDict psyClone()
	{
		return new PsyDict((java.util.HashMap<String, PsyObject>)dict.clone());
	}

	@Override
	public PsyObject get(final String key)
		throws PsyErrorException
	{
		final var oValue=dict.get(key);
		if(oValue!=null)
			return oValue;
		else
			throw new PsyUndefinedException();
	}

	@Override
	public void put(final String key, final PsyObject o)
	{
		dict.put(key, o);
	}

	@Override
	public void undef(final String key)
	{
		dict.remove(key);
	}

	@Override
	public boolean known(final String key)
	{
		return dict.containsKey(key);
	}

	@Override
	public java.util.Iterator<PsyObject> iterator()
	{
		return dict.values().iterator();
	}

	@Override
	public int length()
	{
		return dict.size();
	}

	@Override
	public boolean isEmpty()
	{
		return dict.isEmpty();
	}

	@Override
	public void psyClear()
	{
		dict.clear();
	}

	@Override
	public PsyDict psySlice(final PsyIterable<PsyTextual> oKeys) // TODO
		throws PsyErrorException
	{
		final var values=new PsyDict();
		for(final var oKey: oKeys)
			values.psyPut(oKey, psyGet(oKey));
		return values;
	}

	@Override
	public PsyStream psyKeys()
	{
		return new PsyStream(dict.keySet().stream().<PsyTextual>map(PsyName::new));
	}

	@Override
	public PsyStream psyStream()
	{
		//return new PsyStream(stream());
		return new PsyStream(java.util.stream.StreamSupport.<PsyObject>stream(spliterator(), false));
	}

	protected final java.util.HashMap<String, PsyObject> dict;

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity01
				("dict", PsyDict::new),
			new PsyOperator.Action
				("dicttomark",
					(oContext)->
					{
						final var ostack=oContext.operandStack();
						final var i=ostack.findMarkPosition();
						final var ostackSize=ostack.size();
						if((ostackSize-i) % 2==0)
							throw new PsyRangeCheckException();
						final var oDict=new PsyDict();
						for(int j=i+1; j<ostackSize; j++)
						{
							final var oKey=(PsyTextual)ostack.get(j++);
							final var o=ostack.get(j);
							oDict.psyPut(oKey, o);
						}
						ostack.setSize(i);
						ostack.push(oDict);
					}),
		};
}
