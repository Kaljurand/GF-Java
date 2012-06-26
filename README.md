Java interface to GF
====================

Java front-end to a GF service, modeled after the GF Webservice.

Status
------

  - Interface definitions almost complete.
  - GF Webservice wrapper covers the main webservice functionality.


Backends
--------

### GF Webservice

Documentation:

http://code.google.com/p/grammatical-framework/wiki/GFWebServiceAPI

Starting:

	$ gf -server

See usage examples in `tools/test_gfws.sh`.

Building
--------

Clean up, create JAR files and create Javadoc:

	$ ant

Testing
-------

Make sure that you are connected to the internet and run

	$ ant test

This test uses the GF webservice and a PGF from
<http://cloud.grammaticalframework.org/tmp/gfse.74044909/Go.pgf>.


Testing using a local GF webservice
-----------------------------------

Start the GF server:

	$ gf -server

	This is GF version 3.3.
	Built on linux/x86_64 with ghc-6.12, flags: interrupt server cclazy
	Document root = /home/kaarel/.cabal/share/gf-3.3/www
	Starting HTTP server, open http://localhost:41296/ in your web browser.

Make sure that the PGF files used in testing (e.g. `Go.pgf`) are included in the
server directory ("document root"),
as a missing grammar file would cause _500 Internal Server Error_.

	$ cp grammars/Go.pgf /home/kaarel/.cabal/share/gf-3.3/www/grammars/

Edit the `GfWebServiceTest.java` source to change `CLOUD` to `LOCALHOST` where needed.

Run the tests

	$ ant test
