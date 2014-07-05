package coneforest.psi;

public class PsiInput extends PsiObject
{
	public PsiInput()
	{
	}

	public PsiInput(java.io.InputStream input)
	{
		setInput(input);
	}

	public String getTypeName()
	{
		return "input";
	}

	public String toString()
	{
		return "-input-";
	}

	public void setInput(java.io.InputStream input)
	{
		this.input=input;
	}

	public java.io.InputStream getInput()
	{
		return input;
	}

	public int read()
		throws PsiException
	{
		try
		{
			return input.read();
		}
		catch(java.io.IOException e)
		{
			throw new PsiException("ioerror");
		}
	}

	private java.io.InputStream input;
}
