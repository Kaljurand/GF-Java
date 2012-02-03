Java interface to GF
====================

Java front-end to a GF service, modeled after the GF Webservice.

Status
------

  - Interface definitions almost complete.
  - GF Webservice wrapper covers the main webservice functionality.
  - PNG outputs are not supported


Backends
--------

### GF Webservice

Documentation:

http://code.google.com/p/grammatical-framework/wiki/GFWebServiceAPI

Starting:

> gf -server

> curl http://localhost:41296

#### Examples

Instead of `json_xs` one can use e.g. `python -mjson.tool`.

> curl "http://localhost:41296/grammars/Go.pgf?command=grammar" | json_xs

> curl "http://localhost:41296/grammars/Go.pgf?command=parse&cat=Number&from=GoEng&input=three" | json_xs

> curl "http://localhost:41296/grammars/Go.pgf?command=linearize&to=GoEng&tree=n3" | json_xs

> curl "http://localhost:41296/grammars/Go.pgf?command=translate&cat=Number&from=GoEng&to=GoApp&input=three" | json_xs

> curl "http://localhost:41296/grammars/Go.pgf?command=complete&cat=Number&input=t" | json_xs


Building
--------

Clean up, create JAR files and create Javadoc:

> ant


Testing
-------

Start the GF server:

> $ gf -server

	This is GF version 3.3.
	Built on linux/x86_64 with ghc-6.12, flags: interrupt server cclazy
	Document root = /home/kaarel/.cabal/share/gf-3.3/www
	Starting HTTP server, open http://localhost:41296/ in your web browser.

Make sure that the PGF files used in testing (e.g. `Go.pgf`) are included in the
server directory, missing grammar file would cause _500 Internal Server Error_.

> $ cp grammars/Go.pgf /home/kaarel/.cabal/share/gf-3.3/www/grammars/

> $ ls /home/kaarel/.cabal/share/gf-3.3/www/grammars/ | grep Go

	Go.pgf

Run the tests

> ant test
