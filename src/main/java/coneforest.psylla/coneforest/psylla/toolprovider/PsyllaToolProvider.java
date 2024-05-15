package coneforest.psylla.toolprovider;

import coneforest.clianthus.processor.ProcessingException;
import coneforest.psylla.runtime.*;
import coneforest.psylla.core.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.spi.ToolProvider;

public class PsyllaToolProvider
	implements ToolProvider
{
	@Override
	public String name()
	{
		return "psylla";
	}

	@Override
	public int run(final PrintWriter outputWriter, final PrintWriter errorWriter,
			final String... args)
	{
		try
		{
			Psylla.launch(outputWriter, errorWriter, args);
		}
		catch(final PsyErrorException e)
		{
			System.err.println(e.getLocalizedMessage());
			return 1;
		}
		catch(final ProcessingException ex)
		{
			System.err.println(ex.getLocalizedMessage());
			System.err.println(Messages.getString("useHelpOption"));
			return 1;
		}
		catch(final FileNotFoundException ex)
		{
			System.out.println(Messages.format("badScript", ex.getLocalizedMessage()));
			return 1;
		}
		return 0;
	}
}
