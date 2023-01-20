package coneforest.psylla.test;

import java.io.PrintWriter;
import java.util.ServiceLoader;
import java.util.spi.ToolProvider;

public class PsyllaToolProviderTest
{
	public static void main(final String[] args)
	{
		var tool=ToolProvider.findFirst("psylla").get();
		tool.run((PrintWriter)null, (PrintWriter)null, "-e", "1 1 10 { ? } for");
	}
}
