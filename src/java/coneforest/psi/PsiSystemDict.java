package coneforest.psi;
import coneforest.psi.core.*;

public class PsiSystemDict
	extends PsiModule
{
	public PsiSystemDict()
		throws PsiException
	{
		super();

		//try
		//{
			registerOperatorClasses
				(
					_abort.class,
					_abs.class,
					_acos.class,
					_add.class,
					_and.class,
					_append.class,
					_appendall.class,
					_arg.class,
					_array.class,
					_arraytomark.class,
					_asin.class,
					_astore.class,
					_atan.class,
					_begin.class,
					_binarysearch.class,
					_bind.class,
					_bitset.class,
					_bitshift.class,
					_bitvector.class,
					_blockingqueue.class,
					_calendar.class,
					_calendartime.class,
					_capacity.class,
					_capturegroup.class,
					_capturegroupcount.class,
					_capturegroupend.class,
					_capturegroupstart.class,
					_cbrt.class,
					_ceiling.class,
					_clear.class,
					_clearbit.class,
					_cleardictstack.class,
					_clearstack.class,
					_cleartomark.class,
					_clone.class,
					_close.class,
					_cmp.class,
					_complex.class,
					_complexpolar.class,
					_condition.class,
					_conjugate.class,
					_contains.class,
					_convert.class,
					_copy.class,
					_copyfile.class,
					_cos.class,
					_cosh.class,
					_countdictstack.class,
					_countexecstack.class,
					_countstack.class,
					_counttomark.class,
					_createdirectory.class,
					_currentcontext.class,
					_currentdict.class,
					_currentdirectory.class,
					_def.class,
					_delete.class,
					_deletefile.class,
					_dequeue.class,
					_dict.class,
					_dictstack.class,
					_dicttomark.class,
					_div.class,
					_dup.class,
					_editline.class,
					_end.class,
					_enqueue.class,
					_entries.class,
					_eq.class,
					_eval.class,
					_exch.class,
					_exec.class,
					_execstack.class,
					_executive.class,
					_exit.class,
					_exp.class,
					_external.class,
					_extract.class,
					_extractinterval.class,
					_fileaccesstime.class,
					_filecreationtime.class,
					_fileexists.class,
					_filemodifiedtime.class,
					_filereader.class,
					_files.class,
					_filesize.class,
					_filewriter.class,
					_find.class,
					_findall.class,
					_flipbit.class,
					_floor.class,
					_flush.class,
					_for.class,
					_forall.class,
					_fork.class,
					_ge.class,
					_get.class,
					_getall.class,
					_getinterval.class,
					_give.class,
					_grep.class,
					_gt.class,
					_halt.class,
					_hardlink.class,
					_hashcode.class,
					_hypot.class,
					_idiv.class,
					_if.class,
					_ifelse.class,
					_imagpart.class,
					_index.class,
					_insert.class,
					_insertall.class,
					_instanceof.class,
					_intersects.class,
					_inunicodeblock.class,
					_isdirectory.class,
					_isempty.class,
					_isfile.class,
					_isfull.class,
					_isinstance.class,
					_issamefile.class,
					_issymlink.class,
					_iszero.class,
					_join.class,
					_keys.class,
					_known.class,
					_le.class,
					_length.class,
					_listdirectory.class,
					_load.class,
					_lock.class,
					_log.class,
					_loop.class,
					_lowercase.class,
					_lt.class,
					_map.class,
					_matcher.class,
					_max.class,
					_min.class,
					_mod.class,
					_monitor.class,
					_mul.class,
					_ne.class,
					_neg.class,
					_normaldeviate.class,
					_not.class,
					_notify.class,
					_notzero.class,
					_or.class,
					_pop.class,
					_pow.class,
					_prechop.class,
					_prettyprint.class,
					_print.class,
					_println.class,
					_prepend.class,
					_prependall.class,
					_postchop.class,
					_process.class,
					_processerror.class,
					_processreader.class,
					_processwriter.class,
					_pstack.class,
					_put.class,
					_putinterval.class,
					_quit.class,
					_random.class,
					_read.class,
					_readline.class,
					_readlink.class,
					_readstring.class,
					_ready.class,
					_realpart.class,
					_regexp.class,
					_remove.class,
					_removeall.class,
					_renamefile.class,
					_repeat.class,
					_replicate.class,
					_retainall.class,
					_reverse.class,
					_reset.class,
					_roll.class,
					_round.class,
					_say.class,
					_set.class,
					_setbit.class,
					_setlength.class,
					_setseed.class,
					_settomark.class,
					_signalerror.class,
					_signum.class,
					_sin.class,
					_sinh.class,
					_skip.class,
					_sleep.class,
					_slice.class,
					_sort.class,
					_split.class,
					_sqrt.class,
					_status.class,
					_stack.class,
					_stop.class,
					_stopped.class,
					_store.class,
					_string.class,
					_stringreader.class,
					_stringwriter.class,
					_sub.class,
					_symlink.class,
					_synchronized.class,
					_take.class,
					_tan.class,
					_tanh.class,
					_testbit.class,
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
					_unite.class,
					_uppercase.class,
					_values.class,
					_wait.class,
					_warn.class,
					_where.class,
					_write.class,
					_writestring.class,
					_xor.class,
					_yield.class
				);

			put("]", get("arraytomark"));
			put(">", get("dicttomark"));
			put(")", get("settomark"));
			put("?", get("prettyprint"));
			put("$error", new PsiDict());
			put("(", PsiMark.MARK);
			put("<", PsiMark.MARK);
			put("[", PsiMark.MARK);
			put("classpath", new PsiClassLoader());
			put("eol", new PsiName(System.getProperty("line.separator")));
			put("errordict", new PsiErrorDict());
			put("false", PsiBoolean.FALSE);
			put("mark", PsiMark.MARK);
			put("mathE", PsiReal.E);
			put("mathPI", PsiReal.PI);
			put("maxinteger", PsiInteger.MAX_VALUE);
			put("maxreal", PsiReal.MAX_VALUE);
			put("mininteger", PsiInteger.MIN_VALUE);
			put("minreal", PsiReal.MIN_VALUE);
			put("nan", PsiReal.NAN);
			put("null", PsiNull.NULL);
			put("osname", new PsiName(System.getProperty("os.name")));
			put("osversion", new PsiName(System.getProperty("os.version")));
			put("product", new PsiName("Psylla"));
			put("stderr", new PsiWriter(new java.io.OutputStreamWriter(System.err)));
			put("stdin", new PsiReader(new java.io.InputStreamReader(System.in)));
			put("stdout", new PsiWriter(new java.io.OutputStreamWriter(System.out)));
			put("stdrandom", new PsiRandom());
			put("systemdict", this);
			put("true", PsiBoolean.TRUE);
			put("userdict", new PsiDict());
			put("username", new PsiName(System.getProperty("user.name")));
			put("version", new PsiName(""+Version.getVersion()));
		//}
		//catch(PsiException e)
		//{
		//	throw new AssertionError();
		//}
	}
}
