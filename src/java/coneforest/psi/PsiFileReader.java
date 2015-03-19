package coneforest.psi;

public class PsiFileReader
	extends PsiReader
{
	public PsiFileReader(String name)
		throws PsiException
	{
		try
		{
			setReader(new java.io.FileReader(FileSystem.fileNameToNative(name)));
		}
		catch(java.io.FileNotFoundException e)
		{
			throw new PsiException("filenotfound");
		}
	}

	public PsiFileReader(PsiStringlike name)
		throws PsiException
	{
		this(name.getString());
	}

	static
	{
		TypeRegistry.put("filereader", PsiFileReader.class);
	}
}
