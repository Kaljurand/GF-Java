#! /usr/bin/env python

# Turns strings in the STDIN to abstract trees in the STDOUT
# using the GF webservice.

# Author: Kaarel Kaljurand
# Version: 2012-02-29
#
# Example:
#
# echo "go five meters back" | python pgf.py -g Go -f GoEng
#
import sys
import argparse
import subprocess
import threading
import os
import re
import time
import json
import urllib
import urllib2
from os.path import join

agent='PgfTester/0.0.1'
server_name="http://localhost"
server_port=41296
server_path="/grammars/"
server_url=server_name + ":" + str(server_port) + server_path
prefix='| '


def process_strings():
	"""
	"""
	count = 0
	for line in sys.stdin:
		line = line.strip()
		# small correction to the input data
		line = re.sub('\.$', ' .', line)
		count = count + 1
		lineAsArg = urllib.urlencode({ 'input' : line })
		req = urllib2.Request(server_url + "&" + lineAsArg)
		try:
			res = urllib2.urlopen(req)
			jsonAsStr = res.read()
			data = json.loads(jsonAsStr)
			print '{:}{:}'.format(prefix, get_first_tree(data).encode('utf8'))
		except:
			print >> sys.stderr, 'ERROR {:}'.format(line)
			print >> sys.stderr, sys.exc_info()[0]


def get_first_tree(data):
	"""
	"""
	return data[0]["trees"][0]


parser = argparse.ArgumentParser(description='Parse strings with the GF webservice')

parser.add_argument('-g', '--grammar', type=str, action='store',
					dest='grammar',
					help='PGF grammar')

parser.add_argument('-c', '--command', type=str, action='store',
					default='parse',
					dest='command',
					help='command')

parser.add_argument('-f', '--from', type=str, action='store',
					dest='lang_from',
					help='from')

parser.add_argument('-v', '--version', action='version', version='%(prog)s v0.1')

args = parser.parse_args()

if args.grammar is None:
	print >> sys.stderr, 'ERROR: argument -g/--grammar is not specified'
	exit()

if args.lang_from is None:
	print >> sys.stderr, 'ERROR: argument -f/--from is not specified'
	exit()

server_url += args.grammar + ".pgf?" + urllib.urlencode({
	'command' : args.command,
	'from' : args.lang_from
	})


time_start = time.time()
process_strings()
time_end = time.time()

print >> sys.stderr, 'Duration: {:.2f} sec'.format(time_end - time_start)
