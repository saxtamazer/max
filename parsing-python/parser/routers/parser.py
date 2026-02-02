from service import parser as ParserService
from fastapi import APIRouter, UploadFile
import requests

parser = APIRouter()

@parser.post("/parser")
def parsing_xlsx(file: UploadFile):
    json_path = ParserService.parsing(file.file)

    url = "http://localhost:8081/api/v1/parser/read"

    with open(json_path, "rb") as file:
        response = requests.post(
                url,
                files = {
                    "file": ("schedule.json", file, "application/json")
                }
            )
    
    print("status: " + str(response.status_code))
    print("message: " + str(response.text))