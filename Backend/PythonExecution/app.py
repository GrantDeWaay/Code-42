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
	"code": "x = 23 + 2\nprint(x)"
    tests:{
        "test1":{
            "x"="1"
            "exp"="12"
        }
        "test2":{
            "x"="2"
            "exp"="22"
        }
    }
}	

it is pretty straight forward	
'''

def run(code, conn, tests):
	f = StringIO()
	resp = "200"
	with redirect_stdout(f):
		try:
			x = compile(code, '', 'exec')
			globalsParameter = {'__builtins__': None}
			localsParameter = {'print': print, 'dir': dir}
			exec(x, globalsParameter, localsParameter)
		except Exception:
			resp = "422"
			print(format_exc())
	message = f.getvalue()
	conn.send([message, resp])
	conn.close()

@app.route('/', methods=["POST"])
def init_student_run():
	json_data = request.get_json()
	code = json_data.get('code')
	parent_conn, child_conn = Pipe()
	p = multiprocessing.Process(target=run, args=(code, child_conn, tests))
	p.start()
	p.join(10)
	if p.is_alive():
		p.kill()
		p.join()
		return "Entered code took too long to compile and execute", 508
	msg, resp = parent_conn.recv()
	return msg, resp


if __name__ == '__main__':
	app.run()
