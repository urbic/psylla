package coneforest.psi;
import coneforest.psi.core.*;

public class PsiSystemDictionary
	extends PsiModule
{
	public PsiSystemDictionary()
	{
		super();

		try
		{
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
					_cmp.class,
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
					_delete.class,
					_deleteinterval.class,
					_dict.class,
					_dicttomark.class,
					_div.class,
					_dup.class,
					_editline.class,
					_end.class,
					_eq.class,
					_eval.class,
					_exch.class,
					_exec.class,
					_exit.class,
					_exp.class,
					_external.class,
					_fileexists.class,
					_filereader.class,
					_filesize.class,
					_filewriter.class,
					_find.class,
					_floor.class,
					_flush.class,
					_for.class,
					_forall.class,
					_fork.class,
					_ge.class,
					_get.class,
					_getgroup.class,
					_getgroupend.class,
					_getgroupstart.class,
					_getinterval.class,
					_gt.class,
					_hypot.class,
					_idiv.class,
					_if.class,
					_ifelse.class,
					_im.class,
					_index.class,
					_insert.class,
					_insertall.class,
					_intersects.class,
					_isa.class,
					_isdir.class,
					_isempty.class,
					_isfile.class,
					_iszero.class,
					_join.class,
					_keys.class,
					_keysforall.class,
					_known.class,
					_le.class,
					_length.class,
					_load.class,
					_loadall.class,
					_lock.class,
					_log.class,
					_loop.class,
					_lt.class,
					_lsdir.class,
					_max.class,
					_min.class,
					_mkdir.class,
					_mod.class,
					_monitor.class,
					_mul.class,
					_ne.class,
					_neg.class,
					_normaldeviate.class,
					_not.class,
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
					_re.class,
					_read.class,
					_readline.class,
					_readstring.class,
					_regexp.class,
					_remove.class,
					_removeall.class,
					_repeat.class,
					_replicate.class,
					_retainall.class,
					_reverse.class,
					_roll.class,
					_round.class,
					_say.class,
					_set.class,
					_setseed.class,
					_settomark.class,
					_signum.class,
					_sin.class,
					_sinh.class,
					_sleep.class,
					_slice.class,
					_sort.class,
					_split.class,
					_sqrt.class,
					_stop.class,
					_stopped.class,
					_string.class,
					_stringreader.class,
					_stringwriter.class,
					_sub.class,
					_tan.class,
					_tanh.class,
					_time.class,
					_tointeger.class,
					_tokens.class,
					_toname.class,
					_toreal.class,
					_tostring.class,
					_type.class,
					_undef.class,
					_uniformboolean.class,
					_uniformdeviate.class,
					_values.class,
					_where.class,
					_writestring.class,
					_xor.class,
					_yield.class
				);

			psiPut("]", psiGet("arraytomark"));
			psiPut(">", psiGet("dicttomark"));
			psiPut(")", psiGet("settomark"));
			psiPut("?", psiGet("prettyprint"));
			psiPut("systemdict", this);
			psiPut("mark", PsiMark.MARK);
			psiPut("null", PsiNull.NULL);
			psiPut("[", PsiMark.MARK);
			psiPut("<", PsiMark.MARK);
			psiPut("(", PsiMark.MARK);
			psiPut("true", PsiBoolean.TRUE);
			psiPut("false", PsiBoolean.FALSE);
			psiPut("globaldict", new PsiDictionary());
			psiPut("userdict", new PsiDictionary());
			psiPut("errordict", new PsiErrorDictionary());
			psiPut("mathPI", new PsiReal(Math.PI));
			psiPut("mathE", new PsiReal(Math.E));
			psiPut("stdrandom", new PsiRandom());
			psiPut("product", new PsiName("Psyche"));
			psiPut("version", new PsiName(""+Version.getVersion()));
			psiPut("osname", new PsiName(System.getProperty("os.name")));
			psiPut("osversion", new PsiName(System.getProperty("os.version")));
			psiPut("username", new PsiName(System.getProperty("user.name")));
			psiPut("classpath", new PsiClassLoader());
			psiPut("eol", new PsiString(System.getProperty("line.separator")));
			psiPut("$error", new PsiDictionary());
		}
		catch(PsiException e)
		{
			throw new AssertionError();
		}
	}
}
