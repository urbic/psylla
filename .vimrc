command Preps :.,$s/\<\(в\|во\|на\|с\|по\|над\|под\|со\|для\|к\|до\|о\|об\|из\|от\|у\|про\|при\|от\|за\|по\|без\|между\|вне\)\> \c/\1 /cg
command PrepsEOL :.,$/\<\(в\|во\|на\|с\|по\|над\|под\|со\|для\|к\|до\|о\|об\|из\|от\|у\|про\|при\|от\|за\|по\|без\|между\|вне\)\>\c$/

source src/vim/ftdetect/psylla.vim
au Syntax psylla source src/vim/syntax/psylla.vim

if has("autocmd")
	autocmd FileType {xml,xslt} setlocal iskeyword=@,-,\:,48-57,_,128-167,224-235
endif
