package coneforest.psylla.engine;

import java.util.List;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;

/**
*	The Psylla language scripting engine factory.
*/
public class PsyllaScriptEngineFactory
	implements ScriptEngineFactory
{
	/**
	*	@return a string {@code "Psylla"}.
	*/
	@Override
	public String getEngineName()
	{
		return "Psylla";
	}

	/**
	*	Returns an engine.
	*
	*	@return an engine.
	*/
	@Override
	public ScriptEngine getScriptEngine()
	{
		return new PsyllaScriptEngine(this);
	}

	/**
	*	@return a list consisting of single string {@code "psylla"}.
	*/
	@Override
	public List<String> getNames()
	{
		return List.<String>of("psylla");
	}

	@Override
	public String getProgram(final String... statements)
	{
		final var sb=new StringBuilder();
		for(final var statement: statements)
		{
			sb.append(statement);
			sb.append('\n');
		}
		return sb.toString();
	}

	@Override
	public String getOutputStatement(final String toDisplay)
		throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException(getClass().getName()+".getOutputStatement not supported");
	}

	@Override
	public String getMethodCallSyntax(final String obj, final String m, final String... args)
	{
		throw new UnsupportedOperationException(getClass().getName()+".getMethodCalSyntax not supported");
	}

	@Override
	public String getParameter(final String key)
	{
		if(key.equals(ScriptEngine.ENGINE))
			return getEngineName();
		if(key.equals(ScriptEngine.ENGINE_VERSION))
			return getEngineVersion();
		if(key.equals(ScriptEngine.NAME))
			return getEngineName();
		if(key.equals(ScriptEngine.LANGUAGE))
			return getLanguageName();
		if(key.equals(ScriptEngine.LANGUAGE_VERSION))
			return getLanguageVersion();
		if(key.equals("THREADING"))
			return "MULTITHREADED";	// TODO
		return null;
	}

	@Override
	public String getLanguageVersion()
	{
		return coneforest.psylla.runtime.Version.getVersion();
	}

	@Override
	public String getEngineVersion()
	{
		return coneforest.psylla.runtime.Version.getVersion();
	}

	/**
	*	Returns a name of a language.
	*
	*	@return the string {@code "Psylla"}.
	*/
	@Override
	public String getLanguageName()
	{
		return "Psylla";
	}

	/**
	*	@return a list consisting of single string {@code "application/x-psylla"}.
	*/
	@Override
	public List<String> getMimeTypes()
	{
		if(mimeTypes==null)
			mimeTypes=List.<String>of("application/x-psylla");
		return mimeTypes;
	}

	/**
	*	@return a list consisting of single string {@code "psy"}.
	*/
	@Override
	public List<String> getExtensions()
	{
		if(extensions==null)
			extensions=List.<String>of("psy");
		return extensions;
	}

	private List<String> mimeTypes;
	private List<String> extensions;
}
