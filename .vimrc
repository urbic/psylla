source src/vim/ftdetect/psylla.vim
au! BufNewFile,BufRead *.t se ft=psylla
au Syntax psylla source src/vim/syntax/psylla.vim

if has("autocmd")
	autocmd FileType {xml,xslt} setlocal iskeyword=@,-,\:,48-57,_,128-167,224-235
endif
