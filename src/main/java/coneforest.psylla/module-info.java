/**
*	The Psylla language interpreter and core.
*
*	@author Anton Shvetz ‹shvetz.anton@gmail.com›
*/
module coneforest.psylla
{
	exports coneforest.psylla.core;
	exports coneforest.psylla.engine;
	exports coneforest.psylla;

	requires coneforest.clianthus;
	requires java.scripting;

	provides javax.script.ScriptEngineFactory
		with coneforest.psylla.engine.PsyllaScriptEngineFactory;
}
