#! /usr/bin/env python

# Outputs the main structure of the given GF webservice.
# Main structure: for each grammar its name and the names of each concrete language.

# Author: Kaarel Kaljurand
# Version: 2012-03-16
#
# Examples:
#
# python info.py --name "http://localhost" --port 41296 --dir "/grammars/"
# python info.py -n "http://cloud.grammaticalframework.org" -p 80 -d "/grammars/"
#
import sys
import argparse
import os
import re
import json
import urllib
import urllib2
from os.path import join

def get_grammars(query):
	"""
	"""
	try:
		return json.loads(urllib2.urlopen(urllib2.Request(query)).read())
	except:
		print >> sys.stderr, "ERROR: Failed to get grammars, is the GF server running?"
		sys.exit()


def process_grammars(server_url, server_dir, grammars):
	"""
	"""
	for g in grammars:
		print '{:}{:}'.format(server_dir, g)
		req = urllib2.Request(server_url + server_dir + g)
		try:
			res = urllib2.urlopen(req)
			jsonAsStr = res.read()
			data = json.loads(jsonAsStr)
			for l in get_languages(data):
				print '\t{:}'.format(l)
		except:
			print >> sys.stderr, sys.exc_info()[0]


def get_languages(data):
	"""
	"""
	return list(map(
		(lambda x: x['name']),
		data["languages"])
	)


parser = argparse.ArgumentParser(description='Output the main structure of a GF webservice')

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

server_url = args.server_name + ":" + str(args.server_port)

query = server_url + args.server_dir + "grammars.cgi"
process_grammars(server_url, args.server_dir, get_grammars(query))
