import json
import multiprocessing
from multiprocessing import Pipe
from traceback import format_exc
from flask import Flask, request
from io import StringIO
from contextlib import redirect_stdout

app = Flask(__name__)

'''
The Student Code Execution Server or "SCES"
Written by Grant DeWaay of group 2_AN_4 for CS309

This server is solely made to process, compile, and run Python code
of the JSON parameters sent via the Spring Boot server.

This may be adjusted as we continue with the project and as we set
up the MySQL database. We'll discuss this at a later date.

The JSON should be formatted as such:

{
	"code": "x = 23 + 2\nprint(x)",
	"tests": [
		{
			"var": "x",
			"value": "1",
			"expected_out": "12"
		},
		{
			"var": "x",
			"value": "3",
			"expected_out": "12"
		}
	]
}	

it is pretty straight forward	
'''


def run(code, tests, conn):
	outputs = []
	cases = []
	for i in tests:
		cases.append(i.get("var") + " = " + i.get("value") + "\n")
	resp = "200"
	loop = 0
	for i in cases:
		expected_out = tests[loop].get("expected_out")
		f = StringIO()
		with redirect_stdout(f):
			try:
				y = i + code
				x = compile(y, '', 'exec')
				globals_parameter = {'__builtins__': None}
				locals_parameter = {'print': print, 'dir': dir}
				exec(x, globals_parameter, locals_parameter)
			except Exception:
				resp = "422"
				message = format_exc()
				break
		complete = (f.getvalue() == expected_out) | (f.getvalue() == expected_out + "\n")
		outputs.append({"correct": complete, "actual": f.getvalue(), "expected": expected_out})
		loop += 1
	conn.send([outputs, resp])
	conn.close()


<<<<<<< HEAD
@app.route('/python', methods=["POST"])
=======
@app.route('/assignment/python', methods=["POST"])
>>>>>>> ec4b8dfa3af4d9ad89ab5e5326697b59faef498f
def init_student_run():
	json_data = request.get_json()
	code = json_data.get('code')
	tests = json_data.get('tests')
	parent_conn, child_conn = Pipe()
	p = multiprocessing.Process(target=run, args=(code, tests, child_conn))
	p.start()
	p.join(20)
	if p.is_alive():
		p.kill()
		p.join()
		return "Entered code took too long to compile and execute", 508
	msg, resp = parent_conn.recv()
	z = json.dumps(msg)
	return z, resp


if __name__ == '__main__':
	app.run()
