import multiprocessing
from multiprocessing import Pipe
from traceback import format_exc
from flask import Flask, redirect, url_for, request
from io import StringIO
from contextlib import redirect_stdout
app = Flask(__name__)

def run(clean_code, conn):
	f = StringIO()
	code = "200"
	with redirect_stdout(f):
		try:
			x = compile(clean_code, "user", "exec")
			exec(x)
		except Exception as e:
			code = "422"
			print(format_exc())
	message = f.getvalue()
	conn.send([message, code])
	conn.close()

@app.route('/', methods = ["POST"])
def init_student_run():
	clean_code = request.data #validate(request.data)
	parent_conn, child_conn = Pipe()
	p = multiprocessing.Process(target=run, args=(clean_code, child_conn))
	p.start()
	p.join(10)
	if p.is_alive():
		p.kill()
		p.join()
		return "Entered code took too long to execute", 508
	msg, r_code = parent_conn.recv()
	return msg, r_code


if __name__ == '__main__':
	app.run()
