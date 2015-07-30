package coneforest.psi;

public interface PsiAppendable<T extends PsiObject>
	extends PsiObject
{
	@Override
	default public String getTypeName()
	{
		return "appendable";
	}

	public void psiAppend(T obj)
		throws PsiException;

	public void psiAppendAll(PsiIterable<? extends T> iterable)
		throws PsiException;

	public PsiAppendable psiReplicate(PsiInteger count)
		throws PsiException;

	/*
	public final String TYPE="appendable";

	public class Proxy
		implements PsiAppendable<PsiObject>
	{
		public Proxy(PsiObject obj, PsiDictlike metatable)
		{
			this.metatable=metatable;
			metatable.put("self", obj);
		}

		@Override
		public String getTypeName()
		{
			return PsiAppendable.TYPE;
		}

		@Override
		public void psiAppend(PsiObject obj)
		{
		}

		@Override
		public void psiAppendAll(PsiIterable<? extends PsiObject> iterable)
		{
		}

		@Override
		public Proxy psiReplicate(PsiInteger count)
		{
			throw new UnsupportedOperationException();
		}

		private PsiDictlike metatable;
	}
	*/
}
