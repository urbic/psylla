package coneforest.psylla.examples;

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public interface PsyllaScriptingExample
{
	public static void main(final String[] args)
	{
		final var manager=new ScriptEngineManager();
		final var engine=manager.getEngineByName("psylla");

		System.out.println(engine.get("javax.script.name"));
		System.out.println(engine.get("javax.script.language"));

		try
		{
			engine.eval("0 1 9 { ? } for");
		}
		catch(final ScriptException ex)
		{
			System.err.println(ex.getMessage());
		}
	}
}
