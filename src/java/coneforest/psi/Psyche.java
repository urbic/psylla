package coneforest.psi;

public class Psyche
{
	public static void main(String args[])
	{
		String consoleEncoding=System.getProperty("consoleEncoding");

		int processed;
		try
		{
			try
			{
				cli=new coneforest.cli.CLIProcessor
					(
						new coneforest.cli.OptionFlag("help", "h", "?"),
						new coneforest.cli.OptionFlag("version", "V"),
						new coneforest.cli.OptionString("console-encoding", "C"),
						new coneforest.cli.OptionPath("classpath", "cp"),
						new coneforest.cli.OptionString("eval", "e")
					);
			}
			catch(coneforest.cli.CLIConfigurationException e)
			{
				System.err.println(e.getMessage());
				System.exit(1);
			}
			processed=cli.parse(args, 0);

			if(cli.getValue("console-encoding")!=null)
				consoleEncoding=((String)cli.getValue("console-encoding"));
			if(consoleEncoding!=null)
			{
				try
				{
					System.setOut(new java.io.PrintStream(System.out, true, consoleEncoding));
					System.setErr(new java.io.PrintStream(System.err, true, consoleEncoding));
				}
				catch(java.io.UnsupportedEncodingException e)
				{
					System.err.println(String.format(messages.getString("unsupportedEncodingText"),
						consoleEncoding));
					System.exit(1);
				}
			}
			if(cli.getValue("help"))
				help();
			if(cli.getValue("version"))
				version();

			Interpreter interpreter=new Interpreter();
			interpreter.acceptEnvironment(System.getenv());
			interpreter.setReader(new java.io.InputStreamReader(System.in));
			interpreter.setWriter(new java.io.OutputStreamWriter(System.out));

			if(cli.getValue("classpath")!=null)
				interpreter.acceptClassPath(cli.<String[]>getValue("classpath"));

			if(cli.getValue("eval")!=null)
			{
				interpreter.acceptScriptName("--eval");
				interpreter.acceptShellArguments(
						java.util.Arrays.copyOfRange(args, processed, args.length));
				interpreter.interpret(new java.io.StringReader(cli.<String>getValue("eval")));
			}
			else if(processed<args.length)
			{
				String scriptNname=args[processed];
				interpreter.acceptScriptName(scriptNname);
				interpreter.acceptShellArguments(
						java.util.Arrays.copyOfRange(args, processed+1, args.length));
				interpreter.interpret
					(
						scriptNname.equals("-")?
							new java.io.InputStreamReader(System.in):
							new java.io.FileReader(scriptNname)
					);
			}
			else
			{
				// REPL
			}
			System.exit(0);
		}
		catch(coneforest.cli.CLIProcessingException e)
		{
			System.err.println(e.getMessage());
			System.exit(1);
		}
		catch(java.io.FileNotFoundException e)
		{
			System.out.println(String.format(messages.getString("scriptNotFoundText"),
					e.getMessage()));
			System.exit(1);
		}
	}

	private static void help()
	{
		System.out.println(messages.getString("helpText"));
		System.exit(0);
	}

	private static void version()
	{
		System.out.print(String.format(messages.getString("versionText"),
				Version.getVersion()));
		System.exit(0);
	}

	private static java.util.ResourceBundle messages
		=java.util.ResourceBundle.getBundle("coneforest.psi.Messages");

	private static coneforest.cli.CLIProcessor cli;
}
