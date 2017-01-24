package coneforest.psi.core;

public class PsiFileReader
	extends PsiReader
{
	public PsiFileReader(final String fileName)
		throws PsiException
	{
		super(newFileReader(fileName));
	}

	public PsiFileReader(final PsiStringy oFileName)
		throws PsiException
	{
		this(oFileName.stringValue());
	}

	private static java.io.FileReader newFileReader(final String fileName)
		throws PsiException
	{
		try
		{
			return new java.io.FileReader(
					coneforest.psi.FileSystem.getPath(fileName).toString());
		}
		catch(final java.io.FileNotFoundException e)
		{
			throw new PsiFileNotFoundException();
		}
	}
}
