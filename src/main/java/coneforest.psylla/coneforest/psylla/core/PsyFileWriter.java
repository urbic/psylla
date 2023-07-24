package coneforest.psylla.core;

import coneforest.psylla.*;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

@Type("filewriter")
public class PsyFileWriter
	extends PsyWriter
{
	public PsyFileWriter(final String fileName)
		throws PsyFileNotFoundException, PsyIOErrorException
	{
		super(newFileWriter(fileName));
	}

	public PsyFileWriter(final PsyTextual oFileName)
		throws PsyFileNotFoundException, PsyIOErrorException
	{
		this(oFileName.stringValue());
	}

	private static FileWriter newFileWriter(final String fileName)
		throws PsyFileNotFoundException, PsyIOErrorException
	{
		try
		{
			return new FileWriter(Paths.get(fileName).toString());
		}
		catch(final FileNotFoundException ex)
		{
			throw new PsyFileNotFoundException();
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException();
		}
	}

	/**
	*	Context action of the {@code filewriter} operator.
	*/
	@OperatorType("filewriter")
	public static final ContextAction PSY_FILEWRITER
		=ContextAction.<PsyTextual>ofFunction(PsyFileWriter::new);
}
