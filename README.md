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
  - http://github.com/GrammaticalFramework/GF/blob/master/src/server/PGFService.hs

Starting (requires GF version 2012-11-14 or newer):

	$ GF_RESTRICTED=yes gf --server=41297 --document-root document-root

See usage examples in `tools/test_gfws.sh`.

### JPGF

JPGF could be another possible backend, but currently calls to it have
not been implemented.

Building
--------

Testing against the locally running GF server.

	$ mvn test

Building the jar-file.

	$ mvn package -DskipTests

Building the website

	$ mvn site


Setting up an Eclipse project
-----------------------------

The Eclipse (or any other IDE) project files are not included in
this repository. To generate the required Eclipse files, run:

	$ mvn -Declipse.workspace=/home/yourname/workspace/ eclipse:configure-workspace
	$ mvn eclipse:eclipse
