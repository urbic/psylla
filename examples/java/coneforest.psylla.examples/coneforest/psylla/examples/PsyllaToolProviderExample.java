package coneforest.psylla.examples;

import java.io.PrintWriter;
import java.util.ServiceLoader;
import java.util.spi.ToolProvider;

public class PsyllaToolProviderExample
{
	public static void main(final String[] args)
	{
		ToolProvider.findFirst("psylla")
				.get()
				.run(System.out, System.err, "-e", "1 1 10 { ? } for");
	}
}
