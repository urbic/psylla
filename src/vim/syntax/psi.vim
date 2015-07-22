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
syn match psiHexInteger		"[+-]\=0x\x\+"
syn match psiReal			"[+-]\=\d\+\."
syn match psiReal			"[+-]\=\d\+\.\d*\([Ee][+-]\=\d\+\)\=\>"
syn match psiReal			"[+-]\=\.\d\+\([Ee][+-]\=\d\+\)\=\>"
syn match psiReal			"[+-]\=\d\+[Ee][+-]\=\d\+\>"
syn cluster psiNumeric		contains=psiInteger,psiHexInteger,psiReal
syn region psiComment		start="/#" end="#/" contains=psiTodo


syn match psiNameExecutable	"\([A-Za-z_\.=$@]\)\+\([A-Za-z_\.+-=\d$@]\)*"
syn match psiOperator       "[\[\]{}<>()?]"
syn match psiNameLiteral	"/\([A-Za-z_\.=$@]\)\+\([A-Za-z_\.+-=\d$@]\)*"
syn match psiNameImmediate	"//\([A-Za-z_\.=$@]\)\+\([A-Za-z_\.+-=\d$@]\)*"
syn region psiNameQuoted	start=+'+ end=+'+ skip=+\\\\\|\\'+ contains=psiStringSpecial

syn region psiString		start=+"+ end=+"+ skip=+\\\\\|\\"+ contains=psiStringSpecial
syn match psiStringSpecial	contained +\\[0antf"\\er]+
syn match psiStringSpecial	contained "\\u[[:xdigit:]]\{4}"
syn match psiStringSpecial	contained "\\x{[[:xdigit:]]\+}"
syn match psiStringSpecial	contained "\\c."

syn region psiRegExp		start=+@+ end=+@+ skip=+\\\\\|\\@+ contains=psiStringSpecial,psiRegExpSpecial
syn match psiRegExpSpecial	contained "(?\([idmsuxU\-]\+:\|[:=!>]\|<[=!]\|<[[:alpha:]]\+>\)\?"
syn match psiRegExpSpecial	contained "(?[idmsuxU\-]\+"
syn match psiRegExpSpecial	contained "[|)*+?.]"
syn match psiRegExpSpecial	contained "{\d\+\(,\d*\)\?}"
syn match psiRegExpSpecial	contained "\\[dDsSwWbBAGzZQE@]"
syn match psiRegExpSpecial	contained "\\\d\+"
syn match psiRegExpSpecial	contained "\[[^\]]\+\]"
syn match psiRegExpSpecial	contained "\\[pP]{[[:alpha:]]\+}"
syn match psiRegExpSpecial	contained "[\^^$]"
syn match psiRegExpSpecial	contained "\\k<[[:alpha:]]\+>"

hi link psiString			Constant
hi link psiRegExp			Constant
hi link psiComment			Comment
hi link psiInteger			Number
hi link psiHexInteger		Number
hi link psiReal				Number
hi link psiNameExecutable	Identifier
hi link psiNameLiteral		Constant
hi link psiNameQuoted		Constant
hi link psiStringSpecial	SpecialChar
hi link psiRegExpSpecial	SpecialChar
hi link psiTodo				Todo
hi link psiSharpBang		PreProc
hi link psiNameImmediate	Structure

hi psiOperator term=bold

let b:current_syntax="psi"

" vim: ts=4
