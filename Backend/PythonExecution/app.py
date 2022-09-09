from flask import Flask, redirect, url_for, request
from io import StringIO
from contextlib import redirect_stdout
app = Flask(__name__)

@app.route('/', methods = ["POST"])
def execute():
	cleanCode = request.data #validate(request.data)
	f = StringIO()
	with redirect_stdout(f):
		exec(cleanCode)
	s = f.getvalue()
	return s

if __name__ == '__main__':
	app.run()
