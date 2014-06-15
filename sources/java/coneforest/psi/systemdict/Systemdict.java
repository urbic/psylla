package coneforest.psi.systemdict;

public class Systemdict extends coneforest.psi.PSIModule
{
	public Systemdict()
	{
		super();

		register(_abs.class);
		register(_add.class);
		register(_and.class);
		register(_arraytomark.class);
		register(_ceiling.class);
		register(_clear.class);
		register(_copy.class);
		register(_cos.class);
		register(_count.class);
		register(_countdictstack.class);
		register(_cvlit.class);
		register(_cvx.class);
		register(_def.class);
		register(_div.class);
		register(_dup.class);
		register(_exch.class);
		register(_exec.class);
		register(_exit.class);
		register(_exp.class);
		register(_floor.class);
		register(_for.class);
		register(_idiv.class);
		register(_if.class);
		register(_ifelse.class);
		register(_index.class);
		register(_length.class);
		register(_log.class);
		register(_mul.class);
		register(_neg.class);
		register(_not.class);
		register(_or.class);
		register(_pop.class);
		register(_print.class);
		register(_pstack.class);
		register(_quit.class);
		register(_sin.class);
		register(_sqrt.class);
		register(_sub.class);
		register(_tan.class);
		register(_type.class);
		register(_xor.class);

		put("null", coneforest.psi.PSIInterpreter.NULL);
		put("true", coneforest.psi.PSIInterpreter.TRUE);
		put("false", coneforest.psi.PSIInterpreter.FALSE);
		put("mark", coneforest.psi.PSIInterpreter.MARK);
		put("[", get("mark"));
		put("<<", get("mark"));
		put("]", get("arraytomark"));
		put("systemdict", this);
	}
}
