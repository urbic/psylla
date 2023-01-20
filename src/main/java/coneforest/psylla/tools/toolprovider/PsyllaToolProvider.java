package coneforest.psylla.tools.toolprovider;

import coneforest.psylla.Psylla;
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
	public int run(final PrintWriter out, final PrintWriter err, final String[] args)
	{
		Psylla.main(args);
		return 0;
	}
}
