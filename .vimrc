se rtp+=src/vim
se indentexpr=

ru ftdetect/psylla.vim
ru syntax/psylla.vim

au BufNewFile,BufRead *.java se tw=100

au BufNewFile,BufRead *.t se ft=psylla
au Syntax psylla ru syntax/psylla.vim

au BufNewFile,BufRead *.docbook se ft=docbkxml

au FileType {xml,xslt} setl iskeyword=@,-,\:,48-57,_,128-167,224-235
au FileType {yaml} se expandtab |se ts=2
