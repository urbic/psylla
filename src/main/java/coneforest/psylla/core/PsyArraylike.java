package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of Ψ-{@code arraylike}, an abstraction of an array
*	composed of Ψ-{@code object}s.
*
*	@param <T> a type of contained objects.
*/
@Type("arraylike")
public interface PsyArraylike<T extends PsyObject>
	extends
		PsyAppendable<T>,
		PsyContainer<T>,
		PsyIndexed<PsyInteger, T>
{

	@Override
	public PsyArraylike<T> psyClone();

	default public PsyArraylike<T> psyReverse()
		throws PsyException
	{
		PsyArraylike<T> result=psyClone();
		int length=result.length();
		for(int i=0; i<(int)(length/2); i++)
		{
			T o=result.get(i);
			result.put(i, result.get(length-1-i));
			result.put(length-1-i, o);
		}
		return result;
	}

	@Override
	default public PsyBoolean psyKnown(final PsyInteger oIndex)
	{
		long index=oIndex.longValue();
		return PsyBoolean.valueOf(index>=0 && index<length());
	}

	public T get(final int index)
		throws PsyException;

	@Override
	default public T psyGet(final PsyInteger oIndex)
		throws PsyException
	{
		return get(oIndex.intValue());
	}

	public PsyArraylike<T> psyGetInterval(final PsyInteger oIndex, final PsyInteger oLength)
		throws PsyException;

	public void put(final int index, final T o)
		throws PsyException;

	@Override
	default public void psyPut(final PsyInteger oIndex, final T o)
		throws PsyException
	{
		put(oIndex.intValue(), o);
	}

	/**
	*	Inserts the specified Ψ-{@code object} into this array at the position
	*	specified by a given index.
	*
	*	@param index a Ψ-{@code integer} index.
	*	@param o a Ψ-{@code object}.
	*	@throws PsyException when an error occurs.
	*/
	public void insert(final int index, final T o)
		throws PsyException;

	/**
	*	Inserts the specified Ψ-{@code object} into this array at the position
	*	specified by a given Ψ-{@code integer} index.
	*
	*	@param oIndex a Ψ-{@code integer} index.
	*	@param o a Ψ-{@code object}.
	*	@throws PsyException when an error occurs.
	*/
	default public void psyInsert(final PsyInteger oIndex, final T o)
		throws PsyException
	{
		insert(oIndex.intValue(), o);
	}

	default public void psyInsertAll(final PsyInteger oIndex, final PsyIterable<? extends T> oEnumeration)
		throws PsyException
	{
		int index=oIndex.intValue();
		for(final T o: (this!=oEnumeration? oEnumeration: (PsyIterable<? extends T>)psyClone()))
			insert(index++, o);
	}

	/**
	*	Inserts the specified Ψ-{@code object} into this array at the
	*	beginning.
	*
	*	@param o a Ψ-{@code object}.
	*	@throws PsyException when an error occurs.
	*/
	default public void psyPrepend(final T o)
		throws PsyException
	{
		insert(0, o);
	}

	default public T psyPreChop()
		throws PsyException
	{
		return extract(0);
	}

	default public T psyPostChop()
		throws PsyException
	{
		return extract(length()-1);
	}

	default public void psyPrependAll(final PsyIterable<? extends T> oEnumeration)
		throws PsyException
	{
		psyInsertAll(PsyInteger.ZERO, oEnumeration);
	}

	@Override
	default public PsyArraylike<T> psyReplicate(final PsyInteger oCount)
		throws PsyException
	{
		long count=oCount.longValue();
		if(count<0)
			throw new PsyRangeCheckException();
		if(count*length()>Integer.MAX_VALUE)
			throw new PsyLimitCheckException();
		PsyArraylike<T> oResult=(PsyArraylike<T>)psyNewEmpty();
		while(count-->0)
			oResult.psyAppendAll(this);
		return oResult;
	}

	default public void psyPutInterval(final PsyInteger oIndex, final PsyIterable<? extends T> oEnumeration)
		throws PsyException
	{
		int index=oIndex.intValue();
		if(index<0
			||
			oEnumeration instanceof PsyLengthy
			&& index+((PsyLengthy)oEnumeration).length()>=length())
			throw new PsyRangeCheckException();
		for(final T o: oEnumeration)
		{
			put((int)index++, o);
			if(index==length())
				break;
		}
	}

	@Override
	default public void psyDelete(final PsyInteger oIndex)
		throws PsyException
	{
		delete(oIndex.intValue());
	}

	public void delete(int index)
		throws PsyException;

	@Override
	default public T psyExtract(final PsyInteger oIndex)
		throws PsyException
	{
		return extract(oIndex.intValue());
	}

	@Override
	default public PsyArraylike<T> psyGetAll(final PsyIterable<PsyInteger> oIndices)
		throws PsyException
	{
		final PsyArraylike<T> oResult=(PsyArraylike<T>)psyNewEmpty();
		for(final PsyInteger oIndex: oIndices)
			oResult.psyAppend(psyGet(oIndex));
		return oResult;
	}

	public void psySetLength(final PsyInteger oLength)
		throws PsyException;

	public T extract(final int index)
		throws PsyException;

	public PsyArraylike<T> psyExtractInterval(final PsyInteger oIndex, final PsyInteger oCount)
		throws PsyException;

	@Override
	public PsyArraylike<T> psySlice(final PsyIterable<PsyInteger> oIndices)
		throws PsyException;

	@Override
	default public PsyIterable<PsyInteger> psyKeys()
	{
		return new PsyIterable<PsyInteger>()
			{
				@Override
				public java.util.Iterator<PsyInteger> iterator()
				{
					return new java.util.Iterator<PsyInteger>()
						{
							@Override
							public boolean hasNext()
							{
								return index<length();
							}

							@Override
							public PsyInteger next()
							{
								return PsyInteger.valueOf(index++);
							}

							private int index=0;
						};
				}
			};
	}

	@Override
	default public PsyIterable<T> psyValues()
	{
		return this;
	}

	@Override
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
					final java.util.Iterator<PsyInteger> iterator=psyKeys().iterator();
					interpreter.pushLoopLevel();
					interpreter.executionStack().push(new PsyOperator("#forall_continue")
						{
							@Override
							public void action(final coneforest.psylla.Interpreter interpreter1)
								throws PsyException
							{
								if(iterator.hasNext())
								{
									final PsyInteger oIndex=iterator.next();
									ostack.push(oIndex);
									ostack.push(psyGet(oIndex));
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
								=(java.util.Iterator<PsyObject>)PsyArraylike.this.iterator();

						};
				}
			};
	}

	@Override
	default public PsyInteger psyCount()
	{
		return psyLength();
	}

	@Override
	default public String toSyntaxString()
	{
		return "["+toSyntaxStringHelper(this)+"]";
	}

	default public String toSyntaxStringHelper(final PsyLengthy oLengthy)
	{
		final var sb=new StringBuilder();
		if(length()>0)
		{
			for(final var obj: this)
			{
				if(obj instanceof PsyLengthy)
					sb.append(obj==oLengthy? "-"+obj.typeName()+"-": ((PsyLengthy)obj).toSyntaxString());
				else
					sb.append(obj.toSyntaxString());
				sb.append(' ');
			}
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}
}
