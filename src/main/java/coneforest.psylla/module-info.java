module coneforest.psylla
{
	exports coneforest.psylla.core.errors;
	exports coneforest.psylla.core.types;
	exports coneforest.psylla.engine;
	exports coneforest.psylla;

	requires coneforest.clianthus;
	requires java.scripting;

	provides javax.script.ScriptEngineFactory
		with coneforest.psylla.engine.PsyllaScriptEngineFactory;
}
