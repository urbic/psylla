package coneforest.psylla.core.types;

import coneforest.psylla.Type;
import coneforest.psylla.core.errors.PsyError;

/**
*	A representation of {@code formaldict}, an abstraction of a dictionary.
*
*	@param <V> a type of contained values.
*/
@Type("formaldict")
public interface PsyFormalDict<V extends PsyObject>
	extends
		PsyContainer<V>,
		PsyIndexed<PsyTextual, V>,
		PsySequential<V>
{

	@Override
	default public void psyForAll(final PsyObject oProc, final PsyContext oContext)
		throws PsyError
	{
		final var ostack=oContext.operandStack();
		final var keysIterator=psyKeys().stream().iterator();
		oContext.pushLoopLevel();
		oContext.executionStack().push(new PsyOperator("#forall_continue")
			{
				@Override
				public void perform(final PsyContext oContext1)
					throws PsyError
				{
					if(keysIterator.hasNext())
					{
						final var oKey=keysIterator.next();
						ostack.push(oKey);
						ostack.push(psyGet(oKey));
						oContext1.executionStack().push(this);
						oProc.invoke(oContext1);
					}
					else
						oContext1.popLoopLevel();
				}
			});
	}

	public V get(final String key)
		throws PsyError;

	@Override
	default public V psyGet(final PsyTextual oKey)
		throws PsyError
	{
		return get(oKey.stringValue());
	}

	@Override
	default public PsyFormalArray<V> psyGetAll(final PsyIterable<PsyTextual> oEnumeration)
		throws PsyError
	{
		final PsyFormalArray<V> oResult=(PsyFormalArray<V>)new PsyArray();
		for(final var oKey: oEnumeration)
			oResult.psyAppend(psyGet(oKey));
		return oResult;
	}

	public void put(final String key, final V oValue);

	@Override
	default public void psyPut(final PsyTextual oKey, final V oValue)
	{
		put(oKey.stringValue(), oValue);
	}

	public boolean known(final String key);

	@Override
	default public PsyBoolean psyKnown(final PsyTextual oKey)
	{
		return PsyBoolean.of(known(oKey.stringValue()));
	}

	public void undef(final String key);

	/**
	*	Deletes a key and associated value from this dictionary.
	*
	*	@param oKey a {@code textual} key.
	*/
	default public void psyUndef(final PsyTextual oKey)
	{
		undef(oKey.stringValue());
	}

	@Override
	public PsyFormalStream<PsyTextual> psyKeys();

	@Override
	default public void psyDelete(final PsyTextual oKey)
		throws PsyError
	{
		psyUndef(oKey);
	}

	@Override
	default public V psyExtract(final PsyTextual oKey)
		throws PsyError
	{
		V oResult=psyGet(oKey);
		psyUndef(oKey);
		return oResult;
	}

	@Override
	public PsyFormalDict<V> psySlice(final PsyIterable<PsyTextual> oEnumeration)
		throws PsyError;

	@Override
	default public PsyStream psyEntries()
	{
		return new PsyStream(java.util.stream.StreamSupport.<PsyObject>stream(new PsyIterable<PsyObject>()
			{
				@Override
				public java.util.Iterator<PsyObject> iterator()
				{
					return new java.util.Iterator<PsyObject>()
						{
							@Override
							public boolean hasNext()
							{
								return parentIterator.hasNext() || flag;
							}

							@Override
							public PsyObject next()
							{
								try
								{
									return (flag=!flag)?
										(oKey=(PsyName)parentIterator.next()): psyGet(oKey);
								}
								catch(final PsyError e)
								{
									throw new AssertionError(e);
								}
							}

							private boolean flag=false;

							private PsyName oKey;

							private java.util.Iterator<PsyTextual> parentIterator
								=psyKeys().stream().iterator();

						};
				}
			}.spliterator(),
			false));
		//*/
	}

	@Override
	default public String toSyntaxString()
	{
		return toSyntaxStringHelper(new java.util.HashSet<PsyContainer<V>>());
	}

	@Override
	default public String toSyntaxStringHelper(final java.util.Set<PsyContainer<V>> processed)
	{
		if(!processed.add((PsyContainer<V>)this))
			return '%'+typeName()+'%';
		final var sj=new java.util.StringJoiner(" ", "<", ">");
		psyEntries().stream().forEach(o->
			sj.add(o instanceof PsyContainer?
				((PsyContainer<V>)o).toSyntaxStringHelper(processed):
				o.toSyntaxString()));
		return sj.toString();
	}


	@Override
	default public void psyClear()
	{
		psyKeys().stream().forEach(this::psyUndef);
	}

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity20<PsyFormalDict, PsyTextual>
				("undef", PsyFormalDict::psyUndef),
		};

}
