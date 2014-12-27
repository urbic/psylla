package coneforest.psi;

abstract public class PsiAbstractDictionary<V extends PsiObject>
	extends PsiObject
	implements PsiDictionarylike<V>
{
	@Override
	public String getTypeName()
	{
		return "dict";
	}

	@Override
	public V psiGet(PsiStringlike key)
		throws PsiException
	{
		return psiGet(key.getString());
	}

	@Override
	public void psiPut(PsiStringlike key, V obj)
	{
		psiPut(key.getString(), obj);
	}

	@Override
	public PsiBoolean psiKnown(PsiStringlike key)
	{
		return psiKnown(key.getString());
	}

	@Override
	abstract public void psiUndef(String keyString);

	@Override
	public void psiUndef(PsiStringlike key)
	{
		psiUndef(key.getString());
	}

	@Override
	public V psiDelete(PsiStringlike key)
		throws PsiException
	{
		V result=psiGet(key);
		psiUndef(key);
		return result;
	}

	@Override
	public PsiSet psiKeys()
	{
		PsiSet set=new PsiSet();
		for(java.util.Map.Entry<String, V> entry: this)
			set.psiAppend(new PsiName(entry.getKey()));
		return set;
	}

	@Override
	public PsiArray psiValues()
	{
		PsiArray values=new PsiArray();
		for(java.util.Map.Entry<String, V> entry: this)
			values.psiAppend(entry.getValue());
		return values;
	}

	@Override
	public void psiClear()
	{
		for(java.util.Map.Entry<String, V> entry: this)
			psiUndef(entry.getKey());
	}

	/*
	@Override
	public void psiForAll(PsiObject obj, Interpreter interpreter)
		
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final int loopLevel=interpreter.pushLoopLevel();
		for(java.util.Map.Entry<String, V> entry: this)
		{
			if(interpreter.getExitFlag())
				break;
			opstack.push(new PsiName(entry.getKey()));
			opstack.push(entry.getValue());
			obj.invoke(interpreter);
			interpreter.handleExecutionStack(loopLevel);
		}
		interpreter.popLoopLevel();
		interpreter.setExitFlag(false);
	}
	*/

	@Override
	public String toString()
	{
		return toString(this);
	}

	public String toString(PsiLengthy lengthy)
	{
		StringBuilder sb=new StringBuilder("<");
		if(length()>0)
		{
			for(java.util.Map.Entry<String, ? extends PsiObject> entry: this)
			{
				sb.append('/');
				sb.append(entry.getKey());
				sb.append(' ');
				PsiObject obj=entry.getValue();
				if(obj instanceof PsiLengthy)
					sb.append(obj==lengthy? "-"+obj.getTypeName()+"-": ((PsiLengthy)obj).toString(this));
				else
					sb.append(obj.toString());
				sb.append(' ');
			}
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append('>');
		return sb.toString();
	}
}
