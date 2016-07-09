package coneforest.psi.core;

/**
*	A representation of Ψ-{@code indexed}, a type of the container whose
*	elements are indexed.
*
*	@param <K> a type of keys or indices.
*	@param <V> a type of elements.
*/
public interface PsiIndexed<K extends PsiObject, V extends PsiObject>
	extends PsiObject
{
	/**
	*	@return a string {@code "indexed"}.
	*/
	@Override
	default public String typeName()
	{
		return "indexed";
	}

	/**
	*	Returns a Ψ-{@code boolean} indicating whether given key or index
	*	exists in this object.
	*
	*	@param oKey a key or an index.
	*	@return a result.
	*/
	public PsiBoolean psiKnown(final K oKey);

	/**
	*	Returns the element with given key or index.
	*
	*	@param oKey a key or an index.
	*	@return an element.
	*	@throws PsiException when index is out of range.
	*/
	public V psiGet(final K oKey)
		throws PsiException;

	/**
	*	Stores an element with given key or index. In {@link PsiArraylike}
	*	containers replaces existing element. In {@link PsiDictlike}
	*	containers replaces an old or creates a new element associated with
	*	specified key.
	*
	*	@param oKey a key or an index.
	*	@param oValue an element to be stored.
	*	@throws PsiException when key is absent or index is out of range.
	*/
	public void psiPut(final K oKey, final V oValue)
		throws PsiException;

	/**
	*	Deletes a key or index and a value associated with it from this object.
	*
	*	@param oKey a key or an index.
	*	@throws PsiException when key is absent or index is out of range.
	*/
	public void psiDelete(final K oKey)
		throws PsiException;

	public V psiExtract(final K oKey)
		throws PsiException;

	/**
	*	Returns a container of the same type as this object consisting of keys
	*	or indices from given Ψ-{@code iterable} and of associated values.
	*
	*	@param oKeys an enumeration of keys.
	*	@return a container.
	*	@throws PsiException when key is absent or index is out of range.
	*/
	public PsiIndexed<K, V> psiSlice(final PsiIterable<K> oKeys)
		throws PsiException;

	public PsiArraylike<V> psiGetAll(final PsiIterable<K> oKeys)
		throws PsiException;

	/**
	*	Returns a Ψ-{@code iterable} enumeration of all the keys of this
	*	object.
	*
	*	@return an enumeration of keys.
	*/
	public PsiIterable<K> psiKeys();

	/**
	*	Returns a Ψ-{@code iterable} enumeration of all the values of this
	*	object.
	*
	*	@return an enumeration of values.
	*/
	public PsiIterable<V> psiValues();

	/**
	*	Returns a Ψ-{@code iterable} enumeration of all the keys and values of
	*	this object.
	*
	*	@return an enumeration of entries.
	*/
	public PsiIterable<PsiObject> psiEntries();
	/*
	default public PsiIterable<PsiObject> psiEntries()
	{

		return new PsiIterable<PsiObject>()
			{
				@Override
				public void psiForAll(final PsiObject oProc)
					throws PsiException
				{
					final coneforest.psi.Interpreter interpreter
						=(coneforest.psi.Interpreter)PsiContext.psiCurrentContext();
					final coneforest.psi.OperandStack ostack=interpreter.operandStack();
					final java.util.Iterator<K> iterator=psiKeys().iterator();
					interpreter.pushLoopLevel();
					interpreter.executionStack().push(new PsiOperator("#forall_continue")
						{
							@Override
							public void action(final coneforest.psi.Interpreter interpreter1)
								throws PsiException
							{
								if(iterator.hasNext())
								{
									final K oKey=iterator.next();
									ostack.push(oKey);
									ostack.push(psiGet(oKey));
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
				public java.util.Iterator<PsiObject> iterator()
				{
					return new java.util.Iterator<PsiObject>()
						{
							@Override
							public boolean hasNext()
							{
								return parentIterator.hasNext();
							}

							@Override
							public PsiObject next()
							{
								return (flag=!flag)?
									PsiInteger.valueOf(index++): parentIterator.next();
							}

							private boolean flag=false;

							private int index=0;

							private final java.util.Iterator<PsiObject> parentIterator
								=(java.util.Iterator<PsiObject>)PsiIndexed.this.iterator();

						};
				}
			};
	}
	*/
}
