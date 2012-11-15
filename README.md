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

Starting (requires GF version 2012-11-14 or newer):

	$ GF_RESTRICTED=yes gf --server --document-root document-root

See usage examples in `tools/test_gfws.sh`.

### JPGF

JPGF could be another possible backend, but currently calls to it have
not been implemented.

Building
--------

This includes testing against the locally running GF server.

	$ mvn package

Building the website

	$ mvn site
