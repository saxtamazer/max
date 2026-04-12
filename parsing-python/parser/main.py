import uvicorn
from fastapi import FastAPI
from routers import parser

app = FastAPI()
app.include_router(parser.parser, prefix = "/py/v1")

if __name__ == '__main__':
    uvicorn.run("main:app", host = '0.0.0.0', port = 8082, reload = True, workers = 1)
