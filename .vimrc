:command Preps :.,$s/\<\(в\|во\|на\|с\|по\|над\|под\|со\|для\|к\|до\|о\|об\|из\|от\|у\|про\|при\|от\|за\|по\|без\|между\|вне\)\> \c/\1 /cg
:command PrepsEOL :.,$/\<\(в\|во\|на\|с\|по\|над\|под\|со\|для\|к\|до\|о\|об\|из\|от\|у\|про\|при\|от\|за\|по\|без\|между\|вне\)\>\c$/

:imap <C-W><C-M> <math xmlns="&nsMathML;"><CR></math><UP><End><CR><Tab><mi></mi>
:imap <C-W><C-D> <math xmlns="&nsMathML;" display="block"><CR></math><UP><End><CR><Tab><mi></mi>


