package coneforest.psylla.core;
import coneforest.psylla.*;

@Type("filereader")
public class PsyFileReader
	extends PsyReader
{
	public PsyFileReader(final String fileName)
		throws PsyErrorException
	{
		super(newFileReader(fileName));
	}

	public PsyFileReader(final PsyTextual oFileName)
		throws PsyErrorException
	{
		this(oFileName.stringValue());
	}

	private static java.io.FileReader newFileReader(final String fileName)
		throws PsyErrorException
	{
		try
		{
			return new java.io.FileReader(java.nio.file.Paths.get(fileName).toString());
		}
		catch(final java.io.FileNotFoundException ex)
		{
			throw new PsyFileNotFoundException();
		}
	}

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity11<PsyTextual>
				("filereader", PsyFileReader::new),
		};

}
