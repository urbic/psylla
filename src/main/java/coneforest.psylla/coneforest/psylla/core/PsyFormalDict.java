package coneforest.psylla.core;

import coneforest.psylla.runtime.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
*	The representation of {@code formaldict}, an abstraction of a dictionary.
*
*	@param <V> a type of contained values.
*/
@Type("formaldict")
public interface PsyFormalDict<V extends PsyObject>
	extends
		PsyContainer<V>,
		PsyIndexed<PsyString, V>,
		PsySequential<V>
{
	/**
	*	Context action of the {@code undef} operator.
	*/
	@SuppressWarnings("rawtypes")
	@OperatorType("undef")
	public static final ContextAction PSY_UNDEF
		=ContextAction.<PsyFormalDict, PsyString>ofBiConsumer(PsyFormalDict::psyUndef);

	@Override
	public default void psyForAll(final PsyObject oProc, final PsyContext oContext)
		throws PsyErrorException
	{
		final var ostack=oContext.operandStack();
		final var keysIterator=psyKeys().stream().iterator();
		//oContext.pushLoopLevel();
		oContext.executionStack().enterLoop();
		oContext.executionStack().push(new PsyOperator("#forall_continue")
			{
				@Override
				public void perform(final PsyContext oContext1)
					throws PsyErrorException
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
						//oContext1.popLoopLevel();
						oContext1.executionStack().exitLoop();
				}
			});
	}

	public V get(final String key)
		throws PsyUndefinedException;

	@Override
	public default V psyGet(final PsyString oKey)
		throws PsyUndefinedException
	{
		return get(oKey.stringValue());
	}

	/*
	@Override
	public default PsyFormalArray<V> psyGetAll(final PsyIterable<PsyTextual> oEnumeration)
		throws PsyLimitCheckException, PsyRangeCheckException, PsyUndefinedException
	{
		final PsyFormalArray<V> oResult=(PsyFormalArray<V>)new PsyArray();
		for(final var oKey: oEnumeration)
			oResult.psyAppend(psyGet(oKey));
		return oResult;
	}
	*/

	public void put(final String key, final V oValue);

	@Override
	public default void psyPut(final PsyString oKey, final V oValue)
	{
		put(oKey.stringValue(), oValue);
	}

	public boolean known(final String key);

	@Override
	public default PsyBoolean psyKnown(final PsyString oKey)
	{
		return PsyBoolean.of(known(oKey.stringValue()));
	}

	public void undef(final String key);

	/**
	*	Deletes a key and associated value from this dictionary.
	*
	*	@param oKey a {@code string} key.
	*/
	public default void psyUndef(final PsyString oKey)
	{
		undef(oKey.stringValue());
	}

	@Override
	public default PsyFormalStream<PsyString> psyKeys()
	{
		return PsyFormalStream.<PsyString>of(keys().map(PsyString::new));
	}

	public Stream<String> keys();

	@Override
	public default void psyDelete(final PsyString oKey)
		throws PsyUndefinedException
	{
		psyUndef(oKey);
	}

	@Override
	public default V psyExtract(final PsyString oKey)
		throws PsyUndefinedException
	{
		final V oResult=psyGet(oKey);
		psyUndef(oKey);
		return oResult;
	}

	@Override
	public PsyFormalDict<V> psySlice(final PsyIterable<PsyString> oKeys)
		throws PsyUndefinedException;

	@Override
	public default PsyStream psyEntries()
	{
		return new PsyStream(StreamSupport.<PsyObject>stream(new PsyIterable<PsyObject>()
			{
				@Override
				public Iterator<PsyObject> iterator()
				{
					return new Iterator<PsyObject>()
						{
							private boolean flag=false;

							private PsyString oKey;

							private Iterator<PsyString> parentIterator
								=psyKeys().stream().iterator();

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
										oKey=parentIterator.next(): psyGet(oKey);
								}
								catch(final PsyErrorException e)
								{
									throw new AssertionError(e);
								}
							}

						};
				}
			}.spliterator(),
			false));
	}

	@Override
	public default String toSyntaxString()
	{
		return toSyntaxStringHelper(new HashSet<PsyContainer<? extends PsyObject>>());
	}

	@Override
	public default String toSyntaxStringHelper(final Set<PsyContainer<? extends PsyObject>> processed)
	{
		if(!processed.add(this))
			return '%'+typeName()+'%';
		final var sj=new StringJoiner(" ", "<", ">");
		psyEntries().stream().forEach(o->
			sj.add(o instanceof PsyContainer<? extends PsyObject> oContainer?
				oContainer.toSyntaxStringHelper(processed):
				o.toSyntaxString()));
		return sj.toString();
	}

	@Override
	public default void psyClear()
	{
		psyKeys().stream().forEach(this::psyUndef);
	}
}
