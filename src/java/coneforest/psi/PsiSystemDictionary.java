package coneforest.psi;
import coneforest.psi.systemdict.*;

public class PsiSystemDictionary extends PsiModule
{
	public PsiSystemDictionary()
	{
		super();

		register(_abs.class);
		register(_add.class);
		register(_aload.class);
		register(_and.class);
		register(_append.class);
		register(_array.class);
		register(_arraytomark.class);
		register(_astore.class);
		register(_begin.class);
		register(_bind.class);
		register(_bitset.class);
		register(_bitshift.class);
		register(_bitvector.class);
		register(_ceiling.class);
		register(_clear.class);
		register(_cleardictstack.class);
		register(_close.class);
		register(_copy.class);
		register(_cos.class);
		register(_count.class);
		register(_countdictstack.class);
		register(_countexecstack.class);
		register(_counttomark.class);
		register(_currentdict.class);
		register(_cvi.class);
		register(_cvlit.class);
		register(_cvn.class);
		register(_cvs.class);
		register(_cvx.class);
		register(_def.class);
		register(_dict.class);
		register(_dicttomark.class);
		register(_div.class);
		register(_dup.class);
		register(_end.class);
		register(_eq.class);
		register(_exch.class);
		register(_exec.class);
		register(_exit.class);
		register(_exp.class);
		register(_false.class);
		register(_filereader.class);
		register(_filewriter.class);
		register(_floor.class);
		register(_flush.class);
		register(_for.class);
		register(_forall.class);
		register(_ge.class);
		register(_get.class);
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
		register(_mark.class);
		register(_mod.class);
		register(_mul.class);
		register(_ne.class);
		register(_neg.class);
		register(_not.class);
		register(_or.class);
		register(_pop.class);
		register(_prettyprint.class);
		register(_pstack.class);
		register(_put.class);
		register(_quit.class);
		register(_read.class);
		register(_repeat.class);
		register(_roll.class);
		register(_sin.class);
		register(_sqrt.class);
		register(_stop.class);
		register(_stopped.class);
		register(_string.class);
		register(_stringreader.class);
		register(_stringwriter.class);
		register(_sub.class);
		register(_token.class);
		register(_true.class);
		register(_type.class);
		register(_undef.class);
		register(_where.class);
		register(_writestring.class);
		register(_xcheck.class);
		register(_xor.class);

		try
		{
			put("[", get("mark"));
			put("<<", get("mark"));
			put("]", get("arraytomark"));
			put(">>", get("dicttomark"));
			put("==", get("prettyprint"));
		}
		catch(PsiException e)
		{
			// TODO
		}
		put("systemdict", this);
		put("errordict", new coneforest.psi.errordict.ErrorDictionary());
	}
}
