#! /usr/bin/env python

# Formats the output of info.py as web.xml servlet declarations.

# Author: Kaarel Kaljurand
# Version: 2012-03-16
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

template_grammar = Template("""
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
      <param-value>${pgf_name}</param-value>
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
    <url-pattern>${servlet_name}</url-pattern>
  </servlet-mapping>
""")


template_language = Template("""
  <servlet>
    <servlet-name>${backend}/${servlet_name}</servlet-name>
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
    <servlet-name>${backend}/${servlet_name}</servlet-name>
    <url-pattern>${backend}/${servlet_name}/</url-pattern>
  </servlet-mapping>
""")


def format(server, server_dir):
	"""
	"""
	pattern_lang = re.compile('^\s+(.+)$')
	grammar = None
	langs = []
	for line in sys.stdin:
		line = line.rstrip()
		m = pattern_lang.match(line)
		if m is None:
			format_grammar(server, server_dir, grammar, langs)
			grammar = line
			langs = []
		else:
			langs.append(m.group(1))
	format_grammar(server, server_dir, grammar, langs)


def format_grammar(server, server_dir, grammar, langs):
	if grammar is None or len(langs) == 0:
		return
	ontology = re.sub(r'\.pgf$', r'', grammar)
	ontology = re.sub(r'^/', r'', ontology)
	ontology = re.sub(r'/', r'__', ontology)
	print template_grammar.substitute(
		servlet_name = grammar,
		pgf_name = grammar,
		server = server,
		server_dir = server_dir,
		ontology = ontology
	)
	for l in langs:
		format_language(grammar, l)


def format_language(grammar, language):
	print template_language.substitute(
		servlet_name = language,
		backend = grammar,
		language = language,
		title = 'Wiki in ' + language
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
