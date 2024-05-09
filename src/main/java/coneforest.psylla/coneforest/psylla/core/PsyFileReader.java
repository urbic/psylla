package coneforest.psylla.core;

import coneforest.psylla.runtime.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Paths;

/**
*	The representation of {@code filereader}.
*/
@Type("filereader")
public class PsyFileReader
	extends PsyReader
{
	public PsyFileReader(final String fileName)
		throws PsyFileNotFoundException
	{
		super(newFileReader(fileName));
	}

	public PsyFileReader(final PsyTextual oFileName)
		throws PsyFileNotFoundException
	{
		this(oFileName.stringValue());
	}

	private static FileReader newFileReader(final String fileName)
		throws PsyFileNotFoundException
	{
		try
		{
			return new FileReader(Paths.get(fileName).toString());
		}
		catch(final FileNotFoundException ex)
		{
			throw new PsyFileNotFoundException();
		}
	}

	/**
	*	Context action of the {@code filereader} operator.
	*/
	@OperatorType("filereader")
	public static final ContextAction PSY_FILEREADER
		=ContextAction.<PsyTextual>ofFunction(PsyFileReader::new);
}
