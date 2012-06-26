Tools
=====

Some tools for working with the GF webservice (not really specific to GF-Java).

info.py
-------

For a given GF webservice,
for each of its PGF, outputs its name and concrete languages.

### Example

	$ gf -server=8123
	$ python info.py --name "http://localhost" --port 8123 --dir "/grammars/"


pgf.py
------

Parses strings in STDIN into abstract trees in STDOUT, using
the GF webservice running on localhost.

### Example

	$ gf -server=41296
	$ echo "go five meters back" | python pgf.py -g Go -f GoEng


to_html.py
----------

Formats the output of `info.py` as HTML with links to AceWiki.


to_web_xml.py
-------------

Formats the output of `info.py` as `web.xml` servlet declarations.


test_gfws.sh
------------

Some example webservice queries using `curl`.
