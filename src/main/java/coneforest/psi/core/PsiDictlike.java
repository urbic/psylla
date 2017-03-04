package coneforest.psi.core;
import coneforest.psi.*;

/**
*	A representation of Ψ-{@code dictlike}, an abstraction of a dictionary.
*
*	@param <V> a type of contained values.
*/
@Type("dictlike")
public interface PsiDictlike<V extends PsiObject>
	extends
		PsiContainer<V>,
		PsiIndexed<PsiStringy, V>
{

	/*
	@Override
	default public void psiForAll(final PsiObject oProc)
		throws PsiException
	{
		final Interpreter interpreter=(Interpreter)PsiContext.psiCurrentContext();
		final OperandStack ostack=interpreter.operandStack();
		final java.util.Iterator<PsiStringy> iterator=psiKeys().iterator();
		interpreter.pushLoopLevel();
		interpreter.executionStack().push(new PsiOperator("#forall_continue")
			{
				@Override
				public void action(final Interpreter interpreter1)
					throws PsiException
				{
					if(iterator.hasNext())
					{
						final PsiStringy oKey=iterator.next();
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
	*/

	public V get(final String key)
		throws PsiException;

	@Override
	default public V psiGet(final PsiStringy oKey)
		throws PsiException
	{
		return get(oKey.stringValue());
	}

	@Override
	default public PsiArraylike<V> psiGetAll(final PsiIterable<PsiStringy> oEnumeration)
		throws PsiException
	{
		final PsiArraylike<V> oResult=(PsiArraylike<V>)new PsiArray();
		for(PsiStringy oKey: oEnumeration)
			oResult.psiAppend(psiGet(oKey));
		return oResult;
	}

	public void put(final String key, final V oValue);

	@Override
	default public void psiPut(final PsiStringy oKey, final V oValue)
	{
		put(oKey.stringValue(), oValue);
	}

	public boolean known(final String key);

	@Override
	default public PsiBoolean psiKnown(final PsiStringy oKey)
	{
		return PsiBoolean.valueOf(known(oKey.stringValue()));
	}

	public void undef(final String key);

	/**
	*	Deletes a key and associated value from this dictionary.
	*
	*	@param oKey a Ψ-{@code stringy} key.
	*/
	default public void psiUndef(final PsiStringy oKey)
	{
		undef(oKey.stringValue());
	}

	@Override
	public PsiIterable<PsiStringy> psiKeys();

	@Override
	default public PsiIterable<V> psiValues()
	{
		return new PsiIterable<V>()
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
									return psiGet(parentIterator.next());
								}
								catch(final PsiException e)
								{
									throw new AssertionError(e);
								}
							}

							private final java.util.Iterator<PsiStringy> parentIterator
								=psiKeys().iterator();
						};
				}
			};
	}

	@Override
	default public void psiDelete(final PsiStringy oKey)
		throws PsiException
	{
		psiUndef(oKey);
	}

	@Override
	default public V psiExtract(final PsiStringy oKey)
		throws PsiException
	{
		V oResult=psiGet(oKey);
		psiUndef(oKey);
		return oResult;
	}

	@Override
	public PsiDictlike<V> psiSlice(final PsiIterable<PsiStringy> oEnumeration)
		throws PsiException;

	@Override
	default public String toSyntaxString()
	{
		return "<"+toSyntaxStringHelper(this)+">";
	}

	@Override
	default public PsiIterable<PsiObject> psiEntries()
	{
		return new PsiIterable<PsiObject>()
			{
				@Override
				public java.util.Iterator<PsiObject> iterator()
				{
					return new java.util.Iterator<PsiObject>()
						{
							@Override
							public boolean hasNext()
							{
								return parentIterator.hasNext() || flag;
							}

							@Override
							public PsiObject next()
							{
								try
								{
									return (flag=!flag)?
										(oKey=(PsiName)parentIterator.next()): psiGet(oKey);
								}
								catch(final PsiException e)
								{
									throw new AssertionError(e);
								}
							}

							private boolean flag=false;

							private PsiName oKey;

							private java.util.Iterator<PsiStringy> parentIterator
								=psiKeys().iterator();

						};
				}
			};
	}

	//@Override
	default public String toSyntaxStringHelper(final PsiLengthy oLengthy)
	{
		StringBuilder sb=new StringBuilder();
		if(length()>0)
		{
			for(PsiObject obj: this.psiEntries())
			{
				if(obj instanceof PsiLengthy)
					sb.append(obj==oLengthy? "-"+obj.typeName()+"-": ((PsiLengthy)obj).toSyntaxString());
				else
					sb.append(obj.toSyntaxString());
				sb.append(' ');
			}
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}

	@Override
	default public void psiClear()
	{
		for(PsiStringy oKey: psiKeys())
			psiUndef(oKey);
	}
}
