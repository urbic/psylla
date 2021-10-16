package coneforest.psylla.core;
import coneforest.psylla.*;

@Type("filewriter")
public class PsyFileWriter
	extends PsyWriter
{
	public PsyFileWriter(final String fileName)
		throws PsyException
	{
		super(newFileWriter(fileName));
		/*
		try
		{
			setWriter(new java.io.FileWriter(
					coneforest.psylla.FileSystem.getPath(fileName).toString()));
		}
		catch(final java.io.FileNotFoundException e)
		{
			throw new PsyFileNotFoundException();
		}
		catch(final java.io.IOException e)
		{
			throw new PsyIOErrorException();
		}
		*/
	}

	public PsyFileWriter(final PsyTextual oFileName)
		throws PsyException
	{
		this(oFileName.stringValue());
	}

	private static java.io.FileWriter newFileWriter(final String fileName)
		throws PsyException
	{
		try
		{
			return new java.io.FileWriter(java.nio.file.Paths.get(fileName).toString());
		}
		catch(final java.io.FileNotFoundException e)
		{
			throw new PsyFileNotFoundException();
		}
		catch(final java.io.IOException e)
		{
			throw new PsyIOErrorException();
		}
	}

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity11<PsyTextual>
				("filewriter", PsyFileWriter::new),
		};

}