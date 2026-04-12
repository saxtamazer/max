from service import parser as ParserService
from fastapi import APIRouter, UploadFile
import requests

parser = APIRouter()

@parser.post("/parser")
def parsing_xlsx(file: UploadFile):
    json_path = ParserService.parsing(file.file)

    url = "http://localhost:8081/api/v1/parser/schedule"

    with open(json_path, "rb") as json_file:
        files = {
            "file" :  ("schedule.json", json_file, "application/json")
                }
        response = requests.post(
                url,
                files = files
            )
    
    print("status: " + str(response.status_code))
    print("message: " + str(response.text))