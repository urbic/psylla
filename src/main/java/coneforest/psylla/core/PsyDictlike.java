package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of Ψ-{@code dictlike}, an abstraction of a dictionary.
*
*	@param <V> a type of contained values.
*/
@Type("dictlike")
public interface PsyDictlike<V extends PsyObject>
	extends
		PsyContainer<V>,
		PsyIndexed<PsyStringy, V>
{

	/*
	@Override
	default public void psyForAll(final PsyObject oProc)
		throws PsyException
	{
		final Interpreter interpreter=(Interpreter)PsyContext.psyCurrentContext();
		final OperandStack ostack=interpreter.operandStack();
		final java.util.Iterator<PsyStringy> iterator=psyKeys().iterator();
		interpreter.pushLoopLevel();
		interpreter.executionStack().push(new PsyOperator("#forall_continue")
			{
				@Override
				public void action(final Interpreter interpreter1)
					throws PsyException
				{
					if(iterator.hasNext())
					{
						final PsyStringy oKey=iterator.next();
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
	*/

	public V get(final String key)
		throws PsyException;

	@Override
	default public V psyGet(final PsyStringy oKey)
		throws PsyException
	{
		return get(oKey.stringValue());
	}

	@Override
	default public PsyArraylike<V> psyGetAll(final PsyIterable<PsyStringy> oEnumeration)
		throws PsyException
	{
		final PsyArraylike<V> oResult=(PsyArraylike<V>)new PsyArray();
		for(PsyStringy oKey: oEnumeration)
			oResult.psyAppend(psyGet(oKey));
		return oResult;
	}

	public void put(final String key, final V oValue);

	@Override
	default public void psyPut(final PsyStringy oKey, final V oValue)
	{
		put(oKey.stringValue(), oValue);
	}

	public boolean known(final String key);

	@Override
	default public PsyBoolean psyKnown(final PsyStringy oKey)
	{
		return PsyBoolean.valueOf(known(oKey.stringValue()));
	}

	public void undef(final String key);

	/**
	*	Deletes a key and associated value from this dictionary.
	*
	*	@param oKey a Ψ-{@code stringy} key.
	*/
	default public void psyUndef(final PsyStringy oKey)
	{
		undef(oKey.stringValue());
	}

	@Override
	public PsyIterable<PsyStringy> psyKeys();

	@Override
	default public PsyIterable<V> psyValues()
	{
		return new PsyIterable<V>()
			{
				@Override
				public java.util.Iterator<V> iterator()
				{
					return new java.util.Iterator<V>()
						{
							@Override
							public boolean hasNext()
							{
								return parentIterator.hasNext();
							}

							@Override
							public V next()
							{
								try
								{
									return psyGet(parentIterator.next());
								}
								catch(final PsyException e)
								{
									throw new AssertionError(e);
								}
							}

							private final java.util.Iterator<PsyStringy> parentIterator
								=psyKeys().iterator();
						};
				}
			};
	}

	@Override
	default public void psyDelete(final PsyStringy oKey)
		throws PsyException
	{
		psyUndef(oKey);
	}

	@Override
	default public V psyExtract(final PsyStringy oKey)
		throws PsyException
	{
		V oResult=psyGet(oKey);
		psyUndef(oKey);
		return oResult;
	}

	@Override
	public PsyDictlike<V> psySlice(final PsyIterable<PsyStringy> oEnumeration)
		throws PsyException;

	@Override
	default public String toSyntaxString()
	{
		return "<"+toSyntaxStringHelper(this)+">";
	}

	@Override
	default public PsyIterable<PsyObject> psyEntries()
	{
		return new PsyIterable<PsyObject>()
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
								catch(final PsyException e)
								{
									throw new AssertionError(e);
								}
							}

							private boolean flag=false;

							private PsyName oKey;

							private java.util.Iterator<PsyStringy> parentIterator
								=psyKeys().iterator();

						};
				}
			};
	}

	//@Override
	default public String toSyntaxStringHelper(final PsyLengthy oLengthy)
	{
		StringBuilder sb=new StringBuilder();
		if(length()>0)
		{
			for(PsyObject obj: this.psyEntries())
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

	@Override
	default public void psyClear()
	{
		for(PsyStringy oKey: psyKeys())
			psyUndef(oKey);
	}
}
