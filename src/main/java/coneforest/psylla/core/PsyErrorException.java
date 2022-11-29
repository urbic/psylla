package coneforest.psylla.core;
import coneforest.psylla.*;

@ExceptionType("error")
abstract public class PsyErrorException
	extends	Exception
	implements PsyExecutable
{
	public PsyErrorException()
	{
	}

	public PsyErrorException(final PsyObject oEmitter)
	{
		setEmitter(oEmitter);
	}

	public void invoke(final PsyContext oContext)
	{
		try
		{
			((((Interpreter)oContext).errorDict()).get(getName())).invoke(oContext);
			return;
		}
		catch(final PsyErrorException e)
		{
			// NOP
		}

		System.err.println(Messages.format("handleErrorMessage",
				'/'+getName(),
				getEmitter().toSyntaxString()));

		System.err.print(Messages.getString("handleErrorMessageOStack"));
		{
			final var sj=new java.util.StringJoiner(" ", "\n\t", "");
			sj.setEmptyValue(" "+Messages.getString("handleErrorMessageEmpty"));
			ostack.forEach(o->sj.add(o.toSyntaxString()));
			System.err.println(sj.toString());
		}

		System.err.print(Messages.getString("handleErrorMessageEStack"));
		{
			final var sj=new java.util.StringJoiner(" ", "\n\t", "");
			sj.setEmptyValue(" "+Messages.getString("handleErrorMessageEmpty"));
			estack.forEach(o->sj.add(o.toSyntaxString()));
			System.err.println(sj.toString());
		}

		//oContext.setStopFlag(false);
		oContext.quit();
	}

	public String getName()
	{
		return "error";
	};

	public void setEmitter(final PsyObject oEmitter)
	{
		this.emitter=oEmitter;
	}

	public PsyObject getEmitter()
	{
		return emitter;
	}

	public void setStacks(
			final OperandStack ostack,
			final ExecutionStack estack,
			final DictStack dstack)
	{
		this.ostack=(OperandStack)ostack.clone();
		this.estack=(ExecutionStack)estack.clone();
		this.dstack=(DictStack)dstack.clone();
	}

	private PsyObject emitter;
	private OperandStack ostack;
	private ExecutionStack estack;
	private DictStack dstack;
}
