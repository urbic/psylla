package coneforest.psylla.core;

/**
*	A representation of Ψ-{@code indexed}, a type of the container whose
*	elements are indexed.
*
*	@param <K> a type of keys or indices.
*	@param <V> a type of elements.
*/
@coneforest.psylla.Type("indexed")
public interface PsyIndexed<K extends PsyObject, V extends PsyObject>
	extends PsyObject
{

	/**
	*	Returns a Ψ-{@code boolean} indicating whether given key or index
	*	exists in this object.
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
	*	@throws PsyException when index is out of range.
	*/
	public V psyGet(final K oKey)
		throws PsyException;

	/**
	*	Stores an element with given key or index. In {@link PsyArraylike}
	*	containers replaces existing element. In {@link PsyDictlike}
	*	containers replaces an old or creates a new element associated with
	*	specified key.
	*
	*	@param oKey a key or an index.
	*	@param oValue an element to be stored.
	*	@throws PsyException when key is absent or index is out of range.
	*/
	public void psyPut(final K oKey, final V oValue)
		throws PsyException;

	/**
	*	Deletes a key or index and a value associated with it from this object.
	*
	*	@param oKey a key or an index.
	*	@throws PsyException when key is absent or index is out of range.
	*/
	public void psyDelete(final K oKey)
		throws PsyException;

	public V psyExtract(final K oKey)
		throws PsyException;

	/**
	*	Returns a container of the same type as this object consisting of keys
	*	or indices from given Ψ-{@code iterable} and of associated values.
	*
	*	@param oKeys an enumeration of keys.
	*	@return a container.
	*	@throws PsyException when key is absent or index is out of range.
	*/
	public PsyIndexed<K, V> psySlice(final PsyIterable<K> oKeys)
		throws PsyException;

	public PsyArraylike<V> psyGetAll(final PsyIterable<K> oKeys)
		throws PsyException;

	/**
	*	Returns a Ψ-{@code iterable} enumeration of all the keys of this
	*	object.
	*
	*	@return an enumeration of keys.
	*/
	public PsyIterable<K> psyKeys();

	/**
	*	Returns a Ψ-{@code iterable} enumeration of all the values of this
	*	object.
	*
	*	@return an enumeration of values.
	*/
	public PsyIterable<V> psyValues();

	/**
	*	Returns a Ψ-{@code iterable} enumeration of all the keys and values of
	*	this object.
	*
	*	@return an enumeration of entries.
	*/
	public PsyIterable<PsyObject> psyEntries();
	/*
	default public PsyIterable<PsyObject> psyEntries()
	{

		return new PsyIterable<PsyObject>()
			{
				@Override
				public void psyForAll(final PsyObject oProc)
					throws PsyException
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
								throws PsyException
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
}
