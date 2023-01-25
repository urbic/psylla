package coneforest.psylla;

import java.io.PrintWriter;
import java.util.ServiceLoader;
import java.util.spi.ToolProvider;

public class PsyllaToolProviderExample
{
	public static void main(final String[] args)
	{
		var tool=ToolProvider.findFirst("psylla").get();
		tool.run((PrintWriter)null, (PrintWriter)null, "-e", "1 1 10 { ? } for");
	}
}