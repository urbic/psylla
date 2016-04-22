package coneforest.psi;
import coneforest.psi.core.*;

public class Psylla
{
	public static void main(String args[])
	{
		try
		{
			//String consoleEncoding=System.getProperty("consoleEncoding");
			String consoleEncoding=java.nio.charset.Charset.defaultCharset().toString();
			//System.err.println(consoleEncoding);
			final coneforest.cli.Processor cli=new coneforest.cli.Processor
				(
					new coneforest.cli.OptionFlag("help", "h", "?"),
					new coneforest.cli.OptionFlag("version", "V"),
					new coneforest.cli.OptionString("console-encoding", "C"),
					new coneforest.cli.OptionPath("classpath", "cp"),
					new coneforest.cli.OptionString("eval", "e"),
					new coneforest.cli.OptionString("locale", "L"),
					new coneforest.cli.OptionLong("random-seed", "S")
				);
			final int processed=cli.parse(args, 0);

			if(cli.getValue("console-encoding")!=null)
				consoleEncoding=((String)cli.getValue("console-encoding"));
			if(consoleEncoding!=null)
				try
				{
					System.setOut(new java.io.PrintStream(System.out, true, consoleEncoding));
					System.setErr(new java.io.PrintStream(System.err, true, consoleEncoding));
				}
				catch(java.io.UnsupportedEncodingException e)
				{
					System.err.println(Messages.format("unsupportedEncodingText", consoleEncoding));
					System.exit(1);
				}
			if(cli.getValue("locale")!=null)
				java.util.Locale.setDefault(java.util.Locale.forLanguageTag((String)cli.getValue("locale")));
			if(cli.getValue("help"))
				help();
			if(cli.getValue("version"))
				version();

			final java.io.Reader scriptReader;
			final String scriptName;
			final String[] shellArguments;
			final Interpreter interpreter;

			if(cli.getValue("eval")!=null)
			{
				scriptName="--eval";
				shellArguments=java.util.Arrays.copyOfRange(args, processed, args.length);
				scriptReader=new java.io.StringReader(cli.<String>getValue("eval"));
			}
			else if(processed<args.length)
			{
				scriptName=args[processed];
				shellArguments=java.util.Arrays.copyOfRange(args, processed+1, args.length);
				scriptReader=scriptName.equals("-")?
						new java.io.InputStreamReader(System.in):
						new java.io.FileReader(scriptName);
			}
			else
			{
				scriptName="--repl";
				scriptReader=null;
				shellArguments=new String[]{};
				// TODO REPL
			}

			if(scriptReader!=null)
			{
				interpreter=new Interpreter()
					{
						@Override
						public void run()
						{
							interpret(scriptReader);
						}
					};
			}
			else
			{
				interpreter=new Interpreter()
					{
						@Override
						public void run()
						{
							try
							{
								repl();
							}
							catch(PsiException e)
							{
							}
						}
					};
			}
			interpreter.acceptEnvironment(System.getenv());
			interpreter.acceptScriptName(scriptName);
			interpreter.acceptShellArguments(shellArguments);
			if(cli.getValue("classpath")!=null)
				interpreter.acceptClassPath(cli.<String[]>getValue("classpath"));

			if(cli.getValue("random-seed")!=null)
			{
				long seed=cli.getValue("random-seed");
				((PsiRandom)interpreter.dictStack().load("stdrandom")).psiSetSeed(PsiInteger.valueOf(seed));
			}

			interpreter.start();
		}
		catch(PsiException e)
		{
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}
		catch(coneforest.cli.CLIProcessingException e)
		{
			System.err.println(e.getLocalizedMessage());
			System.err.println(Messages.getString("useHelpOption"));
			System.exit(1);
		}
		catch(java.io.FileNotFoundException e)
		{
			System.out.println(Messages.format("scriptNotFoundText", e.getLocalizedMessage()));
			System.exit(1);
		}
	}

	private static void help()
	{
		System.out.println(Messages.getString("helpText"));
		System.exit(0);
	}

	private static void version()
	{
		System.out.print(Messages.format("versionText", Version.getVersion()));
		System.exit(0);
	}
}
