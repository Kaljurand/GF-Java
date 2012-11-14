Java interface to GF
====================

Java front-end to a GF service, modeled after the GF Webservice / GF Cloud Service

Status
------

  - Interface definitions almost complete.
  - GF Webservice wrapper covers the main webservice functionality.


Backends
--------

### GF Webservice / GF Cloud Service

Documentation:

  - http://code.google.com/p/grammatical-framework/wiki/GFWebServiceAPI
  - http://cloud.grammaticalframework.org/gf-cloud-api.html

Starting:

	$ GF_RESTRICTED=yes gf -server

See usage examples in `tools/test_gfws.sh`.

Building
--------

This includes testing against the locally running GF server.

	$ mvn package


Testing using a local GF webservice
-----------------------------------

Make sure that the PGF files used in testing (e.g. `Go.pgf`) are included in the
server directory ("document root"),
as a missing grammar file would cause _500 Internal Server Error_.

	$ cp grammars/Go.pgf /home/kaarel/.cabal/share/gf-3.3/www/grammars/
