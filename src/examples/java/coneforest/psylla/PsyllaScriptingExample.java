package coneforest.psylla;
import javax.script.*;

public class PsyllaScriptingExample
{
	public static void main(String[] args)
	{
		ScriptEngineManager manager=new ScriptEngineManager();
		ScriptEngine engine=manager.getEngineByName("psylla");

		System.out.println(engine.get("javax.script.name"));
		System.out.println(engine.get("javax.script.language"));

		try
		{
			engine.eval("0 1 9 { ? } for");
		}
		catch(final javax.script.ScriptException e)
		{
			System.err.println(e.getMessage());
		}
	}
}
