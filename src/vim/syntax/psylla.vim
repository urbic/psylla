" Vim syntax file
" Language:		Psylla
" Maintainer:	Anton Shvetz <shvetz.anton@gmail.com>
" Filenames:	*.psy
" Last Change:	20221225
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

syn match psyllaNSPrefix		contained +[^/@]\+@+
syn match psyllaName			"\([A-Za-z_\.=$]\)\+\([A-Za-z_\.+-=\d@$]\)*" contains=psyllaNSPrefix
syn match psyllaOperator		"[\[\]{}<>()?]"
syn match psyllaStringLiteral	"/\([A-Za-z_\.=$]\)\+\([A-Za-z_\.+-=\d@$]\)*" contains=psyllaNSPrefix
syn match psyllaNameImmediate	"//\([A-Za-z_\.=$]\)\+\([A-Za-z_\.+-=\d@$]\)*"
syn region psyllaStringQuoted	start=+'+ end=+'+ skip=+\\\\\|\\'+ contains=psyllaStringSpecial

syn match psyllaIntegral		"[+-]\=\d\+"
syn match psyllaHexIntegral		"[Xx]`[+-]\=\x\+" contains=psyllaIntegralPrefix
syn match psyllaBinIntegral		"[Bb]`[+-]\=[01]\+" contains=psyllaIntegralPrefix
syn match psyllaOctIntegral		"[Oo]`[+-]\=[0-7]\+" contains=psyllaIntegralPrefix
syn match psyllaChrIntegral		"[Cc]`\([^\\]\|\\[0afenrtv\\]\)" contains=psyllaIntegralPrefix
syn match psyllaReal			"[+-]\=\d\+\."
syn match psyllaReal			"[+-]\=\d\+\.\d*\([Ee][+-]\=\d\+\)\=\>"
syn match psyllaReal			"[+-]\=\.\d\+\([Ee][+-]\=\d\+\)\=\>"
syn match psyllaReal			"[+-]\=\d\+[Ee][+-]\=\d\+\>"
syn cluster psyllaNumeric		contains=psyllaIntegral,psyllaHexIntegral,psyllaBinIntegral,psyllaOctIntegral,psyllaReal
syn region psyllaComment		start="/#" end="#/" contains=psyllaTodo

syn region psyllaStringBuffer	start=+"+ end=+"+ skip=+\\\\\|\\"+ contains=psyllaStringSpecial
syn match psyllaStringSpecial	contained +\\[0antf"\\erv]+
syn match psyllaStringSpecial	contained "\\u[[:xdigit:]]\{4}"
syn match psyllaStringSpecial	contained "\\x{[[:xdigit:]]\+}"
syn match psyllaStringSpecial	contained "\\o{[0-7]\+}"
syn match psyllaStringSpecial	contained "\\N{[[:digit:][:upper:]\- ]\+}"
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
syn match psyllaIntegralPrefix	contained "[XxOoBbCc]`"

hi link psyllaStringBuffer		Constant
hi link psyllaRegExp			Constant
hi link psyllaChrIntegral		Constant
hi link psyllaComment			Comment
hi link psyllaIntegral			Number
hi link psyllaHexIntegral		Number
hi link psyllaBinIntegral		Number
hi link psyllaOctIntegral		Number
hi link psyllaReal				Number
hi link psyllaName				Identifier
hi link psyllaStringLiteral		Constant
hi link psyllaStringQuoted		Constant
hi link psyllaStringSpecial		SpecialChar
hi link psyllaRegExpSpecial		SpecialChar
hi link psyllaIntegralPrefix	SpecialChar
hi link psyllaNSPrefix			Special
hi link psyllaTodo				Todo
hi link psyllaSharpBang			PreProc
hi link psyllaNameImmediate		Structure

hi psyllaOperator term=bold

let b:current_syntax="psylla"

" vim: ts=4
