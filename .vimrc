se indentexpr=

ru ftdetect/psylla.vim
ru syntax/psylla.vim

au BufNewFile,BufRead *.t se ft=psylla
au Syntax psylla ru syntax/psylla.vim
	
au FileType {xml,xslt} setl iskeyword=@,-,\:,48-57,_,128-167,224-235
