" Vim syntax file
" Language:     Psi
" Maintainer:   Anton Shvetz <tz@sectorb.msk.ru>
" Filenames:    *.psi
" Last Change:  20140728
" URL:          http://TODO
"
" Options Flags:
"

" Psi is case sensitive
syn case match

syn keyword psiTodo contained  TODO
syn keyword psiTodo contained  XXX
syn match psiComment		"#.*$" contains=psiTodo,@Spell extend
syn match psiSharpBang		"^#!.*"
syn match psiInteger		"[+-]\=\d\+"
syn match psiReal			"[+-]\=\d\+\."
syn match psiReal			"[+-]\=\d\+\.\d*\([Ee][+-]\=\d\+\)\=\>"
syn match psiReal			"[+-]\=\.\d\+\([Ee][+-]\=\d\+\)\=\>"
syn match psiReal			"[+-]\=\d\+[Ee][+-]\=\d\+\>"
syn cluster psiNumeric		contains=psiInteger,psiReal

syn match psiNameExecutable	"\([A-Za-z_\.=$@]\)\+\([A-Za-z_\.+-=\d$@]\)*"
syn match psiOperator       "[\[\]{}<>()?]"
syn match psiNameLiteral	"/\([A-Za-z_\.=$@]\)\+\([A-Za-z_\.+-=\d$@]\)*"
syn match psiNameImmediate	"//\([A-Za-z_\.=$@]\)\+\([A-Za-z_\.+-=\d$@]\)*"

syn region psiString start=+"+ end=+"+ skip=+"[^"]*?"+ contains=psiStringSpecial
syn match psiStringSpecial contained "\\[0antf"\\er]"
syn match psiStringSpecial contained "\\u[:xdigit:]{4}"

"hi link psiString	Constant
hi link psiString			Constant
hi link psiComment			Comment
hi link psiInteger			Number
hi link psiReal				Number
hi link psiNameExecutable	Identifier
hi link psiNameLiteral		Constant
hi link psiStringSpecial	SpecialChar
hi link psiTodo				Todo
hi link psiSharpBang		PreProc
hi link psiNameImmediate	Structure

hi psiOperator term=bold

let b:current_syntax="psi"

" vim: ts=4
