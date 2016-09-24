command Preps :.,$s/\<\(в\|во\|на\|с\|по\|над\|под\|со\|для\|к\|до\|о\|об\|из\|от\|у\|про\|при\|от\|за\|по\|без\|между\|вне\)\> \c/\1 /cg
command PrepsEOL :.,$/\<\(в\|во\|на\|с\|по\|над\|под\|со\|для\|к\|до\|о\|об\|из\|от\|у\|про\|при\|от\|за\|по\|без\|между\|вне\)\>\c$/

source src/vim/ftdetect/psi.vim
au Syntax psi source src/vim/syntax/psi.vim

if has("autocmd")
	autocmd FileType {xml,xslt} setlocal iskeyword=@,-,\:,48-57,_,128-167,224-235
endif
