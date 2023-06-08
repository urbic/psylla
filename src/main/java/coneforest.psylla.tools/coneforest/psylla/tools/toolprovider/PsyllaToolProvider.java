package coneforest.psylla.tools.toolprovider;

import coneforest.psylla.Messages;
import coneforest.psylla.Psylla;
import coneforest.psylla.core.errors.PsyError;
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
		catch(final PsyError e)
		{
			System.err.println(e.getLocalizedMessage());
			return 1;
		}
		catch(final coneforest.clianthus.processor.ProcessingException ex)
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
