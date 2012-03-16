#! /usr/bin/env python

# Formats the output of info.py as HTML with links to AceWiki.

# Author: Kaarel Kaljurand
# Version: 2012-03-16
#
# Examples:
#
# python info.py -n "http://cloud.grammaticalframework.org" -p 80 -d "/grammars/" |\
# python to_html.py -n "http://cloud.grammaticalframework.org" -p 80 -d "/grammars/"

import sys
import argparse
import os
import re
from string import Template

template_grammar = Template("""
  <h2>${grammar}</h2>
  <p>${count} languages</p>
""")


template_language = Template("""
    <li><a href="${server}${grammar}/${language}/">${language}</a></li>
""")



def format(server):
	"""
	"""
	pattern_lang = re.compile('^\s+(.+)$')
	langs = []
	grammar = None
	for line in sys.stdin:
		line = line.rstrip()
		m = pattern_lang.match(line)
		if m is None:
			format_grammar(server, grammar, langs)
			langs = []
			grammar = line
		else:
			langs.append(m.group(1))
	format_grammar(server, grammar, langs)


def format_grammar(server, grammar, langs):
	if grammar is None or len(langs) == 0:
		return
	grammar_name = re.sub(r'\.pgf$', r'', grammar)
	print template_grammar.substitute(
		grammar = grammar_name,
		count = len(langs)
	)
	print '<ul>'
	for lang in langs:
		format_language(server, grammar, lang)
	print '</ul>'


def format_language(server, grammar, language):
	print template_language.substitute(
		server = server,
		grammar = grammar,
		language = language
	)


parser = argparse.ArgumentParser(description='Output the main structure of a GF webservice as HTML with links to AceWiki')

parser.add_argument('-n', '--name', type=str, action='store',
					default='http://localhost',
					dest='server_name',
					help='server of AceWiki')

parser.add_argument('-p', '--port', type=int, action='store',
					default=9077,
					dest='server_port',
					help='port of AceWiki')

parser.add_argument('-v', '--version', action='version', version='%(prog)s v0.1')

args = parser.parse_args()

server = args.server_name + ":" + str(args.server_port)
format(server)
