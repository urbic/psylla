module coneforest.psylla
{
	exports coneforest.psylla;
	exports coneforest.psylla.core;
	exports coneforest.psylla.engine;

	requires coneforest.clianthus;
	requires java.scripting;

	provides javax.script.ScriptEngineFactory
		with coneforest.psylla.engine.PsyllaScriptEngineFactory;
}
