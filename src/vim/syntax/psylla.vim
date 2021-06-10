" Vim syntax file
" Language:		Psylla
" Maintainer:	Anton Shvetz <shvetz.anton@gmail.com>
" Filenames:	*.psy
" Last Change:	20210610
" URL:			https://github.com/urbic/psylla
"
" Options Flags:
"

" Psylla is case sensitive
syn case match

syn keyword psyllaTodo 			contained TODO
syn keyword psyllaTodo 			contained XXX
syn match psyllaComment			"#.*$" contains=psyllaTodo,@Spell extend
syn match psyllaSharpBang		"^#!.*"
syn match psyllaInteger			"[+-]\=\d\+"
syn match psyllaHexInteger		"[+-]\=0x\x\+"
syn match psyllaReal			"[+-]\=\d\+\."
syn match psyllaReal			"[+-]\=\d\+\.\d*\([Ee][+-]\=\d\+\)\=\>"
syn match psyllaReal			"[+-]\=\.\d\+\([Ee][+-]\=\d\+\)\=\>"
syn match psyllaReal			"[+-]\=\d\+[Ee][+-]\=\d\+\>"
syn cluster psyllaNumeric		contains=psyllaInteger,psyllaHexInteger,psyllaReal
syn region psyllaComment		start="/#" end="#/" contains=psyllaTodo

syn match psyllaCharacter		"`\([^\\]\|\\[afenrt\\]\)"

syn match psyllaNSPrefix		contained +[^/@]\+@+
syn match psyllaNameExecutable	"\([A-Za-z_\.=$]\)\+\([A-Za-z_\.+-=\d@$]\)*" contains=psyllaNSPrefix
syn match psyllaOperator		"[\[\]{}<>()?]"
syn match psyllaNameLiteral		"/\([A-Za-z_\.=$]\)\+\([A-Za-z_\.+-=\d@$]\)*" contains=psyllaNSPrefix
syn match psyllaNameImmediate	"//\([A-Za-z_\.=$]\)\+\([A-Za-z_\.+-=\d@$]\)*"
syn region psyllaNameQuoted		start=+'+ end=+'+ skip=+\\\\\|\\'+ contains=psyllaStringSpecial

syn region psyllaString			start=+"+ end=+"+ skip=+\\\\\|\\"+ contains=psyllaStringSpecial
syn match psyllaStringSpecial	contained +\\[0antf"\\er]+
syn match psyllaStringSpecial	contained "\\u[[:xdigit:]]\{4}"
syn match psyllaStringSpecial	contained "\\x{[[:xdigit:]]\+}"
syn match psyllaStringSpecial	contained "\\c."

syn region psyllaRegExp			start=+\~+ end=+\~+ skip=+\\\\\|\\\~+ contains=psyllaStringSpecial,psyllaRegExpSpecial
syn match psyllaRegExpSpecial	contained "(?\([idmsuxU\-]\+:\|[:=!>]\|<[=!]\|<[[:alpha:]]\+>\)\?"
syn match psyllaRegExpSpecial	contained "(?[idmsuxU\-]\+"
syn match psyllaRegExpSpecial	contained "[|)*+?.]"
syn match psyllaRegExpSpecial	contained "{\d\+\(,\d*\)\?}"
syn match psyllaRegExpSpecial	contained "\\[dDsSwWbBAGzZQE~]"
syn match psyllaRegExpSpecial	contained "\\\d\+"
syn match psyllaRegExpSpecial	contained "\[[^\]]\+\]"
syn match psyllaRegExpSpecial	contained "\\[pP]{[[:alpha:]]\+}"
syn match psyllaRegExpSpecial	contained "[\^^$]"
syn match psyllaRegExpSpecial	contained "\\k<[[:alpha:]]\+>"

hi link psyllaString			Constant
hi link psyllaRegExp			Constant
hi link psyllaCharacter			Constant
hi link psyllaComment			Comment
hi link psyllaInteger			Number
hi link psyllaHexInteger		Number
hi link psyllaReal				Number
hi link psyllaNameExecutable	Identifier
hi link psyllaNameLiteral		Constant
hi link psyllaNameQuoted		Constant
hi link psyllaStringSpecial		SpecialChar
hi link psyllaRegExpSpecial		SpecialChar
hi link psyllaNSPrefix			Special
hi link psyllaTodo				Todo
hi link psyllaSharpBang			PreProc
hi link psyllaNameImmediate		Structure

hi psyllaOperator term=bold

let b:current_syntax="psylla"

" vim: ts=4
