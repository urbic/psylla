package coneforest.psylla.core.types;

import coneforest.psylla.Type;
import coneforest.psylla.core.errors.PsyError;

/**
*	A representation of {@code indexed}, a type of the container whose elements
*	are indexed.
*
*	@param <K> a type of keys or indices.
*	@param <V> a type of elements.
*/
@Type("indexed")
public interface PsyIndexed<K extends PsyObject, V extends PsyObject>
	extends PsyObject
{

	/**
	*	Returns a {@code boolean} indicating whether given key or index exists
	*	in this object.
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
	*	@throws PsyError when index is out of range.
	*/
	public V psyGet(final K oKey)
		throws PsyError;

	/**
	*	Stores an element with given key or index. In {@link PsyFormalArray}
	*	containers replaces existing element. In {@link PsyFormalDict}
	*	containers replaces an old or creates a new element associated with
	*	specified key.
	*
	*	@param oKey a key or an index.
	*	@param oValue an element to be stored.
	*	@throws PsyError when key is absent or index is out of range.
	*/
	public void psyPut(final K oKey, final V oValue)
		throws PsyError;

	/**
	*	Deletes a key or index and a value associated with it from this object.
	*
	*	@param oKey a key or an index.
	*	@throws PsyError when key is absent or index is out of range.
	*/
	public void psyDelete(final K oKey)
		throws PsyError;

	public V psyExtract(final K oKey)
		throws PsyError;

	/**
	*	Returns a container of the same type as this object consisting of keys
	*	or indices from given {@code iterable} and of associated values.
	*
	*	@param oKeys an enumeration of keys.
	*	@return a container.
	*	@throws PsyError when key is absent or index is out of range.
	*/
	public PsyIndexed<K, V> psySlice(final PsyIterable<K> oKeys)
		throws PsyError;

	public PsyFormalArray<V> psyGetAll(final PsyIterable<K> oKeys)
		throws PsyError;

	/**
	*	Returns an {@code iterable} enumeration of all the keys of this object.
	*
	*	@return an enumeration of keys.
	*/
	public PsyFormalStream<K> psyKeys();

	/**
	*	Returns an {@code iterable} enumeration of all the values of this
	*	object.
	*
	*	@return an enumeration of values.
	*/
	default public PsyFormalStream<V> psyValues()
	{
		return new PsyFormalStream<V>()
			{
				@Override
				public java.util.stream.Stream<V> stream()
				{
					return psyKeys().stream().<V>map(oKey->
						{
							try
							{
								return PsyIndexed.this.psyGet(oKey);
							}
							catch(final PsyError e)
							{
								return null;
							}
						});
				}
			};
	}

	/**
	*	Returns an {@code iterable} enumeration of all the keys and values of
	*	this object.
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
					throws PsyError
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
								throws PsyError
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

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity20<PsyIndexed, PsyObject>("delete", PsyIndexed::psyDelete),
			new PsyOperator.Arity11<PsyIndexed>("entries", PsyIndexed::psyEntries),
			new PsyOperator.Arity21<PsyIndexed, PsyObject>("extract", PsyIndexed::psyExtract),
			new PsyOperator.Arity21<PsyIndexed, PsyObject>("get", PsyIndexed::psyGet),
			new PsyOperator.Arity21<PsyIndexed, PsyIterable>("getall", PsyIndexed::psyGetAll),
			new PsyOperator.Arity11<PsyIndexed>("keys", PsyIndexed::psyKeys),
			new PsyOperator.Arity21<PsyIndexed, PsyObject>("known", PsyIndexed::psyKnown),
			new PsyOperator.Arity30<PsyIndexed, PsyObject, PsyObject>("put", PsyIndexed::psyPut),
			new PsyOperator.Arity21<PsyIndexed, PsyIterable>("slice", PsyIndexed::psySlice),
			new PsyOperator.Arity11<PsyIndexed>("values", PsyIndexed::psyValues),
		};

}
