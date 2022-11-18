#!/usr/bin/python
import py_compile

import sys

if (len(sys.argv) != 3):
    raise Exception("Incorrect number of Args, expected 3 but got " + str(len(sys.argv)))

py_compile.compile(sys.argv[1], cfile=sys.argv[2])