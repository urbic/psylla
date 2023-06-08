package coneforest.psylla.core.types;

import coneforest.psylla.Type;
import coneforest.psylla.core.errors.PsyError;
import coneforest.psylla.core.errors.PsyFileNotFound;
import java.io.FileNotFoundException;
import java.io.FileReader;

@Type("filereader")
public class PsyFileReader
	extends PsyReader
{
	public PsyFileReader(final String fileName)
		throws PsyError
	{
		super(newFileReader(fileName));
	}

	public PsyFileReader(final PsyTextual oFileName)
		throws PsyError
	{
		this(oFileName.stringValue());
	}

	private static FileReader newFileReader(final String fileName)
		throws PsyError
	{
		try
		{
			return new FileReader(java.nio.file.Paths.get(fileName).toString());
		}
		catch(final FileNotFoundException ex)
		{
			throw new PsyFileNotFound();
		}
	}

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity11<PsyTextual>
				("filereader", PsyFileReader::new),
		};
}
