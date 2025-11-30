package coneforest.psylla.core;

import coneforest.psylla.runtime.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

/**
*	The representation of {@code fileinput}.
*/
@Type("fileinput")
public class PsyFileInput
	extends PsyInput
{
	/**
	*	Context action of the {@code filereader} operator.
	*/
	@OperatorType("fileinput")
	public static final ContextAction PSY_FILEINPUT
		=ContextAction.<PsyTextual>ofFunction(PsyFileInput::new);

	public PsyFileInput(final String fileName)
		throws PsyFileNotFoundException
	{
		super(newFileInput(fileName));
	}

	public PsyFileInput(final PsyTextual oFileName)
		throws PsyFileNotFoundException
	{
		this(oFileName.stringValue());
	}

	private static FileInputStream newFileInput(final String fileName)
		throws PsyFileNotFoundException
	{
		try
		{
			return new FileInputStream(Paths.get(fileName).toString());
		}
		catch(final FileNotFoundException ex)
		{
			throw new PsyFileNotFoundException();
		}
	}
}
