package coneforest.psylla.core.types;

import coneforest.psylla.Type;
import coneforest.psylla.core.errors.PsyError;
import coneforest.psylla.core.errors.PsyFileNotFound;
import coneforest.psylla.core.errors.PsyIOError;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

@Type("filewriter")
public class PsyFileWriter
	extends PsyWriter
{
	public PsyFileWriter(final String fileName)
		throws PsyError
	{
		super(newFileWriter(fileName));
		/*
		try
		{
			setWriter(new java.io.FileWriter(
					coneforest.psylla.FileSystem.getPath(fileName).toString()));
		}
		catch(final java.io.FileNotFoundException ex)
		{
			throw new PsyFileNotFound();
		}
		catch(final java.io.IOException ex)
		{
			throw new PsyIOError();
		}
		*/
	}

	public PsyFileWriter(final PsyTextual oFileName)
		throws PsyError
	{
		this(oFileName.stringValue());
	}

	private static FileWriter newFileWriter(final String fileName)
		throws PsyError
	{
		try
		{
			return new FileWriter(java.nio.file.Paths.get(fileName).toString());
		}
		catch(final FileNotFoundException ex)
		{
			throw new PsyFileNotFound();
		}
		catch(final IOException ex)
		{
			throw new PsyIOError();
		}
	}

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity11<PsyTextual>
				("filewriter", PsyFileWriter::new),
		};

}
