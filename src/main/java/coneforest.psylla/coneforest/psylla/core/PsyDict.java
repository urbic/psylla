package coneforest.psylla.core;

import coneforest.psylla.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.StreamSupport;

/**
*	The representation of {@code dict}, a dictionary.
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
		this(new HashMap<String, PsyObject>());
	}

	/**
	*	Creates a new {@code dict} wrapped around the given hash map.
	*
	*	@param dict a given hash map.
	*/
	public PsyDict(final HashMap<String, PsyObject> dict)
	{
		this.dict=dict;
	}

	@Override
	public PsyDict psyClone()
	{
		return new PsyDict((HashMap<String, PsyObject>)dict.clone());
	}

	@Override
	public PsyObject get(final String key)
		throws PsyUndefinedException
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
	public Iterator<PsyObject> iterator()
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
		throws PsyUndefinedException
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
		return new PsyStream(StreamSupport.<PsyObject>stream(spliterator(), false));
	}

	protected final HashMap<String, PsyObject> dict;

	/**
	*	Context action of the {@code dict} operator.
	*/
	@OperatorType("dict")
	public static final ContextAction PSY_DICT=ContextAction.ofSupplier(PsyDict::new);

	/**
	*	Context action of the {@code dicttomark} operator.
	*/
	@OperatorType("dicttomark")
	public static final ContextAction PSY_DICTTOMARK=oContext->
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
		};
}
