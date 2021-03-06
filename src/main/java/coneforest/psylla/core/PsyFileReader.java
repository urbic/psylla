package coneforest.psylla.core;

public class PsyFileReader
	extends PsyReader
{
	public PsyFileReader(final String fileName)
		throws PsyException
	{
		super(newFileReader(fileName));
	}

	public PsyFileReader(final PsyStringy oFileName)
		throws PsyException
	{
		this(oFileName.stringValue());
	}

	private static java.io.FileReader newFileReader(final String fileName)
		throws PsyException
	{
		try
		{
			return new java.io.FileReader(java.nio.file.Paths.get(fileName).toString());
		}
		catch(final java.io.FileNotFoundException e)
		{
			throw new PsyFileNotFoundException();
		}
	}
}
