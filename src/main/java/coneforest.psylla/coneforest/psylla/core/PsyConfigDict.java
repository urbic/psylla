package coneforest.psylla.core;

import coneforest.psylla.runtime.Config;
import java.util.Properties;

public final class PsyConfigDict
	extends PsyModule
{
	public PsyConfigDict()
	{
		for(final var name: Config.stringPropertyNames())
			put(name, new PsyString(Config.getProperty(name)));

		final Properties systemProperties=System.getProperties();
		for(final var name: systemProperties.stringPropertyNames())
			put(name, new PsyString(systemProperties.getProperty(name)));
	}
}
