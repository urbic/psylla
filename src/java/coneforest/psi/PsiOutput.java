package coneforest.psi;

public class PsiOutput
	extends PsiObject
	implements PsiCloseable, PsiFlushable
{
	public PsiOutput()
	{
	}

	public PsiOutput(java.io.OutputStream output)
	{
		setOutput(output);
	}

	public String getTypeName()
	{
		return "output";
	}

	public String toString()
	{
		return "-output-";
	}

	public void setOutput(java.io.OutputStream output)
	{
		this.output=output;
	}

	public java.io.OutputStream getOutput()
	{
		return output;
	}

	public void write(int b)
		throws PsiException
	{
		try
		{
			output.write(b);
		}
		catch(java.io.IOException e)
		{
			throw new PsiException("ioerror");
		}
	}

	public void write(PsiInteger oByte)
		throws PsiException
	{
		write(oByte.getValue().intValue());
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
			throw new PsiException("ioerror");
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
			throw new PsiException("ioerror");
		}
	}

	private java.io.OutputStream output;
}
