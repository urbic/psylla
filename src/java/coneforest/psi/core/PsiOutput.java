package coneforest.psi.core;

// TODO
abstract
public class PsiOutput
	implements
		PsiCloseable,
		PsiFlushable,
		PsiWritable
{
	public PsiOutput(final java.io.OutputStream output)
	{
		setOutput(output);
	}

	public String getTypeName()
	{
		return "output";
	}

	public void setOutput(final java.io.OutputStream output)
	{
		this.output=output;
	}

	public java.io.OutputStream getOutput()
	{
		return output;
	}

	public void write(final int b)
		throws PsiException
	{
		try
		{
			output.write(b);
		}
		catch(java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	public void write(final PsiInteger oCharacter)
		throws PsiException
	{
		write(oCharacter.intValue());
	}

	@Override
	public void psiFlush()
		throws PsiException
	{
		try
		{
			output.flush();
		}
		catch(java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	@Override
	public void psiClose()
		throws PsiException
	{
		try
		{
			output.close();
		}
		catch(java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	private java.io.OutputStream output;
}
