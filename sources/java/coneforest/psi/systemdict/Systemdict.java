package coneforest.psi.systemdict;
import coneforest.psi.*;

public class Systemdict extends coneforest.psi.PSIModule
{
	public Systemdict()
	{
		super();

		register(_abs.class);
		register(_add.class);
		register(_and.class);
		register(_array.class);
		register(_arraytomark.class);
		register(_begin.class);
		register(_ceiling.class);
		register(_clear.class);
		register(_copy.class);
		register(_cos.class);
		register(_count.class);
		register(_countdictstack.class);
		register(_cvlit.class);
		register(_cvx.class);
		register(_def.class);
		register(_dict.class);
		register(_dicttomark.class);
		register(_div.class);
		register(_dup.class);
		register(_eq.class);
		register(_exch.class);
		register(_exec.class);
		register(_exit.class);
		register(_exp.class);
		register(_floor.class);
		register(_for.class);
		register(_ge.class);
		register(_gt.class);
		register(_idiv.class);
		register(_if.class);
		register(_ifelse.class);
		register(_index.class);
		register(_known.class);
		register(_le.class);
		register(_length.class);
		register(_load.class);
		register(_log.class);
		register(_loop.class);
		register(_lt.class);
		register(_mod.class);
		register(_mul.class);
		register(_ne.class);
		register(_neg.class);
		register(_not.class);
		register(_or.class);
		register(_pop.class);
		register(_prettyprint.class);
		register(_pstack.class);
		register(_quit.class);
		register(_repeat.class);
		register(_roll.class);
		register(_sin.class);
		register(_sqrt.class);
		register(_string.class);
		register(_sub.class);
		register(_tan.class);
		register(_type.class);
		register(_xcheck.class);
		register(_xor.class);

		put("null", new PSINull());
		put("true", new PSIBoolean(true));
		put("false", new PSIBoolean(false));
		put("mark", new PSIMark());
		put("[", get("mark"));
		put("<<", get("mark"));
		put("]", get("arraytomark"));
		put(">>", get("dicttomark"));
		put("==", get("prettyprint"));
		put("systemdict", this);
	}
}
