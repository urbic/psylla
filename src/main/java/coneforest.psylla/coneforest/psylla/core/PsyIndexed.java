package coneforest.psylla.core;

import coneforest.psylla.*;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

/**
*	The representation of {@code indexed}, a type of the container whose elements are indexed.
*
*	@param <K> a type of keys or indices.
*	@param <V> a type of elements.
*/
@Type("indexed")
public interface PsyIndexed<K extends PsyObject, V extends PsyObject>
	extends PsyObject
{

	/**
	*	Returns a {@code boolean} indicating whether given key or index exists in this object.
	*
	*	@param oKey a key or an index.
	*	@return a result.
	*/
	public PsyBoolean psyKnown(final K oKey);

	/**
	*	Returns the element with given key or index.
	*
	*	@param oKey a key or an index.
	*	@return an element.
	*	@throws PsyRangeCheckException when index is out of range.
	*	@throws PsyUndefinedException when the key is absent.
	*/
	public V psyGet(final K oKey)
		throws PsyRangeCheckException, PsyUndefinedException;

	/**
	*	Stores an element with given key or index. In {@link PsyFormalArray} containers replaces
	*	existing element. In {@link PsyFormalDict} containers replaces an old or creates a new
	*	element associated with specified key.
	*
	*	@param oKey a key or an index.
	*	@param oValue an element to be stored.
	*	@throws PsyRangeCheckException when the index is out of range.
	*/
	public void psyPut(final K oKey, final V oValue)
		throws PsyRangeCheckException;

	/**
	*	Deletes a key or index and a value associated with it from this object.
	*
	*	@param oKey a key or an index.
	*	@throws PsyRangeCheckException when the index is out of range.
	*	@throws PsyUndefinedException when the key is absent.
	*/
	public void psyDelete(final K oKey)
		throws PsyRangeCheckException, PsyUndefinedException;

	public V psyExtract(final K oKey)
		throws PsyRangeCheckException, PsyUndefinedException;

	/**
	*	Returns a container of the same type as this object consisting of keys or indices from given
	*	{@code iterable} and of associated values.
	*
	*	@param oKeys an enumeration of keys.
	*	@return a container.
	*	@throws PsyLimitCheckException when TODO
	*	@throws PsyRangeCheckException when the index is out of range.
	*	@throws PsyUndefinedException when the key is absent.
	*	@throws PsyUnsupportedException when TODO
	*/
	public PsyIndexed<K, V> psySlice(final PsyIterable<K> oKeys)
		throws
			PsyRangeCheckException,
			PsyLimitCheckException,
			PsyUndefinedException,
			PsyUnsupportedException;

	default public PsyIterable<V> psyGetAll(final PsyIterable<K> oKeys)
		throws
			PsyRangeCheckException,
			PsyLimitCheckException,
			PsyUndefinedException,
			PsyUnsupportedException
	{
		return new PsyIterable<V>()
			{
				@Override
				public Iterator iterator()
				{
					return new Iterator()
						{
							@Override
							public boolean hasNext()
							{
								return keysIterator.hasNext();
							}

							@Override
							public V next()
							{
								try
								{
									return PsyIndexed.this.psyGet(keysIterator.next());
								}
								catch(final PsyRangeCheckException|PsyUndefinedException e)
								{
									throw new NoSuchElementException();
								}
							}
						};
				}

				private Iterator<K> keysIterator=oKeys.iterator();
			};
	}

	/**
	*	Returns an {@code iterable} enumeration of all the keys of this object.
	*
	*	@return an enumeration of keys.
	*/
	public PsyFormalStream<K> psyKeys();

	/**
	*	Returns an {@code iterable} enumeration of all the values of this object.
	*
	*	@return an enumeration of values.
	*/
	default public PsyFormalStream<V> psyValues()
	{
		return new PsyFormalStream<V>()
			{
				@Override
				public Stream<V> stream()
				{
					return psyKeys().stream().<V>map(oKey->
						{
							try
							{
								return PsyIndexed.this.psyGet(oKey);
							}
							catch(final PsyRangeCheckException|PsyUndefinedException e)
							{
								return null;
							}
						});
				}
			};
	}

	/**
	*	Returns an {@code iterable} enumeration of all the keys and values of this object.
	*
	*	@return an enumeration of entries.
	*/
	public PsyFormalStream<PsyObject> psyEntries();
	/*
	default public PsyFormalStream<PsyObject> psyEntries()
	{
		return new PsyStream(java.util.stream.StreamSupport.<PsyObject>stream(new Iterable()
			{
				@Override
				public java.util.Iterator<PsyObject> iterator()
				{
					return new java.util.Iterator<PsyObject>()
						{
							@Override
							public boolean hasNext()
							{
								return parentIterator.hasNext();
							}

							@Override
							public PsyObject next()
							{
								return (flag=!flag)?
									PsyInteger.valueOf(index++): parentIterator.next();
							}

							private boolean flag=false;

							private int index=0;

							private final java.util.Iterator<PsyObject> parentIterator
								=(java.util.Iterator<PsyObject>)PsyIndexed.this.iterator();

						};
				}
			}.spliterator(),
			false));
	}
	*/
	/*
	default public PsyIterable<PsyObject> psyEntries()
	{

		return new PsyIterable<PsyObject>()
			{
				@Override
				public void psyForAll(final PsyObject oProc)
					throws PsyErrorException
				{
					final coneforest.psylla.Interpreter interpreter
						=(coneforest.psylla.Interpreter)PsyContext.psyCurrentContext();
					final coneforest.psylla.OperandStack ostack=interpreter.operandStack();
					final java.util.Iterator<K> iterator=psyKeys().iterator();
					interpreter.pushLoopLevel();
					interpreter.executionStack().push(new PsyOperator("#forall_continue")
						{
							@Override
							public void action(final coneforest.psylla.Interpreter interpreter1)
								throws PsyErrorException
							{
								if(iterator.hasNext())
								{
									final K oKey=iterator.next();
									ostack.push(oKey);
									ostack.push(psyGet(oKey));
									interpreter1.executionStack().push(this);
									oProc.invoke(interpreter1);
								}
								else
								{
									interpreter1.popLoopLevel();
								}
							}
						});
				}

				@Override
				public java.util.Iterator<PsyObject> iterator()
				{
					return new java.util.Iterator<PsyObject>()
						{
							@Override
							public boolean hasNext()
							{
								return parentIterator.hasNext();
							}

							@Override
							public PsyObject next()
							{
								return (flag=!flag)?
									PsyInteger.valueOf(index++): parentIterator.next();
							}

							private boolean flag=false;

							private int index=0;

							private final java.util.Iterator<PsyObject> parentIterator
								=(java.util.Iterator<PsyObject>)PsyIndexed.this.iterator();

						};
				}
			};
	}
	*/

	/**
	*	Context action of the {@code delete} operator.
	*/
	@OperatorType("delete")
	public static final ContextAction PSY_DELETE
		=ContextAction.<PsyIndexed, PsyObject>ofBiConsumer(PsyIndexed::psyDelete);

	/**
	*	Context action of the {@code entries} operator.
	*/
	@OperatorType("entries")
	public static final ContextAction PSY_ENTRIES
		=ContextAction.<PsyIndexed>ofFunction(PsyIndexed::psyEntries);

	/**
	*	Context action of the {@code extract} operator.
	*/
	@OperatorType("extract")
	public static final ContextAction PSY_EXTRACT
		=ContextAction.<PsyIndexed, PsyObject>ofBiFunction(PsyIndexed::psyExtract);

	/**
	*	Context action of the {@code get} operator.
	*/
	@OperatorType("get")
	public static final ContextAction PSY_GET
		=ContextAction.<PsyIndexed, PsyObject>ofBiFunction(PsyIndexed::psyGet);

	/**
	*	Context action of the {@code getall} operator.
	*/
	@OperatorType("getall")
	public static final ContextAction PSY_GETALL
		=ContextAction.<PsyIndexed, PsyIterable>ofBiFunction(PsyIndexed::psyGetAll);

	/**
	*	Context action of the {@code keys} operator.
	*/
	@OperatorType("keys")
	public static final ContextAction PSY_KEYS
		=ContextAction.<PsyIndexed>ofFunction(PsyIndexed::psyKeys);

	/**
	*	Context action of the {@code known} operator.
	*/
	@OperatorType("known")
	public static final ContextAction PSY_KNOWN
		=ContextAction.<PsyIndexed, PsyObject>ofBiFunction(PsyIndexed::psyKnown);

	/**
	*	Context action of the {@code put} operator.
	*/
	@OperatorType("put")
	public static final ContextAction PSY_PUT
		=ContextAction.<PsyIndexed, PsyObject, PsyObject>ofTriConsumer(PsyIndexed::psyPut);

	/**
	*	Context action of the {@code slice} operator.
	*/
	@OperatorType("slice")
	public static final ContextAction PSY_SLICE
		=ContextAction.<PsyIndexed, PsyIterable>ofBiFunction(PsyIndexed::psySlice);

	/**
	*	Context action of the {@code values} operator.
	*/
	@OperatorType("values")
	public static final ContextAction PSY_VALUES
		=ContextAction.<PsyIndexed>ofFunction(PsyIndexed::psyValues);
}
