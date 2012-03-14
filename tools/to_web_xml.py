#! /usr/bin/env python

# Formats the output of info.py as web.xml servlet declarations.

# Author: Kaarel Kaljurand
# Version: 2012-03-14
#
# Examples:
#
# python info.py -n "http://cloud.grammaticalframework.org" -p 80 -d "/grammars/" |\
# python to_web_xml.py -n "http://cloud.grammaticalframework.org" -p 80 -d "/grammars/"

import sys
import argparse
import os
import re
from string import Template

template_backend = Template("""
  <servlet>
    <servlet-name>${servlet_name}</servlet-name>
    <servlet-class>ch.uzh.ifi.attempto.acewiki.BackendServlet</servlet-class>
    <load-on-startup>1</load-on-startup>
    <init-param>
      <param-name>engine_class</param-name>
      <param-value>ch.uzh.ifi.attempto.acewiki.gfservice.GFEngine</param-value>
    </init-param>
    <init-param>
      <param-name>pgf_name</param-name>
      <param-value>${server_dir}${pgf_name}</param-value>
    </init-param>
    <init-param>
      <param-name>service_uri</param-name>
      <param-value>${server}</param-value>
    </init-param>
    <init-param>
      <param-name>ontology</param-name>
      <param-value>${ontology}</param-value>
    </init-param>
  </servlet>
  <servlet-mapping>
    <servlet-name>${servlet_name}</servlet-name>
    <url-pattern>/backend/${servlet_name}</url-pattern>
  </servlet-mapping>
""")


template_wiki = Template("""
  <servlet>
    <servlet-name>${servlet_name}</servlet-name>
    <servlet-class>ch.uzh.ifi.attempto.acewiki.AceWikiServlet</servlet-class>
    <init-param>
      <param-name>backend</param-name>
      <param-value>${backend}</param-value>
    </init-param>
    <init-param>
      <param-name>language</param-name>
      <param-value>${language}</param-value>
    </init-param>
    <init-param>
      <param-name>title</param-name>
      <param-value>${title}</param-value>
    </init-param>
  </servlet>
  <servlet-mapping>
    <servlet-name>${servlet_name}</servlet-name>
    <url-pattern>/${backend}/${servlet_name}/</url-pattern>
  </servlet-mapping>
""")



def format(server, server_dir):
	"""
	"""
	pattern_lang = re.compile('^\s+(.+)$')
	backend = None
	for line in sys.stdin:
		line = line.rstrip()
		m = pattern_lang.match(line)
		if m is not None:
			format_wiki(backend, m.group(1))
		else:
			backend = line
			format_backend(line, server, server_dir)


def format_backend(line, server, server_dir):
	print template_backend.substitute(
		servlet_name = line,
		pgf_name = line,
		server = server,
		server_dir = server_dir,
		ontology = line
	)


def format_wiki(backend, lang):
	print template_wiki.substitute(
		servlet_name = lang,
		backend = backend,
		language = lang,
		title = 'Wiki in ' + lang
	)


parser = argparse.ArgumentParser(description='Output the main structure of a GF webservice as web.xml')

parser.add_argument('-n', '--name', type=str, action='store',
					default='http://localhost',
					dest='server_name',
					help='server of the webservice')

parser.add_argument('-p', '--port', type=int, action='store',
					default=41296,
					dest='server_port',
					help='port of the webservice')

parser.add_argument('-d', '--dir', type=str, action='store',
					default='/grammars/',
					dest='server_dir',
					help='directory on the server where PGFs are hosted')

parser.add_argument('-v', '--version', action='version', version='%(prog)s v0.1')

args = parser.parse_args()

server = args.server_name + ":" + str(args.server_port)
format(server, args.server_dir)
