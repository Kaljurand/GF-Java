
pgf=$(HOME)/.cabal/share/gf-3.3.3/www/grammars/
editable=$(HOME)/.cabal/share/gf-3.3.3/www/tmp/

all: help

help:
	@echo Targets:
	@echo
	@echo "     ls_pgf:  list PGF grammars"
	@echo "ls_editable:  list editable grammars"
	@echo "       test:  run a small test"


ls_pgf:
	ls -la $(pgf)

ls_editable:
	find $(editable)

test:
	sh test_gfws.sh
