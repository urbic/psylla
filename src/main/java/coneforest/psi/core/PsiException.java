package coneforest.psi.core;

@coneforest.psi.Type("exception")
abstract public class PsiException
	extends	Exception
	implements PsiObject
{
	public PsiException()
	{
	}

	public PsiException(final PsiObject oEmitter)
	{
		setEmitter(oEmitter);
	}

	abstract public String getName();

	public void setEmitter(final PsiObject oEmitter)
	{
		this.emitter=oEmitter;
	}

	public PsiObject getEmitter()
	{
		return emitter;
	}

	private PsiObject emitter;
}
