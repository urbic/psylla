package coneforest.psi.core;

abstract public class PsiException
	extends	Exception
	implements PsiObject
{
	public PsiException()
	{
	}

	public PsiException(PsiObject oEmitter)
	{
		setEmitter(oEmitter);
	}

	@Override
	public String getTypeName()
	{
		return "exception";
	}

	abstract public String getName();

	public void setEmitter(PsiObject oEmitter)
	{
		this.emitter=oEmitter;
	}

	public PsiObject getEmitter()
	{
		return emitter;
	}

	private PsiObject emitter;
}
