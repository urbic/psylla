package coneforest.psi;
import coneforest.psi.core.*;

public class PsiSystemDictionary
	extends PsiModule
{
	public PsiSystemDictionary()
	{
		super();

		registerOperatorClasses
			(
				_abort.class,
				_abs.class,
				_add.class,
				_and.class,
				_append.class,
				_appendall.class,
				_arg.class,
				_array.class,
				_arraytomark.class,
				_astore.class,
				_atan.class,
				_begin.class,
				_bind.class,
				_bitset.class,
				_bitshift.class,
				_bitvector.class,
				_cbrt.class,
				_ceiling.class,
				_clear.class,
				_cleardictstack.class,
				_clearstack.class,
				_cleartomark.class,
				_clone.class,
				_close.class,
				_complex.class,
				_complexpolar.class,
				_conjugate.class,
				_contains.class,
				_copy.class,
				_cos.class,
				_cosh.class,
				_count.class,
				_countdictstack.class,
				_countexecstack.class,
				_counttomark.class,
				_currentdict.class,
				_def.class,
				_dict.class,
				_dicttomark.class,
				_div.class,
				_dup.class,
				_end.class,
				_eq.class,
				_exch.class,
				_exec.class,
				_executable.class,
				_exit.class,
				_exp.class,
				_external.class,
				_false.class,
				_filereader.class,
				_filewriter.class,
				_floor.class,
				_flush.class,
				_for.class,
				_forall.class,
				_ge.class,
				_get.class,
				_getinterval.class,
				_gt.class,
				_hypot.class,
				_idiv.class,
				_if.class,
				_ifelse.class,
				_index.class,
				_insert.class,
				_insertall.class,
				_intersects.class,
				_isempty.class,
				_keys.class,
				_keysforall.class,
				_known.class,
				_le.class,
				_length.class,
				_literal.class,
				_load.class,
				_loadall.class,
				_log.class,
				_loop.class,
				_lt.class,
				_mark.class,
				_max.class,
				_min.class,
				_mod.class,
				_mul.class,
				_ne.class,
				_neg.class,
				_normaldeviate.class,
				_not.class,
				_null.class,
				_or.class,
				_pop.class,
				_pow.class,
				_prettyprint.class,
				_print.class,
				_println.class,
				_pstack.class,
				_put.class,
				_putinterval.class,
				_quit.class,
				_random.class,
				_rcheck.class,
				_read.class,
				_readline.class,
				_readstring.class,
				_remove.class,
				_removeall.class,
				_repeat.class,
				_retainall.class,
				_reverse.class,
				_roll.class,
				_say.class,
				_set.class,
				_setseed.class,
				_settomark.class,
				_signum.class,
				_sin.class,
				_sinh.class,
				_sqrt.class,
				_stop.class,
				_stopped.class,
				_string.class,
				_stringreader.class,
				_stringwriter.class,
				_sub.class,
				_tan.class,
				_tanh.class,
				_tointeger.class,
				_token.class,
				_toname.class,
				_toreal.class,
				_tostring.class,
				_true.class,
				_type.class,
				_undef.class,
				_uniformboolean.class,
				_uniformdeviate.class,
				_wcheck.class,
				_where.class,
				_writestring.class,
				_xcheck.class,
				_xor.class
			);
		try
		{
			PsiOperator markOperator=(PsiOperator)psiGet("mark");
			psiPut("[", markOperator);
			psiPut("<", markOperator);
			psiPut("(", markOperator);
			psiPut("]", psiGet("arraytomark"));
			psiPut(">", psiGet("dicttomark"));
			psiPut(")", psiGet("settomark"));
			psiPut("?", psiGet("prettyprint"));
		}
		catch(PsiException e)
		{
			// TODO
		}
		psiPut("systemdict", this);
		psiPut("errordict", new coneforest.psi.errordict.ErrorDictionary());
		psiPut("mathPI", new PsiReal(Math.PI));
		psiPut("mathE", new PsiReal(Math.E));
		psiPut("stdrandom", new PsiRandom());
		psiPut("version", new PsiString(""+Version.getVersion()));
		psiPut("product", new PsiString("Psi"));
		psiPut("classpath", new PsiClassLoader());
	}
}
